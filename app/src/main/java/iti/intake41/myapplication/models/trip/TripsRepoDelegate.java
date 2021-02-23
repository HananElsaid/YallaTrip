package iti.intake41.myapplication.models.trip;

import java.util.List;

import iti.intake41.myapplication.models.Trip;

public interface TripsRepoDelegate {
    void updateTrips(List<Trip> tripList);
    void showMessage(String message);
}
