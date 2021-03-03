package iti.intake41.myapplication.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import iti.intake41.myapplication.R;

public class Loader{
    ProgressDialog d;
    Context context;

    public Loader(Context context){
        this.context = context;
        d = new ProgressDialog(context, R.style.MyGravity);
        d.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setProgressDrawable(new ColorDrawable(Color.WHITE));
        d.setMax(1);
    }

    public void start(){
        if(NetworkClass.isNetworkConnected(context))
            d.show();
    }

    public void stop(){
        if(d.isShowing())
            d.dismiss();
    }
}