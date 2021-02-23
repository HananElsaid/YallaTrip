package iti.intake41.myapplication.models.trip;

import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;

public interface TripRepoInterface {
    void getTrips(FirebaseRepoDelegate delegate);
    void addTrip(Trip trip, FirebaseRepoDelegate delegate);
    void updateTrip(Trip trip, FirebaseRepoDelegate delegate);
    void deleteTrip(String id, FirebaseRepoDelegate delegate);
    void getTripDetails(String id, FirebaseRepoDelegate delegate);
}
