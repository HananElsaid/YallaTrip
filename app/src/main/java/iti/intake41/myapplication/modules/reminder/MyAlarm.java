package iti.intake41.myapplication.modules.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Calendar;

import iti.intake41.myapplication.models.Trip;

public class MyAlarm {
    public static String TAG = "TAG";

    public MyAlarm(){}

    public static void setAlarm(Context context, Trip trip ,int hour, int minute, int day, int month, int year) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.YEAR,year);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(context, MyAlarmReceiver.class);
        intent1.putExtra("trip", new Gson().toJson(trip));
        //intent1.putExtra("trip", trip);
         //intent1.setAction("reminder.MyAlarmReceiver");

//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,id, intent1, 0); //not from user
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,trip.getId().hashCode(), intent1, 0); //not from user
        Log.i(TAG, "add Alarm: "+calendar.getTime().toString());

        ////importrant
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() , pendingIntent);
        } else {
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() , pendingIntent);
        }
    }

   // for cancel alarm
    public static void cancelAlarm(Context context, int id){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.i(TAG, "cancelAlarm: "+id);
        Intent intent1 = new Intent(context, MyAlarmReceiver.class);
        intent1.putExtra("id",id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,id, intent1, 0); //not from user
        alarmManager.cancel(pendingIntent);
    }
}