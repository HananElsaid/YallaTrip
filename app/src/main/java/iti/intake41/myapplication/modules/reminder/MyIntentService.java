//package iti.intake41.myapplication.modules.reminder;
//
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.media.MediaPlayer;
//import android.provider.Settings;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.JobIntentService;
//
//public class MyIntentService extends JobIntentService {
//    static final int JOB_ID = 1000;
//
//    MyAlarm broadCast;
//    MediaPlayer mediaPlayer;
//
////    static void enqueueWork(Context context, Intent work){
////        enqueueWork(context, MyIntentService.class,JOB_ID,work);
////    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        IntentFilter filter = new IntentFilter("com.example.SendBroadCast");
//        broadCast = new MyAlarm();
//        registerReceiver(broadCast,filter);
//    }
//
//    @Override
//    protected void onHandleWork(@NonNull Intent intent) {
//
//
//        openAlarmRingTone();
//
//        intent = new Intent();
//        intent.setAction("com.example.SendBroadCast");
//        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//        sendBroadcast(intent);
//
//    }
//    public void openAlarmRingTone() {
//        mediaPlayer= MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
//        mediaPlayer.start();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(broadCast);
//
//    }
//
//    private void unregisterReceiver(MyAlarm broadCast) {
//    }
//
//}