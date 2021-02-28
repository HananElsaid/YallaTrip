package iti.intake41.myapplication.modules.map.tripsmap.viewmodel;

import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripRepo;
import iti.intake41.myapplication.modules.map.tripsmap.model.DoneTripsModel;
import iti.intake41.myapplication.modules.map.tripsmap.model.DoneTripsModelInterface;

public class DoneTripsViewModel extends ViewModel {

    public MediatorLiveData<List<Trip>> tripsList;
    public MutableLiveData<String> message;
    //public MutableLiveData<List<Integer>> colorList;
    TripRepo repo;

    DoneTripsModelInterface doneTripsModel;

    public DoneTripsViewModel() {
        doneTripsModel = new DoneTripsModel();
        // tripsList = new MutableLiveData<>();
        tripsList = new MediatorLiveData<>();
        //colorList = new MutableLiveData<>();
        message = new MutableLiveData<>();
        setupRepo();
        //getTrips();

    }

    public void setupRepo(){
        repo = new TripRepo();
        repo.setListener(new FirebaseRepoDelegate() {
            @Override
            public <T> void getListSuccess(List<T> list) {
                List<Trip> tripLists = new ArrayList<>();
                for (Trip t : (List<Trip>)list) {
                    if (t.getStatus().equals("done")) {
                        tripLists.add(t);
                    }
                }
                tripsList.setValue(tripLists);
            }
        });
    }

    public List<Integer> getColorList() {
        return doneTripsModel.getColorList();
    }


    public void getTrips() {
        Log.i("getListSuccess", ": ");
        repo.getTrips(new FirebaseRepoDelegate() {
            MutableLiveData<List<Trip>> tripss = new MutableLiveData<>();
            List<Trip> tripLists = new ArrayList<>();

            @Override
            public <T> void getListSuccess(List<T> list) {
                List<Trip> trips = (List<Trip>) list;
                //if api >7
              /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    TripLists = trips.stream().filter(e -> e.getStatus().equals(TripStatus.done)).collect(toList());
                } else {*/
                    for (Trip t : trips) {
                        if (t.getStatus().equals("done")) {
                            tripLists.add(t);
                        }
                 //   }

                }
                tripss.setValue(tripLists);
                tripsList.addSource(tripss, new Observer<List<Trip>>() {
                    @Override
                    public void onChanged(List<Trip> trips1) {
                        tripsList.setValue(trips1);
                    }
                });


            }

            @Override
            public void failed(String mes) {
                message.setValue(mes);
                Log.i("getListSuccess", "getListSuccess: " + mes);
            }
        });
    }

}
