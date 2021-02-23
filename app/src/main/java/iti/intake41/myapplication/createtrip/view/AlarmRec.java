package iti.intake41.myapplication.createtrip.view;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;



//class extending the Broadcast Receiver
public class AlarmRec extends BroadcastReceiver {

    //the method will be fired when the alarm is triggerred
    @Override
    public void onReceive(Context context, Intent intent) {

        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday
        //Intent intent1
        MediaPlayer mediaPlayer= MediaPlayer.create(context,
                Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        Intent i = new Intent(context, AlertDialog.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        Log.d("MyAlarmBelal", "Alarm just fired");
    }


}