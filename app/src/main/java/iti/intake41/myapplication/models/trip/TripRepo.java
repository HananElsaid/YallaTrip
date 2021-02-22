package iti.intake41.myapplication.models.trip;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iti.intake41.myapplication.models.FirebaseRepo;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;

public class TripRepo extends FirebaseRepo implements TripRepoInterface{
    DatabaseReference userRef;

    public TripRepo(){
            userRef  = mDatabase.child("trips").child(getUid());
    }

    public String getTripID(){
        return userRef.push().getKey();
    }

    @Override
    public void getTrips(FirebaseRepoDelegate delegate){
        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    List<Trip> trips = new ArrayList();
                    task.getResult().getChildren().forEach(i->{
                        trips.add(i.getValue(Trip.class));
                    });

                    System.out.println("trips: " + task.getResult().getChildren());
                    delegate.getListSuccess(trips);
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                    delegate.failed(task.getException().getLocalizedMessage());
                }
            }
        });
    }



    @Override //#done
    public void addTrip(Trip trip, FirebaseRepoDelegate delegate) {
        String tripId = getTripID();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/trips/" + getUid() + "/" + tripId, trip.toMap());

        mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    delegate.success("Trip added Successfully");
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                    delegate.failed(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void updateTrip(Trip trip, FirebaseRepoDelegate delegate) {
        String tripId = trip.getId();
        String path = "/trips/" + getUid() + "/" + tripId;
        mDatabase.child(path).setValue(trip.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    delegate.success("Trip updated Successfully");
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                    delegate.failed(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void deleteTrip(String id, FirebaseRepoDelegate delegate) {
        String path = "/trips/" + getUid() + "/" + id;

        mDatabase.child(path).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    delegate.success("Trip updated Successfully");
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                    delegate.failed(task.getException().getLocalizedMessage());
                }
            }
        });
    }


}
