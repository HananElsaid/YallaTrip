package iti.intake41.myapplication.modules.reminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.modules.createtrip.view.CreateTrip;

public class SnoozeBroadcastReciver extends BroadcastReceiver {
    int NOTIFICATION_ID = 234;
    String CHANNEL_ID = "notifyme";
    @Override
    public void onReceive(Context context, Intent intent) {

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyme")
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle("YallTrip")
//                .setContentText("Time For your Trip")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
//        notificationManagerCompat.notify(200,builder.build());


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setOngoing(true) //outocancle ->
                .setAutoCancel(true)
                .setContentTitle("YallaTrip")
                .setContentText("Start your Trip Now");
        Intent resultIntent = new Intent(context, ReminderTrip.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(CreateTrip.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
