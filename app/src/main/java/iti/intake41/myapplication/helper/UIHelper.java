package iti.intake41.myapplication.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.Trip;

public class UIHelper {

    public static Dialog creatDialog(Context context){
        // custom dialog
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.custom_alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnAdd =  dialog.findViewById(R.id.dialogButtonAdd);
        Button btnCancel =  dialog.findViewById(R.id.dialogButtonCancel);
        EditText etNoteText =  dialog.findViewById(R.id.dialogEditNoteText);
        // if button is clicked, close the custom dialog
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String noteText= etNoteText.getText().toString().trim();
                Toast.makeText(context,noteText,Toast.LENGTH_SHORT).show();
            }
        });
        // if button is clicked, close the custom dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static void startTrip(Context context, Trip trip){
        String to = trip.getEndPoint().getAddress();
        // Creates an Intent that will load a map of San Francisco
        //Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr="+to);
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+
                trip.getEndPoint().getLatitude()+","+trip.getEndPoint().getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

    public static Activity getActivity() {

        try {
            Class activityThreadClass = null;
            activityThreadClass = Class.forName("android.app.ActivityThread");

            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);

            Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
            if (activities == null)
                return null;

            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

}
