package iti.intake41.myapplication.createtrip.view;

import android.app.Dialog;
import android.os.Bundle;
import android.content.DialogInterface;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.note.Note;

public class AlartDialog extends AppCompatDialogFragment {
    Trip trip;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        trip=new Trip();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(trip.getTitle())
                .setMessage("this time for your" +trip.getTitle()+"Trip")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                                 AlartDialog.finish();

                    }

//                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
               });
        return builder.create();
    }
}

// .setOnClickListener("dismiss",new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        })