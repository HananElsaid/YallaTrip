package iti.intake41.myapplication.modules.trip.createtrip.model;

import iti.intake41.myapplication.modules.trip.createtrip.viewmodel.CreateTripViewModel;

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
