package iti.intake41.myapplication.models;

import java.util.List;

public interface FirebaseRepoDelegate{
    void getTripsSuccess(List<Trip> tripList);
    void failed(String message);
}
