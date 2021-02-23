package iti.intake41.myapplication.modules.view.tripdetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripRepo;
import iti.intake41.myapplication.models.trip.TripRepoInterface;
import iti.intake41.myapplication.models.trip.TripStatus;

public class TripDetailes extends AppCompatActivity {

    //MARK: - UIComponents
    TextView txtStartPoint,txtEndPoint;

    //MARK: - Properties
    private Trip trip;
    private TextView titleTextView;
    private TextView statusTextView;
    private TextView dateTextView;
    private TextView timeTextView;


    TripRepoInterface repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detailes);
        initViews();
        repo = new TripRepo();
        Intent i = getIntent();
        if(i.hasExtra("trip")){
            trip = i.getParcelableExtra("trip");
            configureTrip();

        }else if(i.hasExtra("trip_id")){
            String id = i.getStringExtra("trip_id");
            getTripDetails(id);
        }
    }
    
    public void initViews(){
        titleTextView=findViewById(R.id.titleTextView);
        statusTextView=findViewById(R.id.statusTextView);
        txtStartPoint=findViewById(R.id.txtStartPoint);
        txtEndPoint=findViewById(R.id.txtEndPoint);
        dateTextView=findViewById(R.id.dateTextView);
        timeTextView=findViewById(R.id.timeTextView);
    }

    public void configureTrip(){
        titleTextView.setText(trip.getTitle());
        statusTextView.setText(trip.getStatus());
        txtStartPoint.setText(trip.getStartPoint().getAddress());
        txtEndPoint.setText(trip.getEndPoint().getAddress());
        dateTextView.setText(trip.getDate());
        timeTextView.setText(trip.getTime());

        TripStatus status = TripStatus.valueOf(trip.getStatus());
        int color;
        switch (status){
            case upcoming:
                color = R.color.orangeDark; break;
            case done:
                color = R.color.greenMid; break;
            default:
                color = R.color.redMid; break;

        }
        int c = ContextCompat.getColor(this,color);
        statusTextView.setTextColor(c);
    }

    public void addNotes(View view) {
        Navigator.navigateToNotes(this, trip.getId());
    }

    public void deleteClicked(View view) {
        if(trip != null)
        repo.deleteTrip(trip.getId(), new FirebaseRepoDelegate() {

            @Override
            public void success(String message) {
                Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void failed(String message) {
                Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editClicked(View view) {
        if(trip != null)
            Navigator.navigateToUpdateTrip(this, trip.getId());
    }

    public void startClicked(View view) {
        if(trip != null){
            String to = trip.getEndPoint().getAddress();
            // Creates an Intent that will load a map of San Francisco
            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr="+to);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    public void getTripDetails(String id){
        repo.getTripDetails(id, new FirebaseRepoDelegate() {
            @Override
            public <T> void getObjSuccess(T obj) {
                trip = (Trip) obj;
                configureTrip();
            }

            @Override
            public void failed(String message) {
                Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}