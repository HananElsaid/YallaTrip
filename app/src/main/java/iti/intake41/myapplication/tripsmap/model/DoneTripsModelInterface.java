package iti.intake41.myapplication.tripsmap.model;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import iti.intake41.myapplication.models.Trip;

public interface DoneTripsModelInterface {
 /*  public MutableLiveData<List<Trip>> doneTrips=null;
   public MutableLiveData<String> message=null;
    public MutableLiveData<List<Trip>>  getDoneTrips();*/
    public MutableLiveData<List<Integer>> getColorList();
}
