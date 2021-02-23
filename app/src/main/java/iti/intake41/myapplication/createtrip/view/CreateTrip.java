package iti.intake41.myapplication.createtrip.view;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.allyants.notifyme.NotifyMe;
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerController;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.creatnote.model.Note;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.Location;
import iti.intake41.myapplication.models.trip.TripRepo;
import iti.intake41.myapplication.models.user.User;

import static android.app.DatePickerDialog.*;

public class CreateTrip extends AppCompatActivity {

    private static final String TAG = "TAG";
    Button createTrip, addNotes, timePicker, datePicker;
    TextView tripName, startPoint, endPoint;
    CheckBox roundTrip;

    Trip trip= new Trip();

    TimePickerDialog tm;
    DatePickerDialog dp;
    Calendar calendar;
    //Trip trip=new Trip();
    /////
    int t1Hours, t1Min;
    int t1year, t1mounth, t1day;

    TimePicker myTimePicker;
    Note note=new Note();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        createTrip = findViewById(R.id.btnCreateTrip);
        addNotes = findViewById(R.id.addNotes);
        datePicker = findViewById(R.id.btnDatePicker);
        timePicker = findViewById(R.id.btnTimePicker);

        tripName = findViewById(R.id.txtNameTrip);
        startPoint = findViewById(R.id.txtStartPoint);
        endPoint = findViewById(R.id.txtEndPoint);

        //roundTrip=findViewById(R.id.roundTrip);

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

                Calendar calendar = Calendar.getInstance();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            myTimePicker.getHour(), myTimePicker.getMinute(), 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            myTimePicker.getCurrentHour(), myTimePicker.getCurrentMinute(), 0);
                }


                setAlarm(calendar.getTimeInMillis());

        });
    }




    public void timePicker(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // initialize hour and Mins
                t1Hours = hourOfDay;
                t1Min = minute;

                //initialize calendar
                //set hours and minute
                calendar.set(0, 0, 0, t1Hours, t1Min);


                //set on btn
                //Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                timePicker.setText(hourOfDay + " : " + minute); // set the current time in text view

                //Initialize
                NotifyMe notifyMe=new NotifyMe.Builder(getApplicationContext())
                        .title(note.getTripID().toString())
                        .content("It's Time For Your Trip")
                        .time(calendar)
                        .addAction(new Intent(),"Done") //insert the intent
                        .addAction(new Intent(), "Snooze",false) //insert intent
                        .addAction(new Intent(),"Cancel",false) //insert intent
                        .large_icon(R.mipmap.ic_launcher_round)
                        .build();
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

                        datePicker.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    }
                }, t1year, t1mounth, t1day);
        datePickerDialog.updateDate(t1year, t1mounth, t1day);
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.show();
    }

    public void  createTripDone(View view){
        trip.setTitle( tripName.getText().toString());
//        trip.setStartPoint((Location) startPoint.getText());
//        trip.setEndPoint( (Location) endPoint.getText());
        trip.setTime((String) timePicker.getText());
        trip.setDate((String) datePicker.getText());
        trip.setStatus("upcoming");


        (new TripRepo()).addTrip(trip, new FirebaseRepoDelegate() {
            @Override
            public void success(String message) {
                Toast.makeText(CreateTrip.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed(String message) {
                Toast.makeText(CreateTrip.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void openDialog() {
//        AlartDialog exampleDialog = new AlartDialog();
//        exampleDialog.show(getSupportFragmentManager(), "example dialog");
//    }

    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, AlarmRec.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }
    public void onCheckboxClicked(View view) {

    }

    public void addNotes(View view) {
    }

}