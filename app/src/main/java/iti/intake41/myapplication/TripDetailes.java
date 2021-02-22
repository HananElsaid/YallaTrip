package iti.intake41.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TripDetailes extends AppCompatActivity {

    TextView txtStartPoint,txtEndPoint;
    private FloatingActionButton more, edit,delete,navigate;
    private boolean isopen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detailes);
        more=findViewById(R.id.more_floating_action_button);
        edit=findViewById(R.id.edit_floating_button);
        delete=findViewById(R.id.delet_floating_button);
        navigate=findViewById(R.id.navigation_floating_button);

        txtStartPoint=findViewById(R.id.txtStartPoint);
        txtEndPoint=findViewById(R.id.txtEndPoint);



        isopen=false;

        more.setOnClickListener(v -> {
            if(isopen==true){
                more.setVisibility(View.VISIBLE);
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                navigate.setVisibility(View.VISIBLE);

                isopen=false;

            }else {
                more.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
                navigate.setVisibility(View.INVISIBLE);

                isopen=true;

            }
        });


        navigate.setOnClickListener(
                v -> {

//                    String a= txtStartPoint.getText().toString();
//                    String b= txtEndPoint.getText().toString();

                    String to = "cairo";
                    // Creates an Intent that will load a map of San Francisco
                    Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr="+to);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
        );

    }
}