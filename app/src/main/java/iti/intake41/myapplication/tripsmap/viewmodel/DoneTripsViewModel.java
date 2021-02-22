package iti.intake41.myapplication.tripsmap.viewmodel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;


import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripRepo;
import iti.intake41.myapplication.models.trip.TripStatus;
import iti.intake41.myapplication.tripsmap.model.DoneTripsModel;
import iti.intake41.myapplication.tripsmap.model.DoneTripsModelInterface;

import static java.util.stream.Collectors.toList;

public class DoneTripsViewModel extends ViewModel {

    public MutableLiveData<List<Trip>> tripsList;
    public MutableLiveData<String> message;
    public MutableLiveData<List<Integer>> colorList;
    TripRepo repo;

    DoneTripsModelInterface doneTripsModel;

    public DoneTripsViewModel() {
        doneTripsModel = new DoneTripsModel();
        tripsList = new MutableLiveData<>();
        //colorList = new MutableLiveData<>();
        message = new MutableLiveData<>();
        repo= new TripRepo();
        getTrips();
    }

    public List<Integer> getColorList() {
        return doneTripsModel.getColorList().getValue();
    }

    void getTrips() {

        repo.getTrips(new FirebaseRepoDelegate() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public <T> void getListSuccess(List<T> list) {
                List<Trip> trips = (List<Trip>) list;

                //if api >7
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    List<Trip> collect = trips.stream().filter(e -> e.getStatus().equals(TripStatus.done)).collect(toList());
                    tripsList.setValue(collect);
                } else {
                    List<Trip> tripss = new ArrayList<>();
                    for (Trip t : trips) {

                        if (t.getStatus().equals(TripStatus.done)) {
                            tripss.add(t);
                        }
                    }
                    tripsList.setValue(tripss);
                }

            }

            @Override
            public void failed(String mes) {
                message.setValue(mes);
                Log.i("getListSuccess", "getListSuccess: "+mes);
            }
        });
    }

}
