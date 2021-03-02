package iti.intake41.myapplication.modules.reminder;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import iti.intake41.myapplication.models.Trip;

//class extending the Broadcast Receiver
public class MyAlarmReceiver extends BroadcastReceiver {

    //the method will be fired when the alarm is fired
    @Override
    public void onReceive(Context context, Intent intent) {

        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday

//        Intent intent1 = new Intent("android.intent.action.MAIN");
//        intent1.setClass(context, ReminderTrip.class);
//
//        Intent intent1 = new Intent(context, ReminderTrip.class);

        //String trip= intent.getStringExtra("trip");
        Trip trip= intent.getParcelableExtra("trip");

        Intent start = new Intent();

              // Intent start = new Intent("android.intent.action.MAIN");
                start.setClass(context, ReminderTrip.class);
                start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                start.putExtra("trip",trip);
                Log.i("TripTitle", "onReceive: Trip "+trip);
                context.startActivity(start);
                Log.e("alarm", "Alarm just fired");
    }
}