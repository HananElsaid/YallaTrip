package iti.intake41.myapplication.modules.tripsmap.viewmodel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;

import iti.intake41.myapplication.helper.Constants;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripRepo;
import iti.intake41.myapplication.models.trip.TripStatus;
import iti.intake41.myapplication.modules.tripsmap.model.DoneTripsModel;
import iti.intake41.myapplication.modules.tripsmap.model.DoneTripsModelInterface;

import static java.util.stream.Collectors.toList;

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
        repo = new TripRepo();
        //getTrips();

    }

    public List<Integer> getColorList() {
        return doneTripsModel.getColorList();
    }


    public void getTrips() {
        Log.i("getListSuccess", ": ");
        repo.getTrips(new FirebaseRepoDelegate() {
            MutableLiveData<List<Trip>> tripss = new MutableLiveData<>();
            List<Trip> TripLists = new ArrayList<>();

            @Override
            public <T> void getListSuccess(List<T> list) {
                List<Trip> trips = (List<Trip>) list;
                //if api >7
              /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    TripLists = trips.stream().filter(e -> e.getStatus().equals(TripStatus.done)).collect(toList());
                } else {*/
                    for (Trip t : trips) {
                        if (t.getStatus().equals("done")) {
                            TripLists.add(t);
                        }
                 //   }

                }
                tripss.setValue(TripLists);
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
