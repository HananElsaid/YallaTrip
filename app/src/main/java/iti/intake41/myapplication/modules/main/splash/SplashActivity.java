package iti.intake41.myapplication.modules.main.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.firebase.database.FirebaseDatabase;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Constants;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.NetworkClass;
import iti.intake41.myapplication.modules.main.MainActivity;
import iti.intake41.myapplication.modules.user.login.view.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPermission();
        //for enable caching
        if (FirebaseDatabase.getInstance() == null) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        ImageView imageView = findViewById(R.id.imageView);
        DrawableImageViewTarget splashScreen = new DrawableImageViewTarget(imageView);

//        String img = "https://media.giphy.com/media/MFCz5th6KXo5kE8XGh/giphy.gif";
        String img = "https://media.giphy.com/media/lMyMOixBA7YDdikqZy/giphy.gif";
        Glide.with(this).load(img).into(splashScreen);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /****** Create Thread that will sleep for 5 seconds****/
        Thread background = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void run() {
                try {
                    // Thread will sleep for 5 seconds

                    sleep(2 * 1000);
                    // After n seconds redirect to another screen
                    if (NetworkClass.getCurrentUser() != null)
                        Navigator.gotoScreen(SplashActivity.this, MainActivity.class);
                    else
                        Navigator.gotoScreen(SplashActivity.this, LoginActivity.class);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }

    public void checkPermission() {
   /*     int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, Constants.APP_PERMISSION_REQUEST);
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, Constants.APP_PERMISSION_REQUEST);
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, Constants.SYSTEM_ALERT_WINDOW_PERMISSION);
        } else {
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.APP_PERMISSION_REQUEST && resultCode == RESULT_OK) {
            return;
        } else {
            checkPermission();
            Toast.makeText(this, "Draw over other app permission not enable.", Toast.LENGTH_SHORT).show();
        }
    }

}