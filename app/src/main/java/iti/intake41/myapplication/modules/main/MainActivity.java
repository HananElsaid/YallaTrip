package iti.intake41.myapplication.modules.main;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.modules.trip.createtrip.view.CreateTrip;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setBackground(null);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            //when screen is black but not locked it will light-up
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(this)) {
                checkDrawOverAppsPermissionsDialog();
            }
        }
        runBackgroundPermissions();

        BottomAppBar appbar = findViewById(R.id.bottomAppBar);
        MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) appbar.getBackground();
        bottomBarBackground.setShapeAppearanceModel(
                bottomBarBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setTopRightCorner(CornerFamily.ROUNDED,60)
                        .setTopLeftCorner(CornerFamily.ROUNDED,60)
                        .build());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_more)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void createTripClicked(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Navigator.gotoScreen(this, CreateTrip.class);
        }
    }

    private void checkDrawOverAppsPermissionsDialog(){
        new AlertDialog.Builder(this).setTitle("Permission request").setCancelable(false).setMessage("please allow Draw Over Apps permission to be able to use application properly")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drawOverAppPermission();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                errorWarningForNotGivingDrawOverAppsPermissions();
            }
        }).show();
    }

    private void errorWarningForNotGivingDrawOverAppsPermissions(){
        new AlertDialog.Builder(this).setTitle("Warning").setCancelable(false).setMessage("Unfortunately the display over other apps permission" +
                " is not granted so the application might not behave properly \nTo enable this permission kindly restart the application" )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).show();
    }

    public void runBackgroundPermissions() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                startActivity(intent);
            } else if (Build.BRAND.equalsIgnoreCase("Honor") || Build.BRAND.equalsIgnoreCase("HUAWEI")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                startActivity(intent);
            }
        }
    }

    public void drawOverAppPermission (){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 80);
            }
        }
    }
}