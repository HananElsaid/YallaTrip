package iti.intake41.myapplication.modules.createtrip.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.AlarmManagerCompat;

import com.google.android.libraries.places.internal.m;

import java.util.Calendar;

import iti.intake41.myapplication.modules.reminder.MyAlarmReceiver;
import iti.intake41.myapplication.modules.reminder.ReminderTrip;
import iti.intake41.myapplication.modules.reminder.SnoozeBroadcastReciver;

import static androidx.core.content.ContextCompat.getSystemService;

public class MyAlarm {

    Context context;
    int hour;
    int minute;
    int day;
    int month;
    int year;
    //int id;

    public MyAlarm(Context context, int hour, int minute, int day, int month, int year) {
        this.context = context;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
    }

//    public MyAlarm(Context context, int hour, int minute) {
//        this.context = context;
//        this.hour = hour;
//        this.minute = minute;
//    }

    public void setAlarm() {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);  ///
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.YEAR,year);
        Intent intent1 = new Intent(context, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, 0); //not from user
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);



        ////importrant
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() , pendingIntent);
        } else {

            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() , pendingIntent);
        }
    }


//    public void cancelAlarm(){
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent myIntent = new Intent(context, MyAlarm.class);
//        Intent notificationIntent= new Intent(context,ReminderTrip.class);
//        PendingIntent pendingIntent= PendingIntent.getActivity(context,0,notificationIntent,0);
//        //        alarmManager.cancel(pendingIntent);
//    }
}