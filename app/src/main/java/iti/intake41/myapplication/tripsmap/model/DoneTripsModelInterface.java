package iti.intake41.myapplication.tripsmap.model;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import iti.intake41.myapplication.models.Trips;

public interface DoneTripsModelInterface {
   public MutableLiveData<List<Trips>> doneTrips=null;
   public MutableLiveData<String> message=null;
    public MutableLiveData<List<Trips>>  getDoneTrips();
    public MutableLiveData<List<Integer>> getColorList();
}
