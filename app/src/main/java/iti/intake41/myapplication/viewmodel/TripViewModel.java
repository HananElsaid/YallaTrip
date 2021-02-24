package iti.intake41.myapplication.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripsRepo;
import iti.intake41.myapplication.models.trip.TripsRepoDelegate;

public class TripViewModel extends ViewModel implements TripsRepoDelegate {

    public MediatorLiveData<List<Trip>> itemsList = new MediatorLiveData();
    public MediatorLiveData<Trip> selectedItem = new MediatorLiveData();

    private TripsRepo repo;
    private static TripViewModel instance;
    Context context;

    private TripViewModel(){
        this.repo = new TripsRepo(this);
        getTrips();
    }

    public static TripViewModel getInstance(Context context){
        if(instance == null){
            synchronized (TripViewModel.class){
                if(instance == null){
                    instance = new TripViewModel();
                }
            }
        }

        instance.context = context;
        return instance;
    }

    public void getTrips(){
        repo.getTrips();
    }

    public void addTrip(Trip trip) {
        repo.addTrip(trip);
    }

    public void deleteTrip(String id) {
        repo.deleteTrip(id);
    }

    public void updateTrip(Trip trip) {
        repo.updateTrip(trip);
    }

    @Override
    public void updateTrips(List<Trip> tripList) {
        itemsList.setValue(tripList);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}


//public class TripViewModel extends ViewModel{
//
//    public MediatorLiveData<List<Trip>> itemsList = new MediatorLiveData();
//    public MediatorLiveData<Trip> selectedItem = new MediatorLiveData();
//
//    private TripRepoInterface repo;
//    private static TripViewModel instance;
//
//    private TripViewModel(){
//        this.repo = new TripRepo();
//        getTrips();
//    }
//
//    public static TripViewModel getInstance(){
//        if(instance == null){
//            synchronized (TripViewModel.class){
//                if(instance == null){
//                    instance = new TripViewModel();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public void getTrips(){
//        System.out.println("Get items called");
//        repo.getTrips(new FirebaseRepoDelegate() {
//            @Override
//            public <T> void getListSuccess(List<T> list) {
//                itemsList.setValue((List<Trip>) list);
//            }
//        });
//    }
//
//    public void addTrip(Trip trip) {
//        repo.addTrip(trip, new FirebaseRepoDelegate(){
//            @Override
//            public void success(String message) {
//                getTrips();
//            }
//        });
//    }
//
//    public void deleteTrip(String id) {
//        repo.deleteTrip(id, new FirebaseRepoDelegate(){
//            @Override
//            public void success(String message) {
//                getTrips();
//            }
//        });
//    }
//
//    public void updateTrip(Trip trip) {
//        repo.updateTrip(trip, new FirebaseRepoDelegate(){
//            @Override
//            public void success(String message) {
//                getTrips();
//            }
//        });
//    }
//
//    public void getTripDetails(String id) {
//        repo.getTripDetails(id, new FirebaseRepoDelegate() {
//            @Override
//            public <T> void getObjSuccess(T obj) {
//                selectedItem.setValue( (Trip)obj );
//            }
//        });
//    }
//}
