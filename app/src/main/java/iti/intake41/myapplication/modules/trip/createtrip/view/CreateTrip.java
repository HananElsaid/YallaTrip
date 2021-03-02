package iti.intake41.myapplication.modules.trip.createtrip.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.Location;
import iti.intake41.myapplication.modules.reminder.MyAlarm;
import iti.intake41.myapplication.modules.map.searchplace.PlaceSearchFragment;
import iti.intake41.myapplication.viewmodel.TripViewModel;

import static android.app.DatePickerDialog.OnDateSetListener;

public class CreateTrip extends AppCompatActivity {

    private static final int MAP_BUTTON_REQUEST_CODE = 123;
    //MARK: - UICompnents
    Button doneButton, timeButton, dateButton;
    TextView tripNameTV, startPointTV, endPointTV;
    CheckBox roundTripCheckBox;

    //MARK: - Attributes
    Calendar calendar;
    public Trip trip;
    int hr, min, year, month, day;
    TripViewModel tripViewModel;
    public static final String TAG = "CreateTrip";

    //Activity Life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        initViews();
        if(getIntent().hasExtra("trip")){ //Update Trip
            configureData(getIntent().getParcelableExtra("trip"));
        }else{ //Create Trip
            trip = new Trip();
        }
        calendar = Calendar.getInstance();

        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel.setContext(this);
    }

    public void initViews(){
        doneButton = findViewById(R.id.btnCreateTrip);
        dateButton = findViewById(R.id.dateTextView);
        timeButton = findViewById(R.id.timeTextView);
        tripNameTV = findViewById(R.id.titleTextView);
        startPointTV = findViewById(R.id.txtStartPoint);
        endPointTV = findViewById(R.id.txtEndPoint);
        roundTripCheckBox = findViewById(R.id.roundTrip);
    }

    public void configureData(Trip trip){
        this.trip = trip;
        tripNameTV.setText( trip.getTitle());
        timeButton.setText(trip.getTime());
        dateButton.setText(trip.getDate());
        startPointTV.setText(trip.getStartPoint().getAddress());
        endPointTV.setText(trip.getEndPoint().getAddress());
    }



    public void timePickerClicked(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // initialize hour and Mins
                hr = hourOfDay;
                min = minute;
                calendar.set(Calendar.HOUR_OF_DAY,hr);
                calendar.set(Calendar.MINUTE,min);

                Date d = calendar.getTime();
                String time = new SimpleDateFormat("HH:mm a").format(d);
                timeButton.setText(time); // set the current time in text view
            }
        }, 12, 0, false
        );
        timePickerDialog.updateTime(hr, min);
        timePickerDialog.show();

    }

    public void datePickerClicked(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateButton.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                    }
                }, year, month, day);

        datePickerDialog.updateDate(year, month, day);
        try {
            Date d;
            if(trip != null && trip.getDate() != null){
                d = new SimpleDateFormat("dd/MM/YYYY").parse(trip.getDate());
            }else{
                d = new Date();
            }
            datePickerDialog.getDatePicker().setMinDate(d.getTime());
            datePickerDialog.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void doneClicked(View view){
        if(isValid()){
            trip.setTitle( tripNameTV.getText().toString());
            trip.setTime((String) timeButton.getText());
            trip.setDate((String) dateButton.getText());
            trip.setStatus("upcoming");

            tripViewModel.addTrip(trip, ()->{
                MyAlarm.setAlarm(CreateTrip.this,trip,hr,min,day,month,year);
                finish();
            });

        }else {
            Toast.makeText(this, "Please fill all fields first", Toast.LENGTH_LONG).show();
        }

    }

    public void onCheckboxClicked(View view) {

    }


    public boolean isValid(){
        return !(tripNameTV.getText().toString().isEmpty() ||
                timeButton.getText().equals("TIME") ||
                dateButton.getText().equals("Date")  ||
                startPointTV.getText().equals("Tap to choose location") ||
                endPointTV.getText().equals("Tap to choose location"));
    }

    public void setupPlaces(Boolean isStartPoint){
        Fragment fragment;
        String searchText;
        if(isStartPoint){
            searchText = trip.getStartPoint() == null ? "" : trip.getStartPoint().getAddress();
            fragment = PlaceSearchFragment.newInstance(searchText, (place) -> {
                Location loc = new Location(place.getTitle() + ", " + place.getVicinity(), String.valueOf(place.getPosition()[0]), String.valueOf(place.getPosition()[1]));
                trip.setStartPoint(loc);
                startPointTV.setText(loc.getAddress());
            });
        }else{
            searchText = trip.getEndPoint() == null ? "" : trip.getEndPoint().getAddress();
            fragment = PlaceSearchFragment.newInstance(searchText, (place) -> {
                Location loc = new Location(place.getTitle() + ", " + place.getVicinity(), String.valueOf(place.getPosition()[0]), String.valueOf(place.getPosition()[1]));
                trip.setEndPoint(loc);
                endPointTV.setText(loc.getAddress());
            });
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.place_fragment_container_view, fragment, "fragment_place_search");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }



    public void startPointClicked(View view) {
        setupPlaces(true);
    }

    public void endPointClicked(View view) {
        setupPlaces(false);
    }
}