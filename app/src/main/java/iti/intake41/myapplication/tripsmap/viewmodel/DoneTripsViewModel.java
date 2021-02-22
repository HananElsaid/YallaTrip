package iti.intake41.myapplication.tripsmap.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.intake41.myapplication.models.Trips;
import iti.intake41.myapplication.tripsmap.model.DoneTripsModel;
import iti.intake41.myapplication.tripsmap.model.DoneTripsModelInterface;

public class DoneTripsViewModel extends ViewModel {
    public MutableLiveData<List<Trips>> trips;
    public MutableLiveData<String> message;
    //public MutableLiveData<List<Integer>> colorList;

    DoneTripsModelInterface doneTripsModel;

    public DoneTripsViewModel() {
        doneTripsModel = new DoneTripsModel();
        trips = new MutableLiveData<>();
        //colorList = new MutableLiveData<>();
        message = new MutableLiveData<>();
        getDataFromModel();
    }

    void getDataFromModel() {
        trips = doneTripsModel.getDoneTrips();
        message = doneTripsModel.message;
        //colorList = doneTripsModel.getColorList();

    }
    public List<Integer> getColorList(){
       return  doneTripsModel.getColorList().getValue();
    }


}
