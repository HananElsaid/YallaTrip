package iti.intake41.myapplication.modules.map.searchplace;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.Place.model.Place;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private final Context context;
    private List<Place> items = new ArrayList<>();
    private PlaceAdapterDelegate delegate;

    private static final String TAG = "RecyclerView";

    public PlaceAdapter(Context context, PlaceAdapterDelegate delegate){
        this.context = context;
        this.delegate = delegate;
    }

    public void setItems(List<Place> items){
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(recyclerview.getContext());
        View v = inflater.inflate(R.layout.place_row_layout, recyclerview, false);
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

        //MARK: - UIComponents
        public TextView titleTextView;
        public TextView addressTextView;
        public TextView categoryTextView;
        public LinearLayout linearLayout;
        public View layout;

        public ViewHolder(View v){
            super(v);
            layout = v;
            titleTextView = v.findViewById(R.id.titleTextView);
            addressTextView = v.findViewById(R.id.addressTextView);
            categoryTextView = v.findViewById(R.id.categoryTextView);
            linearLayout = v.findViewById(R.id.row);
        }

        public void configure(Place item){
            titleTextView.setText(item.getTitle());
            addressTextView.setText(item.getVicinity());
            categoryTextView.setText(item.getCategoryTitle());
            linearLayout.setOnClickListener((v)->{
                delegate.itemClicked(item);
            });
        }

    }

}

