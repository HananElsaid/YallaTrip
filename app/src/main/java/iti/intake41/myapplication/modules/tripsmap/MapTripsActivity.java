package iti.intake41.myapplication.modules.tripsmap;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.modules.tripsmap.viewmodel.DoneTripsViewModel;

public class MapTripsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //view model refrence
    DoneTripsViewModel doneTripsViewModel;
    private List<Integer> colorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_trips);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //view model object
        doneTripsViewModel = new ViewModelProvider(this).get(DoneTripsViewModel.class);
        //list of colors
        colorList = new ArrayList<>();
        //observe on message if an error ocures
        if(doneTripsViewModel.message!=null){
        doneTripsViewModel.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MapTripsActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });}
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng src1 = new LatLng(31.041455, 31.4178593);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(src1, 4f));

        doneTripsViewModel.tripsList.observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {

                //if i have a list of object
                for (int i = 0; i < trips.size(); i++) {
                    Log.i("I", "i= " + i);
                    Trip trip = trips.get(i);
                    double srclong = Double.parseDouble(trip.getStartPoint().getLongitude());
                    double scrlat = Double.parseDouble(trip.getStartPoint().getLatitude());
                    final LatLng src = new LatLng(srclong, scrlat);
                    mMap.addMarker(new MarkerOptions().position(src).title(trip.getTitle())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    double deslong = Double.parseDouble(trip.getEndPoint().getLongitude());
                    double deslat = Double.parseDouble(trip.getEndPoint().getLatitude());
                    final LatLng des = new LatLng(deslong, deslat);
                    mMap.addMarker(new MarkerOptions().position(des).title(trip.getTitle()));

                    //
                    colorList = doneTripsViewModel.getColorList();
                    if (colorList.size() > i)
                        mMap.addPolyline(new PolylineOptions().add(src).add(des).width(10f).color(colorList.get(i)));
                    else {
                        //i = 0;
                        mMap.addPolyline(new PolylineOptions().add(src).add(des).width(10f).color(colorList.get(3)));
                    }

                }
            }

        });


    }
}