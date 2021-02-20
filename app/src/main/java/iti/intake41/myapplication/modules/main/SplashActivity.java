package iti.intake41.myapplication.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import iti.intake41.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.imageView);
        DrawableImageViewTarget splashScreen = new DrawableImageViewTarget(imageView);

        //String img = "https://media.giphy.com/media/MFCz5th6KXo5kE8XGh/giphy.gif";
        String img = "https://media.giphy.com/media/lMyMOixBA7YDdikqZy/giphy.gif";
        Glide.with(this).load(img).into(splashScreen);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /****** Create Thread that will sleep for 5 seconds****/
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(3*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}