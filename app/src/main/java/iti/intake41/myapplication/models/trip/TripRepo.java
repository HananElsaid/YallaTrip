package iti.intake41.myapplication.models.trip;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iti.intake41.myapplication.models.FirebaseRepo;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;

public class TripRepo extends FirebaseRepo implements TripRepoInterface{
    DatabaseReference tripRef;

    public TripRepo(FirebaseRepoDelegate delegate){
        tripRef = mDatabase.child("trips").child(getUid());
        tripRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Trip> trips = new ArrayList();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    trips.add(postSnapshot.getValue(Trip.class));
                }
                delegate.getListSuccess(trips);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, "onCancelled: "+ error.getMessage());
                delegate.failed(error.getMessage());
            }
        });

    }

    public String getTripID(){
        return tripRef.push().getKey();
    }

    public void getTrips(FirebaseRepoDelegate delegate){
        tripRef.get()
                .addOnSuccessListener(dataSnapshot -> {})
                .addOnFailureListener(e -> delegate.failed(e.getLocalizedMessage()));
    }

    public void addTrip(Trip trip, FirebaseRepoDelegate delegate) {
        String tripId = getTripID();
        trip.setId(tripId);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + tripId, trip.toMap());

        tripRef.updateChildren(childUpdates)
                .addOnSuccessListener(dataSnapshot -> delegate.success("Trip Added Successfully"))
                .addOnFailureListener(e -> delegate.failed(e.getLocalizedMessage()));
    }

    public void updateTrip(Trip trip, FirebaseRepoDelegate delegate) {
        String path = "/trips/" + getUid() + "/" +  trip.getId();

        mDatabase.child(path).setValue(trip.toMap())
                .addOnSuccessListener(dataSnapshot -> delegate.success("Trip Updated Successfully"))
                .addOnFailureListener(e -> delegate.failed(e.getLocalizedMessage()));
    }


    public void deleteTrip(String id, FirebaseRepoDelegate delegate) {
        String path = "/trips/" + getUid() + "/" + id;

        mDatabase.child(path).removeValue()
                .addOnSuccessListener(dataSnapshot -> delegate.success("Trip Deleted Successfully"))
                .addOnFailureListener(e -> delegate.failed(e.getLocalizedMessage()));
    }

    public void getTripDetails(String id, FirebaseRepoDelegate delegate) {
        tripRef.child(id).get()
                .addOnSuccessListener(dataSnapshot -> delegate.getObjSuccess(dataSnapshot.getValue(Trip.class)))
                .addOnFailureListener(e -> delegate.failed(e.getLocalizedMessage()));
    }
}
