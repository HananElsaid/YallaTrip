package iti.intake41.myapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.intake41.myapplication.helper.Loader;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripRepo;

public class TripViewModel extends ViewModel {

    public MediatorLiveData<List<Trip>> itemsList = new MediatorLiveData();
    public MediatorLiveData<Trip> selectedItem = new MediatorLiveData();
    private Loader loader;
    private TripRepo repo;
    Context context;

    private static final String TAG = "TripViewModel";

    public TripViewModel() {
        this.repo = new TripRepo();
        this.repo.setListener(new FirebaseRepoDelegate() {
            @Override
            public <T> void getListSuccess(List<T> list) {
                itemsList.setValue((List<Trip>) list);
            }
            @Override
            public void failed(String message) {
                showMessage(message);
            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
        this.loader = new Loader(context);
    }

    public void getTrips() {
        if (itemsList.getValue() == null) {
            repo.getTrips(new FirebaseRepoDelegate() {
                @Override
                public <T> void getListSuccess(List<T> list) {
                    itemsList.setValue((List<Trip>) list);
                }

                @Override
                public void failed(String message) {
                    showMessage(message);
                }
            });
        }
    }

    public void addTrip(Trip trip, OnSuccess delegate) {
        repo.addTrip(trip, new FirebaseRepoDelegate() {
            @Override
            public void success(String message) {
                showMessage(message);
                delegate.onSuccess();
            }

            @Override
            public void failed(String message) {
                showMessage(message);
            }
        });
    }

    public void deleteTrip(String id,  OnSuccess delegate) {
        repo.deleteTrip(id, new FirebaseRepoDelegate() {
            @Override
            public void success(String message) {
                showMessage(message);
                delegate.onSuccess();
            }

            @Override
            public void failed(String message) {
                showMessage(message);
            }
        });
    }

    public void updateTrip(Trip trip, OnSuccess delegate) {
        repo.updateTrip(trip, new FirebaseRepoDelegate() {
            @Override
            public void success(String message) {
               showMessage(message);
                delegate.onSuccess();
            }

            @Override
            public void failed(String message) {
                showMessage(message);
            }
        });
    }

    public void getTripDetails(String id) {
        repo.getTripDetails(id, new FirebaseRepoDelegate() {
            @Override
            public <T> void getObjSuccess(T obj) {
                selectedItem.setValue((Trip) obj);
            }

            @Override
            public void failed(String message) {
                showMessage(message);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared: ");
    }

    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG);
    }
}