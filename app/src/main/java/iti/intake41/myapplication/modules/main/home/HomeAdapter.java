package iti.intake41.myapplication.modules.main.home;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripStatus;
import iti.intake41.myapplication.modules.createtrip.view.MyAlarm;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final Context context;
    private List<Trip> items;
    private HomeAdapterDelegate delegate;

    private static final String TAG = "RecyclerView";

    public HomeAdapter(Context context, HomeAdapterDelegate delegate){
        this.delegate = delegate;
        this.context = context;
    }
    public void setItems(List<Trip> items){
        this.items = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(recyclerview.getContext());
        View v = inflater.inflate(R.layout.trip_row_layout, recyclerview, false);
        ViewHolder vh = new ViewHolder(v);
        Log.i(TAG, "==== onCreateViewHolder ====");
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.configure(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final Button startButton;
        private final TextView titleTextView, timeTextView, dayTextView, monthTextView;
        private final LinearLayout linearLayout;
        private final View layout;

        public ViewHolder(View v){
            super(v);
            layout = v;
            titleTextView = v.findViewById(R.id.titleTextView);
            timeTextView = v.findViewById(R.id.timeTextView);
            dayTextView = v.findViewById(R.id.dayTextView);
            monthTextView = v.findViewById(R.id.monthTextView);
            startButton = v.findViewById(R.id.startButton);
            linearLayout = v.findViewById(R.id.row);
        }

        public void configure(Trip item){
            titleTextView.setText(item.getTitle());
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(item.getDate());
                String month  = (String) DateFormat.format("MMM",  date); // Jun
                String day  = (String) DateFormat.format("dd",   date); // 20
                monthTextView.setText(month);
                dayTextView.setText(day);
                timeTextView.setText(item.getTime());

                if(item.getStatus().equals(TripStatus.upcoming.toString()))
                    startButton.setVisibility(View.VISIBLE);
                else
                    startButton.setVisibility(View.INVISIBLE);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            linearLayout.setOnClickListener((v)->{
                delegate.itemClicked(item);
            });

            startButton.setOnClickListener((v)->{
                delegate.startClicked(item);
                /////
                MyAlarm.cancelAlarm(itemView.getContext(),Integer.parseInt(item.getId()));
            });

            Log.i(TAG, "***** onBindViewHolder *****");
        }
    }

}

//            Date date = item.getDate();
//            //String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
//            String day          = (String) DateFormat.format("dd",   date); // 20
//            String monthString  = (String) DateFormat.format("MMM",  date); // Jun
//            //String monthNumber  = (String) DateFormat.format("MM",   date); // 06
//            //String year         = (String) DateFormat.format("yyyy", date); // 2013
//            String time         = (String) DateFormat.format("hh:mm a", date); // 2013