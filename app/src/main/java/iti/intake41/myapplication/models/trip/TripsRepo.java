package iti.intake41.myapplication.models.trip;

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
import iti.intake41.myapplication.models.Trip;

public class TripsRepo extends FirebaseRepo{
    DatabaseReference tripRef;

    public TripsRepo(TripsRepoDelegate delegate){
        tripRef = mDatabase.child("trips").child(getUid());
        tripRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Trip> trips = new ArrayList();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    trips.add(postSnapshot.getValue(Trip.class));

                }
                delegate.updateTrips(trips);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                delegate.showMessage(error.getMessage());
            }
        });

    }

    public String getTripID(){
        return tripRef.push().getKey();
    }

    public void getTrips(){
            tripRef.get();
    }

    public void addTrip(Trip trip) {
        String tripId = getTripID();
        trip.setId(tripId);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/trips/" + getUid() + "/" + tripId, trip.toMap());
        mDatabase.updateChildren(childUpdates);
    }

    public void updateTrip(Trip trip) {
        String path = "/trips/" + getUid() + "/" +  trip.getId();
        mDatabase.child(path).setValue(trip.toMap());
    }


    public void deleteTrip(String id) {
        String path = "/trips/" + getUid() + "/" + id;
        mDatabase.child(path).removeValue();
    }

    public void getTripDetails(String id) {
        tripRef.child(id).get();
    }
}
