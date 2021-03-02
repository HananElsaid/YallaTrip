package iti.intake41.myapplication.modules.trip.tripdetails;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.nambimobile.widgets.efab.FabOption;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.UIHelper;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripStatus;
import iti.intake41.myapplication.modules.map.floatingwidget.FloatWidgetService;
import iti.intake41.myapplication.modules.reminder.MyAlarm;
import iti.intake41.myapplication.viewmodel.TripViewModel;

public class TripDetailes extends AppCompatActivity {

    public static String TAG="TripDET";
    //MARK: - UIComponents
    private TextView txtStartPoint, txtEndPoint, titleTextView,
            statusTextView, dateTextView, timeTextView;
    private FabOption editButton, startButton, cancelButton;

    //MARK: - Properties
    private Trip trip;
    private TripViewModel tripViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detailes);
        initViews();
        setupViewModel();

        Intent i = getIntent();
        if(i.hasExtra("trip")){
            trip = i.getParcelableExtra("trip");
            configureTrip();

        } else if (i.hasExtra("trip_id")) {
            String id = i.getStringExtra("trip_id");
            tripViewModel.getTripDetails(id);
        }
    }

    public void initViews() {
        titleTextView = findViewById(R.id.titleTextView);
        statusTextView = findViewById(R.id.statusTextView);
        txtStartPoint = findViewById(R.id.txtStartPoint);
        txtEndPoint = findViewById(R.id.txtEndPoint);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        editButton = findViewById(R.id.editButton);
        startButton = findViewById(R.id.startButton);
        cancelButton = findViewById(R.id.cancelButton);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void configureTrip() {
        titleTextView.setText(trip.getTitle());
        statusTextView.setText(trip.getStatus());
        txtStartPoint.setText(trip.getStartPoint().getAddress());
        txtEndPoint.setText(trip.getEndPoint().getAddress());
        dateTextView.setText(trip.getDate());
        timeTextView.setText(trip.getTime());

        TripStatus status = TripStatus.valueOf(trip.getStatus());
        int color;
        switch (status) {
            case upcoming:
                color = R.color.orangeDark;
                break;
            case done:
                color = R.color.greenMid;
                break;
            default:
                color = R.color.redMid;
                break;

        }
        int c = ContextCompat.getColor(this, color);
        statusTextView.setTextColor(c);

        if (status == TripStatus.upcoming) {
            editButton.setFabOptionEnabled(true);
            startButton.setFabOptionEnabled(true);
            cancelButton.setFabOptionEnabled(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupViewModel() {
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel.setContext(this);
        tripViewModel.selectedItem.observe(this, trip -> {
            this.trip = trip;
            configureTrip();
        });
    }

    public void addNotes(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Navigator.navigateToNotes(this, trip.getId());
        }
    }

    public void deleteClicked(View view) {
        if(trip != null)
            new AlertDialog.Builder(this)
                .setTitle("Delete Trip")
                .setMessage("Are you sure you want to delete this trip?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        delete();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void delete(){
        tripViewModel.deleteTrip(trip.getId(), () -> {
            MyAlarm.cancelAlarm(this,trip.getId().hashCode());
            finish();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void editClicked(View view) {
        if (trip != null)
            Navigator.navigateToUpdateTrip(this, trip);
        finish();
    }

    public void startClicked(View view) {
        if(trip != null){
            trip.setStatus(TripStatus.done.toString());
            tripViewModel.updateTrip(trip, () -> {
                UIHelper.startTrip(this, trip);
                viewWidgetButton();
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void cancelClicked(View view) {
        if (trip != null) {
            trip.setStatus(TripStatus.cancelled.toString());
            tripViewModel.updateTrip(trip, () -> {
                configureTrip();
            });
        }
//        MyAlarm.cancelAlarm(this,Integer.parseInt(trip.getId()));

    }

    void viewWidgetButton() {
        Intent intent = new Intent(TripDetailes.this, FloatWidgetService.class);
        intent.putExtra("tripID", trip.getId());
        intent.putExtra("tripName", trip.getTitle());
        startService(intent);
        finish();
    }

}