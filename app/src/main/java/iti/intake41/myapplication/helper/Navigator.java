package iti.intake41.myapplication.helper;

import android.content.Context;
import android.content.Intent;


public class Navigator {
    public static void gotoScreen(Context c1, Class<?> c2) {
        Intent n = new Intent(c1, c2);
        c1.startActivity(n);
    }


}
