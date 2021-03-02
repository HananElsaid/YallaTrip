//package iti.intake41.myapplication.modules.createtrip.view;
//
//import android.app.AlarmManager;
//import android.app.DatePickerDialog;
//import android.app.PendingIntent;
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.DatePicker;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import iti.intake41.myapplication.R;
//import iti.intake41.myapplication.models.trip.Location;
//import iti.intake41.myapplication.models.FirebaseRepoDelegate;
//import iti.intake41.myapplication.models.Trip;
//import iti.intake41.myapplication.models.trip.TripRepo;
//import iti.intake41.myapplication.modules.reminder.ReminderTrip;
//import static android.app.DatePickerDialog.OnDateSetListener;
//
//public class CreateTrip extends AppCompatActivity {
//
//    private static final String TAG = "TAG";
//    Button createTrip, timePicker, datePicker;
//    TextView tripName, startPoint, endPoint;
//    CheckBox roundTrip;
//
//    Trip trip;
//    ReminderTrip reminderTrip;
//    TimePicker tm;
//    DatePicker dp;
//    Calendar calendar;
//    //Trip trip=new Trip();
//    /////
//    int t1Hours, t1Min;
//    int t1year, t1mounth, t1day;
//
//    TimePicker myTimePicker;
//    //MyAlarm myAlarm;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_trip);
//        createTrip=findViewById(R.id.btnCreateTrip);
//        datePicker=findViewById(R.id.dateTextView);
//        timePicker=findViewById(R.id.timeTextView);
//        tripName= findViewById(R.id.titleTextView);
//        startPoint= findViewById(R.id.txtStartPoint);
//        endPoint= findViewById(R.id.txtEndPoint);
//        roundTrip=findViewById(R.id.roundTrip);
//
//        if(getIntent().hasExtra("trip")){
//            trip = getIntent().getParcelableExtra("trip");
//            tripName.setText( trip.getTitle());
//            timePicker.setText(trip.getTime());
//            datePicker.setText(trip.getDate());
//            startPoint.setText(trip.getStartPoint().getAddress());
//            endPoint.setText(trip.getEndPoint().getAddress());
//        }else{
//            trip= new Trip();
//        }
//
//        //git current date and time
//        calendar = Calendar.getInstance();
//        int yy = calendar.get(Calendar.YEAR);
//        int mm = calendar.get(Calendar.MONTH);
//        int dd = calendar.get(Calendar.DAY_OF_MONTH);
//        Date today = calendar.getTime();
//
//        timePicker.setOnClickListener(v -> {
//            timePicker(v);
//
//        });
//
//
//        datePicker.setOnClickListener(v -> {
//            datePicker(v);
//
//        });
//
//        createTrip.setOnClickListener(v -> {
//
//            createTripDone(v);
////            Calendar calendar = Calendar.getInstance();
////            if (android.os.Build.VERSION.SDK_INT >= 23) {
////                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
////                        timePicker.getHour(), timePicker.getMinute(), 0);
////
////            } else {
////                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
////                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
////            }
////             timePicker(v);
////             datePicker(v);
//
//            //int id= Integer.parseInt(trip.getId());
//            //myAlarm = new MyAlarm(CreateTrip.this,t1Hours,t1Min,t1day,t1mounth,t1year,id);
////            int time= (int)Calendar.getInstance().getTimeInMillis();
////
////            myAlarm = new MyAlarm(CreateTrip.this,t1Hours,t1Min,t1day,t1mounth,t1year,time);
////            myAlarm.setAlarm();
//        });
//    }
//
//
//
//
//    public void timePicker(View view) {
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                // initialize hour and Mins
//                t1Hours = hourOfDay;
//                t1Min = minute;
//               // calendar.set(0, 0, 0, t1Hours, t1Min);
//                calendar.set(Calendar.HOUR_OF_DAY,t1Hours);
//                calendar.set(Calendar.MINUTE,t1Min);
//
//                Date d = calendar.getTime();
//
//                String hour = new SimpleDateFormat("HH:mm a").format(d);
//                //String hour = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + " " + ;
//                //set on btn
//                //Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
//                timePicker.setText(hour); // set the current time in text view
//            }
//        }, 12, 0, false
//        );
//        timePickerDialog.updateTime(t1Hours, t1Min);
//        timePickerDialog.show();
//
//    }
//
//    public void datePicker(View view) {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
//                new OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                       // monthOfYear=monthOfYear; //month start 0
//                        t1day = dayOfMonth;
//                        t1mounth = monthOfYear;
//                        t1year=year;
//                       // calendar.set(t1year, t1mounth, t1day,0,0);
//                        calendar.set(Calendar.DAY_OF_MONTH,t1day);
//                        calendar.set(Calendar.MONTH,t1mounth);
//                        calendar.set(Calendar.YEAR,t1year);
//
//                        Log.i(TAG, "onDateSet: Time"+calendar.getTime().toString());
//
//                        Date d = calendar.getTime();
//                        String date = new SimpleDateFormat("dd/MM/yyyy").format(d);
//
//                        Log.i(TAG, "onDateSet: Time D aaaaaaaaaaaaa"+d.toString());
////                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH);
////                        timePicker.getHour(), timePicker.getMinute(), 0);
//                        datePicker.setText(date);
//                    }
//                }, t1year, t1mounth, t1day);
//        datePickerDialog.updateDate(t1year, t1mounth, t1day);
//        try {
//            Date d;
//            if(trip != null && trip.getDate() != null){
//                d = new SimpleDateFormat("dd/MM/yyyy").parse(trip.getDate());
//            }else{
//                d = new Date();
//            }
//
//            datePickerDialog.getDatePicker().setMinDate(d.getTime());
//            datePickerDialog.show();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void  createTripDone(View view){
//
//        int time= (int)Calendar.getInstance().getTimeInMillis();
//       // myAlarm = new MyAlarm(CreateTrip.this,t1Hours,t1Min,t1day,t1mounth,t1year,time);
//
//        if(isValid()){
//            trip.setTitle( tripName.getText().toString());
//            Log.i(TAG, "createTripDone: ");
//            trip.setStartPoint(new Location("Ismaila", "30.5965", "32.2715"));
//            trip.setEndPoint(new Location("Cairo", "30.0444", "31.2357"));
//            trip.setTime((String) timePicker.getText());
//            trip.setDate((String) datePicker.getText());
//            trip.setStatus("upcoming");
//            trip.setId(String.valueOf(time));
//            MyAlarm.setAlarm(CreateTrip.this,trip,t1Hours,t1Min,t1day,t1mounth,t1year,time);
//
//
//
//            (new TripRepo()).addTrip(trip, new FirebaseRepoDelegate() {
//                @Override
//                public void success(String message) {
//                    Toast.makeText(CreateTrip.this, message, Toast.LENGTH_LONG).show();
//                    finish();
//                }
//
//                @Override
//                public void failed(String message) {
//                    Toast.makeText(CreateTrip.this, message, Toast.LENGTH_LONG).show();
//                }
//            });
//        }else {
//            Toast.makeText(this, "Please fill all fields first", Toast.LENGTH_LONG).show();
//        }
//
//
//    }
//
//
//    public void onCheckboxClicked(View view) {
//    }
//
//    public boolean isValid(){
//        return !(tripName.getText().toString().isEmpty() ||
//                timePicker.getText().toString().isEmpty() ||
//                datePicker.getText().toString().isEmpty());
//    }
//
//}