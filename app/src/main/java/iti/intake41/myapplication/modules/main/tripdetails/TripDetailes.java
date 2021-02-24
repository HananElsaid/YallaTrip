package iti.intake41.myapplication.modules.main.tripdetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.nambimobile.widgets.efab.FabOption;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Constants;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.UIHelper;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripRepo;
import iti.intake41.myapplication.models.trip.TripRepoInterface;
import iti.intake41.myapplication.models.trip.TripStatus;
import iti.intake41.myapplication.modules.floatingwidget.FloatWidgetService;

public class TripDetailes extends AppCompatActivity {

    //MARK: - UIComponents
    private TextView txtStartPoint, txtEndPoint, titleTextView,
            statusTextView, dateTextView, timeTextView;
    private FabOption editButton, startButton, cancelButton;

    //MARK: - Properties
    private Trip trip;


    TripRepoInterface repo;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detailes);
        initViews();
        repo = new TripRepo();
        Intent i = getIntent();
        if(i.hasExtra("trip")){
            trip = i.getParcelableExtra("trip");
            configureTrip();

        }else if(i.hasExtra("trip_id")){
            String id = i.getStringExtra("trip_id");
            getTripDetails(id);
        }
    }
    
    public void initViews(){
        titleTextView=findViewById(R.id.titleTextView);
        statusTextView=findViewById(R.id.statusTextView);
        txtStartPoint=findViewById(R.id.txtStartPoint);
        txtEndPoint=findViewById(R.id.txtEndPoint);
        dateTextView=findViewById(R.id.dateTextView);
        timeTextView=findViewById(R.id.timeTextView);
        editButton=findViewById(R.id.editButton);
        startButton=findViewById(R.id.startButton);
        cancelButton=findViewById(R.id.cancelButton);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void configureTrip(){
        titleTextView.setText(trip.getTitle());
        statusTextView.setText(trip.getStatus());
        txtStartPoint.setText(trip.getStartPoint().getAddress());
        txtEndPoint.setText(trip.getEndPoint().getAddress());
        dateTextView.setText(trip.getDate());
        timeTextView.setText(trip.getTime());

        TripStatus status = TripStatus.valueOf(trip.getStatus());
        int color;
        switch (status){
            case upcoming:
                color = R.color.orangeDark; break;
            case done:
                color = R.color.greenMid; break;
            default:
                color = R.color.redMid; break;

        }
        int c = ContextCompat.getColor(this,color);
        statusTextView.setTextColor(c);

        if(status == TripStatus.upcoming){
            editButton.setFabOptionEnabled(true);
            startButton.setFabOptionEnabled(true);
            cancelButton.setFabOptionEnabled(true);
        }
    }

    public void addNotes(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Navigator.navigateToNotes(this, trip.getId());
        }
    }

    public void deleteClicked(View view) {
        if(trip != null)
        repo.deleteTrip(trip.getId(), new FirebaseRepoDelegate() {

            @Override
            public void success(String message) {
                Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void failed(String message) {
                Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void editClicked(View view) {
        if(trip != null)
            Navigator.navigateToUpdateTrip(this, trip);
            finish();
    }

    public void startClicked(View view) {
        if(trip != null){
            trip.setStatus(TripStatus.done.toString());
            repo.updateTrip(trip, new FirebaseRepoDelegate() {
                @Override
                public void success(String message) {
                    Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failed(String message) {
                    Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
                }
            });
            UIHelper.startTrip(this, trip);
            checkPermission();
        }
    }

    public void cancelClicked(View view) {
        if(trip != null){
            trip.setStatus(TripStatus.cancelled.toString());
            repo.updateTrip(trip, new FirebaseRepoDelegate() {
                @Override
                public void success(String message) {
                    Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failed(String message) {
                    Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getTripDetails(String id){
        repo.getTripDetails(id, new FirebaseRepoDelegate() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public <T> void getObjSuccess(T obj) {
                trip = (Trip) obj;
                configureTrip();
            }

            @Override
            public void failed(String message) {
                Toast.makeText(TripDetailes.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, Constants.APP_PERMISSION_REQUEST);
        } else {
            viewWidgetButton();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.APP_PERMISSION_REQUEST && resultCode == RESULT_OK) {
            viewWidgetButton();
        } else {
            Toast.makeText(this, "Draw over other app permission not enable.", Toast.LENGTH_SHORT).show();
        }
    }
    void viewWidgetButton() {
        startService(new Intent(TripDetailes.this, FloatWidgetService.class));
        finish();
    }


}