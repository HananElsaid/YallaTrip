package iti.intake41.myapplication.modules.tripsmap.model;

import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class DoneTripsModel implements DoneTripsModelInterface {
  /*  public MutableLiveData<List<Trip>> doneTrips;
    public MutableLiveData<String> message;
    DatabaseReference databaseTrips;
    ArrayList<Trips> historyTrips;*/
    ArrayList<Integer> colorList;
    MutableLiveData<List<Integer>> colerData;

    public DoneTripsModel() {
      /*  doneTrips = new MutableLiveData<>();
        message = new MutableLiveData<>();
        databaseTrips = FirebaseDatabase.getInstance().getReference("trips");
        historyTrips = new ArrayList<>();*/
        colerData= new MutableLiveData<>();

    }


    public List<Integer> getColorList() {
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
        //colerData.postValue(colorList);
        return colorList;
    }

/*
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
*/


}
