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
import iti.intake41.myapplication.models.trip.Location;
import iti.intake41.myapplication.modules.creatnote.model.Note;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripRepo;
import iti.intake41.myapplication.modules.reminder.ReminderTrip;

import static android.app.DatePickerDialog.OnDateSetListener;

public class CreateTrip extends AppCompatActivity {

    private static final String TAG = "TAG";
    Button createTrip, timePicker, datePicker;
    TextView tripName, startPoint, endPoint;
    CheckBox roundTrip;

    Trip trip;
    ReminderTrip reminderTrip;
    TimePicker tm;
    DatePicker dp;
    Calendar calendar;
    //Trip trip=new Trip();
    /////
    int t1Hours, t1Min;
    int t1year, t1mounth, t1day;

    TimePicker myTimePicker;
    Note note=new Note();
    MyAlarm myAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        createTrip=findViewById(R.id.btnCreateTrip);
        datePicker=findViewById(R.id.dateTextView);
        timePicker=findViewById(R.id.timeTextView);
        tripName= findViewById(R.id.titleTextView);
        startPoint= findViewById(R.id.txtStartPoint);
        endPoint= findViewById(R.id.txtEndPoint);
        roundTrip=findViewById(R.id.roundTrip);

        if(getIntent().hasExtra("trip")){
            trip = getIntent().getParcelableExtra("trip");
            tripName.setText( trip.getTitle());
            timePicker.setText(trip.getTime());
            datePicker.setText(trip.getDate());
            startPoint.setText(trip.getStartPoint().getAddress());
            endPoint.setText(trip.getEndPoint().getAddress());
        }else{
            trip= new Trip();
        }

        //git current date and time
        calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        Date today = calendar.getTime();

        timePicker.setOnClickListener(v -> {
            timePicker(v);

        });


        datePicker.setOnClickListener(v -> {
            datePicker(v);

        });

        createTrip.setOnClickListener(v -> {

            createTripDone(v);
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

            Intent intent1 = new Intent(getApplicationContext(),ReminderTrip.class);
            //MyIntentService.enqueueWork(getApplicationContext(), intent1);
            myAlarm = new MyAlarm(CreateTrip.this,t1Hours,t1Min,t1day,t1mounth,t1year);
            myAlarm.setAlarm();
        });
    }




    public void timePicker(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // initialize hour and Mins
                t1Hours = hourOfDay;
                t1Min = minute;
                calendar.set(0, 0, 0, t1Hours, t1Min);
                Date d = calendar.getTime();

                String hour = new SimpleDateFormat("HH:mm a").format(d);
                //String hour = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + " " + ;
                //set on btn
                //Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                timePicker.setText(hour); // set the current time in text view
            }
        }, 12, 0, false
        );
        timePickerDialog.updateTime(t1Hours, t1Min);
        timePickerDialog.show();

    }

    public void datePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        datePicker.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                    }
                }, t1year, t1mounth, t1day);

        datePickerDialog.updateDate(t1year, t1mounth, t1day);
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

    public void  createTripDone(View view){

        if(isValid()){
            trip.setTitle( tripName.getText().toString());
            trip.setStartPoint(new Location("Ismaila", "30.5965", "32.2715"));
            trip.setEndPoint(new Location("Cairo", "30.0444", "31.2357"));
            trip.setTime((String) timePicker.getText());
            trip.setDate((String) datePicker.getText());
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


    }

//    public void openDialog() {
//        AlartDialog exampleDialog = new AlartDialog();
//        exampleDialog.show(getSupportFragmentManager(), "example dialog");
//    }

    public void onCheckboxClicked(View view) {

    }

//    private void setAlarm(long time) {
//        //getting the alarm manager
//        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        //creating a new intent specifying the broadcast receiver
//        Intent i = new Intent(this, MyAlarm.class);
//        //creating a pending intent using the intent
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//
//        //setting the repeating alarm that will be fired every day
//        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
//        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
//    }

    public boolean isValid(){
        return !(tripName.getText().toString().isEmpty() ||
                timePicker.getText().toString().isEmpty() ||
                datePicker.getText().toString().isEmpty());
    }

}