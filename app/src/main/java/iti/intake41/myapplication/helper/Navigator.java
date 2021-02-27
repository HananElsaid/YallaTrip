package iti.intake41.myapplication.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.modules.createtrip.view.CreateTrip;
import iti.intake41.myapplication.modules.creatnote.view.CreatNoteActivity;
import iti.intake41.myapplication.modules.main.tripdetails.TripDetailes;


public class Navigator {
    public static void gotoScreen(Context c1, Class<?> c2) {
        Intent n = new Intent(c1, c2);
        c1.startActivity(n);
        //c1.startActivity(n, ActivityOptions.makeSceneTransitionAnimation((Activity) c1).toBundle());
    }

    public static void navigateToTripDetails(Context context, String tripId){
        Intent n = new Intent(context, TripDetailes.class);
        n.putExtra("trip_id", tripId);
        context.startActivity(n);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void navigateToTripDetails(Context context, Trip trip){
        Intent n = new Intent(context, TripDetailes.class);
        n.putExtra("trip", trip);
        context.startActivity(n);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void navigateToNotes(Context context, String tripId){
        Intent n = new Intent(context, CreatNoteActivity.class);
        n.putExtra("trip_id", tripId);
        context.startActivity(n);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void navigateToUpdateTrip(Context context, Trip trip){
        Intent n = new Intent(context, CreateTrip.class);
        n.putExtra("trip", trip);
        context.startActivity(n);
    }


}
