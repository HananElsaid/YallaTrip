package iti.intake41.myapplication.modules.createtrip.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.Location;
import iti.intake41.myapplication.models.trip.TripRepo;
import iti.intake41.myapplication.modules.reminder.MyAlarm;
import iti.intake41.myapplication.modules.reminder.MyIntentService;

import static android.app.DatePickerDialog.OnDateSetListener;

public class CreateTrip extends AppCompatActivity {

    //MARK: - UICompnents
    Button doneButton, timeButton, dateButton;
    TextView tripNameTV, startPointTV, endPointTV;
    CheckBox roundTripCheckBox;

    //MARK: - Attributes
    Calendar calendar;
    Trip trip;
    int hr, min, year, month, day;

    //Activity Life cycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        initViews();
        addActions();
        if(getIntent().hasExtra("trip")){ //Update Trip
            configureData(getIntent().getParcelableExtra("trip"));
        }else{ //Create Trip
            trip = new Trip();
        }
        calendar = Calendar.getInstance();
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

    public void addActions(){
        timeButton.setOnClickListener(v -> { timePickerClicked(); });
        dateButton.setOnClickListener(date -> { datePickerClicked(); });
        doneButton.setOnClickListener(v -> { doneClicked(); });
    }


    public void timePickerClicked() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // initialize hour and Mins
                hr = hourOfDay;
                min = minute;
                calendar.set(0, 0, 0, hr, min);
                Date d = calendar.getTime();
                String time = new SimpleDateFormat("HH:mm a").format(d);
                timeButton.setText(time); // set the current time in text view
            }
        }, 12, 0, false
        );
        timePickerDialog.updateTime(hr, min);
        timePickerDialog.show();

    }

    public void datePickerClicked() {
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

    public void doneClicked(){
        if(isValid()){
            trip.setTitle( tripNameTV.getText().toString());
            trip.setStartPoint(new Location("Ismaila", "30.5965", "32.2715"));
            trip.setEndPoint(new Location("Cairo", "30.0444", "31.2357"));
            trip.setTime((String) timeButton.getText());
            trip.setDate((String) dateButton.getText());
            trip.setStatus("upcoming");


            (new TripRepo()).addTrip(trip, new FirebaseRepoDelegate() {
                @Override
                public void success(String message) {
                    Toast.makeText(CreateTrip.this, message, Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void failed(String message) {
                    Toast.makeText(CreateTrip.this, message, Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(this, "Please fill all fields first", Toast.LENGTH_LONG).show();
        }

        //            Calendar calendar = Calendar.getInstance();
//            if (android.os.Build.VERSION.SDK_INT >= 23) {
//                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
//                        timePicker.getHour(), timePicker.getMinute(), 0);
//
//            } else {
//                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
//                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
//            }
//             timePicker(v);
//             datePicker(v);
        Intent intent1 = new Intent(getApplicationContext(), MyIntentService.class);
        //MyIntentService.enqueueWork(getApplicationContext(), intent1);
        setAlarm(calendar.getTimeInMillis());

    }

    public void onCheckboxClicked(View view) {

    }

    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, MyAlarm.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }

    public boolean isValid(){
        return !(tripNameTV.getText().toString().isEmpty() ||
                timeButton.getText().toString().isEmpty() ||
                dateButton.getText().toString().isEmpty()) ||
                startPointTV.getText().toString().isEmpty() ||
                endPointTV.getText().toString().isEmpty();
    }

}