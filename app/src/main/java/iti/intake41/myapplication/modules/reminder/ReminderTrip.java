package iti.intake41.myapplication.modules.reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.UIHelper;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripStatus;
import iti.intake41.myapplication.viewmodel.TripViewModel;

public class ReminderTrip extends AppCompatActivity {

    Button startbtn, cancelbtn,snoozebtn;
    MediaPlayer mediaPlayer;
    TextView tripTitle;
    Trip trip;
    private TripViewModel tripViewModel;
    public int id;


    @Override
    protected void onStart() {
        super.onStart();
    }
    private void openAlarmRingTone() {
        mediaPlayer=MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_trip);
        startbtn=findViewById(R.id.start);
        cancelbtn =findViewById(R.id.cancel);
        snoozebtn=findViewById(R.id.snooze);
        tripTitle=findViewById(R.id.tripTitle);

        openAlarmRingTone();
//        myAlarm=new MyAlarm();
        setupViewModel();
        Intent x= getIntent();

        if(x.hasExtra("trip")){
            String json  = x.getStringExtra("trip");
            System.out.println(json);
            trip=new Gson().fromJson(json,Trip.class);
            //String trip = x.getStringExtra("trip");
//            if(trip != null)
            Log.i("trip", "onCreate: Trip"+ trip.getId());
            tripTitle.setText(trip.getTitle());
        }


        startbtn.setOnClickListener(v -> {
            mediaPlayer.stop();
            UIHelper.startTrip(this, trip);
            finish();
        });

        cancelbtn.setOnClickListener(v -> {
            mediaPlayer.stop();
             //cancel trip not alarm
            cancel();
        });

        snoozebtn.setOnClickListener(v -> {
            mediaPlayer.stop();
            Toast.makeText(this,"Reminder set",Toast.LENGTH_LONG).show();
            createNotification();
            finish();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupViewModel() {
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel.setContext(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void cancel() {
        if (trip != null) {
            trip.setStatus(TripStatus.cancelled.toString());
            tripViewModel.updateTrip(trip, () -> {
                finish();
            });
        }
    }

    private  void createNotification(){

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "notifyme";
            CharSequence name = "YallaTrip";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);

            mChannel.canShowBadge();
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 100, 200, 200, 400});
            mChannel.setShowBadge(false);
            mChannel.setLockscreenVisibility(importance);
            notificationManager.createNotificationChannel(mChannel);
            Log.i("TAG", "createNotification: ");
            Toast.makeText(this, "notification created", Toast.LENGTH_LONG).show();

            Intent notificationIntent= new Intent(ReminderTrip.this,ReminderTrip.class);
            PendingIntent pendingIntent= PendingIntent.getActivity(ReminderTrip.this,0,notificationIntent,0);
            notificationManager.notify(2,new NotificationCompat.Builder(ReminderTrip.this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setOngoing(true) //outocancle ->
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setContentTitle("YallaTrip")
                    .setContentText("Start your Trip Now")
                    .build());
        }
    }
}
