package iti.intake41.myapplication.tripsmap;

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
import iti.intake41.myapplication.models.Trips;
import iti.intake41.myapplication.tripsmap.viewmodel.DoneTripsViewModel;

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
        doneTripsViewModel = new ViewModelProvider(this).get(DoneTripsViewModel.class);
        colorList = new ArrayList<>();
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

        doneTripsViewModel.trips.observe(this, new Observer<List<Trips>>() {
            @Override
            public void onChanged(List<Trips> trips) {

                //if i have a list of object
                for (int i = 0; i < trips.size(); i++) {
                    Log.i("I", "i= " + i);
                    Trips trip = trips.get(i);
                    double srclong = trip.getTripStartPointLongitude();
                    double scrlat = trip.getTripStartPointLatitude();
                    final LatLng src = new LatLng(srclong, scrlat);
                    mMap.addMarker(new MarkerOptions().position(src).title(trip.getTripName())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    double deslong = trip.getTripEndPointLongitude();
                    double deslat = trip.getTripEndPointLatitude();
                    final LatLng des = new LatLng(deslong, deslat);
                    mMap.addMarker(new MarkerOptions().position(des).title(trip.getTripName()));

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