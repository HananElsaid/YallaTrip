package iti.intake41.myapplication.models;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRepo {

    private static final String TAG = "ReadAndWriteSnippets";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    public FirebaseRepo(DatabaseReference database) {
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
    }

    // [START rtdb_write_new_user]
//    public void writeNewUser(String userId, String name, String email) {
//        User user = new User(name, email);
//
//        mDatabase.child("users").child(userId).setValue(user);
//    }
    // [END rtdb_write_new_user]

//    public void writeNewUserWithTaskListeners(String userId, String name, String email) {
//        User user = new User(name, email);
//
//        // [START rtdb_write_new_user_task]
//        mDatabase.child("users").child(userId).setValue(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Write was successful!
//                        // ...
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Write failed
//                        // ...
//                    }
//                });
//        // [END rtdb_write_new_user_task]
//    }

//    private void addPostEventListener(DatabaseReference mPostReference) {
//        // [START post_value_event_listener]
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                Post post = dataSnapshot.getValue(Post.class);
//                // ..
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//            }
//        };
//        mPostReference.addValueEventListener(postListener);
//        // [END post_value_event_listener]
//    }

    // [START write_fan_out]
    public void writeNewTrip(Trip trip) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("trips").push().getKey();
        Map<String, Object> tripValues = trip.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/trips/" + key, tripValues);
        childUpdates.put("/user-trips/" + trip.getUserID() + "/" + key, tripValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]


    public void getTrips(FirebaseRepoDelegate delegate){
        mDatabase.child("user-trips").child("1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    delegate.failed(task.getException().getLocalizedMessage());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    List<Trip> trips = new ArrayList();
                    task.getResult().getChildren().forEach(i->{
                        trips.add(i.getValue(Trip.class));
                    });

                    System.out.println("trips: " + task.getResult().getChildren());
                    delegate.getTripsSuccess(trips);
                }
            }
        });
    }


    public void getDataFirebase(FirebaseRepoDelegate delegate) {
        //get fresh data here
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "load items : onDataChanged() " + dataSnapshot.getKey());
                List<Trip> trips = new ArrayList();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //here it will parse every Item you have pushed to firebase
                    Trip trip = snapshot.getValue(Trip.class);
                    trips.add(trip);
                }
                delegate.getTripsSuccess(trips);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "load events : onCancelled", error.toException());
                delegate.failed(error.getMessage());
            }

        };
        mDatabase.addListenerForSingleValueEvent(listener);
    }





    private String getUid() {
        return "";
    }

    // [START post_stars_transaction]
//    private void onStarClicked(DatabaseReference postRef) {
//        postRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                Post p = mutableData.getValue(Post.class);
//                if (p == null) {
//                    return Transaction.success(mutableData);
//                }
//
//                if (p.stars.containsKey(getUid())) {
//                    // Unstar the post and remove self from stars
//                    p.starCount = p.starCount - 1;
//                    p.stars.remove(getUid());
//                } else {
//                    // Star the post and add self to stars
//                    p.starCount = p.starCount + 1;
//                    p.stars.put(getUid(), true);
//                }
//
//                // Set value and report transaction success
//                mutableData.setValue(p);
//                return Transaction.success(mutableData);
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean committed,
//                                   DataSnapshot currentData) {
//                // Transaction completed
//                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
//            }
//        });
//    }
    // [END post_stars_transaction]


}


