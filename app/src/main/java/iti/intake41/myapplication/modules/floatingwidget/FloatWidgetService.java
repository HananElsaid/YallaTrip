package iti.intake41.myapplication.modules.floatingwidget;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.modules.creatnote.notedialog.NoteDialogueActivity;

public class FloatWidgetService extends Service {
    private WindowManager mWindowManager;
    private View mFloatingWidget;
    //trip details
    String TripID, TripName;

    public FloatWidgetService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        TripID = intent.getStringExtra("tripID");
        TripName = intent.getStringExtra("tripName");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mFloatingWidget = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        //getting windows services and adding the floating view to it
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingWidget, params);

        //getting the collapsed and expanded view from the floating view
        final View collapsedView = mFloatingWidget.findViewById(R.id.collapse_view);
        final View expandedView = mFloatingWidget.findViewById(R.id.expanded_container);
        ImageView closeButtonCollapsed = (ImageView) mFloatingWidget.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });

        ImageView closeButton = (ImageView) mFloatingWidget.findViewById(R.id.close_button);
        //adding click listener to close button and expanded view
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
                /*collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);*/
            }
        });
        //adding an touchlistener to make drag movement of the floating widget
        mFloatingWidget.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);
                        if (Xdiff < 10 && Ydiff < 10) {
                            Intent openNoteDialogue = new Intent(getApplicationContext(), NoteDialogueActivity.class);

                            openNoteDialogue.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            openNoteDialogue.putExtra("tripID", TripID);
                            openNoteDialogue.putExtra("tripName", TripName);
                            
                            getApplicationContext().startActivity(openNoteDialogue);

                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingWidget, params);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingWidget != null) mWindowManager.removeView(mFloatingWidget);
    }
}