package iti.intake41.myapplication.modules.main.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.Trip;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final Context context;
    private List<Trip> items;
    private HomeAdapterDelegate delegate;

    private static final String TAG = "RecyclerView";

    public HomeAdapter(Context _context, HomeAdapterDelegate _delegate){
        delegate = _delegate;
        context = _context;
    }

    public void setItems(List<Trip> items){
        this.items = items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(recyclerview.getContext());
        View v = inflater.inflate(R.layout.row_layout, recyclerview, false);
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

        public TextView titleTextView;
        public TextView timeTextView;
        public TextView dayTextView;
        public TextView monthTextView;
        public LinearLayout linearLayout;
        public View layout;

        public ViewHolder(View v){
            super(v);
            layout = v;
            titleTextView = v.findViewById(R.id.titleTextView);
            timeTextView = v.findViewById(R.id.timeTextView);
            dayTextView = v.findViewById(R.id.dayTextView);
            monthTextView = v.findViewById(R.id.monthTextView);
            linearLayout = v.findViewById(R.id.row);
        }

        public void configure(Trip item){
            titleTextView.setText(item.getTitle());

//            Date date = item.getDate();
//            //String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
//            String day          = (String) DateFormat.format("dd",   date); // 20
//            String monthString  = (String) DateFormat.format("MMM",  date); // Jun
//            //String monthNumber  = (String) DateFormat.format("MM",   date); // 06
//            //String year         = (String) DateFormat.format("yyyy", date); // 2013
//            String time         = (String) DateFormat.format("hh:mm a", date); // 2013

//            monthTextView.setText(monthString);
//            dayTextView.setText(day);
//            timeTextView.setText(time);

            linearLayout.setOnClickListener((v)->{
                Toast.makeText(context, item.getTitle(), Toast.LENGTH_LONG).show();
                //Intent i = new Intent(context, DetailsActivity.class);
                //i.putExtra("item", item);
                //context.startActivity(i);
            });
            Log.i(TAG, "***** onBindViewHolder *****");
        }

    }

}

