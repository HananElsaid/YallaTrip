package iti.intake41.myapplication.createtrip.model;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import iti.intake41.myapplication.createtrip.view.CreateTrip;
import iti.intake41.myapplication.createtrip.viewmodel.CreateTripViewModel;
import iti.intake41.myapplication.login.viewmodel.LoginViewModel;

public class CreateTripRepository {
    CreateTripViewModel createTripViewModel;

    public CreateTripRepository(CreateTripViewModel createTripViewModel) {
        this.createTripViewModel = createTripViewModel;
    }

//    /////
//    Button timePicker, datePicker;
//    int t1Hours, t1Min;
//    int t1year, t1mounth, t1day;
//    CreateTrip CreateTrip;
//
//    public void timePicker(View view) {
//        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTrip,
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        // initialize hour and Mins
//                        t1Hours = hourOfDay;
//                        t1Min = minute;
//                        //initialize calendar
//                        Calendar calendar = Calendar.getInstance();
//                        //set hours and minute
//                        calendar.set(0, 0, 0, t1Hours, t1Min);
//                        //set on btn
//                        //Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
//                        timePicker.setText(hourOfDay + " : " + minute); // set the current time in text view
//
//                    }
//                }, 12, 00, false
//        );
//        timePickerDialog.updateTime(t1Hours, t1Min);
//        timePickerDialog.show();
//
//    }
//
//    public void datePicker(View view) {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTrip,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        datePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                    }
//                }, t1year, t1mounth, t1day
//        );
//
//        datePickerDialog.show();
//    }
//
//
//    public void onCheckboxClicked(View view) {
//
//    }
//
//    public void addNotes(View view) {
//    }


}
