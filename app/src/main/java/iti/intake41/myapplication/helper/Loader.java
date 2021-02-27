package iti.intake41.myapplication.helper;

import android.app.ProgressDialog;
import android.content.Context;

public class Loader{
    ProgressDialog d;
    Context context;

    public Loader(Context context){
        this.context = context;
        d = new ProgressDialog(context);
        d.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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