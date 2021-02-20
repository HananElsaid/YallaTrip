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
}
