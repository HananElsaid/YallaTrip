package iti.intake41.myapplication.modules.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.Location;
import iti.intake41.myapplication.models.trip.TripRepo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setBackground(null);

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


        Trip trip2 = new Trip("Travel to Ismailia", "12 Dec, 2020", "5:00 am", "done",
                new Location("Ismailia, Egypt", "30.571721", "32.369218"),
                new Location("Cairo, Egypt", "30.031055", "31.236319"));

        (new TripRepo()).addTrip(trip2, new FirebaseRepoDelegate() {
            @Override
            public void success(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        Trip trip = new Trip("Travel to Dahab", "12 Dec, 2020", "5:00 am", "cancelled",
                new Location("Ismailia, Egypt", "30.571721", "32.369218"),
                new Location("Cairo, Egypt", "30.031055", "31.236319"));

        (new TripRepo()).addTrip(trip, new FirebaseRepoDelegate() {
            @Override
            public void success(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        Trip trip1 = new Trip("Travel to Assuit", "12 Dec, 2020", "5:00 am", "upcoming",
                new Location("Ismailia, Egypt", "30.571721", "32.369218"),
                new Location("Cairo, Egypt", "30.031055", "31.236319"));

        (new TripRepo()).addTrip(trip1, new FirebaseRepoDelegate() {
            @Override
            public void success(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}