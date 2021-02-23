package iti.intake41.myapplication.helper;

import android.content.Context;
import android.content.Intent;

import iti.intake41.myapplication.createtrip.view.CreateTrip;
import iti.intake41.myapplication.creatnote.view.CreatNoteActivity;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.modules.view.tripdetails.TripDetailes;


public class Navigator {
    public static void gotoScreen(Context c1, Class<?> c2) {
        Intent n = new Intent(c1, c2);
        c1.startActivity(n);
    }

    public static void navigateToTripDetails(Context context, String tripId){
        Intent n = new Intent(context, TripDetailes.class);
        n.putExtra("trip_id", tripId);
        context.startActivity(n);
    }

    public static void navigateToTripDetails(Context context, Trip trip){
        Intent n = new Intent(context, TripDetailes.class);
        n.putExtra("trip", trip);
        context.startActivity(n);
    }

    public static void navigateToNotes(Context context, String tripId){
        Intent n = new Intent(context, CreatNoteActivity.class);
        n.putExtra("trip_id", tripId);
        context.startActivity(n);
    }

    public static void navigateToUpdateTrip(Context context, String tripId){
        Intent n = new Intent(context, CreateTrip.class);
        n.putExtra("trip_id", tripId);
        context.startActivity(n);
    }


}
