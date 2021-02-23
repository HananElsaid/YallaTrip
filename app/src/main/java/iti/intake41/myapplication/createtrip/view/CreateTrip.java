package iti.intake41.myapplication.createtrip.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import iti.intake41.myapplication.R;

public class CreateTrip extends AppCompatActivity {

    Button createTrip,addNotes,timePicker,datePicker;
    TextView tripName,startPoint,endPoint;
    CheckBox roundTrip;


    /////
    int t1Hours,t1Min;
    int t1year, t1mounth,t1day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        createTrip=findViewById(R.id.btnCreateTrip);
        addNotes=findViewById(R.id.addNotes);
        datePicker=findViewById(R.id.timeTextView);
        timePicker=findViewById(R.id.timeTextView);

        tripName= findViewById(R.id.titleTextView);
        startPoint= findViewById(R.id.txtStartPoint);
        endPoint= findViewById(R.id.txtEndPoint);
        //roundTrip=findViewById(R.id.roundTrip);

        timePicker.setOnClickListener(v -> {
            timePicker(v);

        });


        datePicker.setOnClickListener(v -> {
            datePicker(v);

        });
    }


    public void timePicker(View view) {
        TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // initialize hour and Mins
                t1Hours=hourOfDay;
                t1Min=minute;
                //initialize calendar
                Calendar  calendar= Calendar.getInstance();
                //set hours and minute
                calendar.set(0,0,0,t1Hours,t1Min);
                //set on btn
                //Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                timePicker.setText(hourOfDay + " : " + minute); // set the current time in text view

            }
        },12 ,0, false
                );
        timePickerDialog.updateTime(t1Hours,t1Min);
        timePickerDialog.show();

    }

    public void datePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            datePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, t1year, t1mounth, t1day);
        datePickerDialog.updateDate(t1year,t1mounth,t1day);

        datePickerDialog.show();
    }


    public void onCheckboxClicked(View view) {

    }

    public void addNotes(View view) {
    }
}