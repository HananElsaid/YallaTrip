package iti.intake41.myapplication.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.note.NoteRepo;

public class UIHelper {
   static NoteRepo noteRepo;

    public static Dialog creatDialog(Context context,String tripId){
        noteRepo=new NoteRepo();
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
               noteRepo.addNote(tripId, noteText, new FirebaseRepoDelegate() {
                   @Override
                   public void success(String message) {
                       Toast.makeText(context,"upload note success",Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void failed(String message) {
                       Toast.makeText(context,"upload note failed",Toast.LENGTH_SHORT).show();
                   }
               });

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
}
