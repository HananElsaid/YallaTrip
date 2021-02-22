package iti.intake41.myapplication.tripsmap.model;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import iti.intake41.myapplication.helper.Constants;
import iti.intake41.myapplication.helper.NetworkClass;
import iti.intake41.myapplication.models.Trips;

public class DoneTripsModel implements DoneTripsModelInterface {
    public MutableLiveData<List<Trips>> doneTrips;
    public MutableLiveData<String> message;
    DatabaseReference databaseTrips;
    ArrayList<Trips> historyTrips;
    ArrayList<Integer> colorList;
    MutableLiveData<List<Integer>> colerData;

    public DoneTripsModel() {
        doneTrips = new MutableLiveData<>();
        message = new MutableLiveData<>();
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips");
        historyTrips = new ArrayList<>();
        colerData= new MutableLiveData<>();

    }


    public MutableLiveData<List<Integer>> getColorList() {
        colorList = new ArrayList<>();
        colorList.add(Color.RED);
        colorList.add(Color.GREEN);
        colorList.add(Color.YELLOW);
        colorList.add(Color.BLUE);
        colorList.add(Color.WHITE);
        colorList.add(Color.GRAY);
        colorList.add(Color.BLACK);
        colorList.add(Color.DKGRAY);
        colorList.add(Color.LTGRAY);
        colerData.postValue(colorList);
        return colerData;
    }

    public MutableLiveData<List<Trips>>  getDoneTrips() {
        new Thread(new Runnable() {
            int i = 0;

            @Override
            public void run() {

                databaseTrips.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        historyTrips.clear();

                        for (DataSnapshot tripSnapshot : dataSnapshot.getChildren()) {
                            Trips upcomingTrip = tripSnapshot.getValue(Trips.class);

                            Log.i("1111", "onDataChange: "+tripSnapshot.getChildren());
                          if(upcomingTrip.getUserID()!=null){
                            //if (upcomingTrip.getUserID().equals(NetworkClass.getCurrentUser().getUid())) {
                                if (upcomingTrip.getTripStatus().equals(Constants.CANCELLED) ||
                                        upcomingTrip.getTripStatus().equals(Constants.DONE)) {
                                    historyTrips.add(upcomingTrip);
                                    Log.i("1111", "onDataChange: "+historyTrips.get(0).getTripName());
                                }
                            //}
                            //else Log.i("1111", "onDataChange: ");
                          }

                        }
                        doneTrips.postValue(historyTrips);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        message.postValue(databaseError.getDetails());
                    }
                });

            }
        }).start();
        return doneTrips;
    }


}
