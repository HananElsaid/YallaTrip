package iti.intake41.myapplication.modules.main.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.UIHelper;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripStatus;
import iti.intake41.myapplication.modules.map.floatingwidget.FloatWidgetService;
import iti.intake41.myapplication.modules.reminder.MyAlarm;
import iti.intake41.myapplication.viewmodel.TripViewModel;
import iti.intake41.myapplication.viewmodel.UserViewModel;

public class HomeFragment extends Fragment{

    //MArk: - UIComponents
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView userNameTextView;
    private TabLayout tabLayout;

    //Mark :- Properties
    private HomeAdapter adapter;
    private List<Trip> items;
    private TripStatus status = TripStatus.upcoming;
    private TripViewModel tripViewModel;
    private UserViewModel userViewModel;
    private static final String TAG = "HomeFragment : ";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    public void initViews(View view) {
        userNameTextView = view.findViewById(R.id.userNameTextView);
        recyclerView = view.findViewById(R.id.recyclerView);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");

        //Setup Recycler
        initViews(view);
        setupRecycler(view);
        setupTabs();
        setupViewModel();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        tripViewModel.getTrips();
    }

    private void setupRecycler(View view) {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupTabs(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                status= getStatus(tab.getPosition());
                if(items != null)
                    updateItems();
            }
            public void onTabUnselected(TabLayout.Tab tab) {}
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupViewModel(){
        Log.i(TAG, "setupViewModel: ");
        //tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);
        tripViewModel.setContext(getContext());
        tripViewModel.itemsList.observe(getViewLifecycleOwner(), items -> {
            updateItems(items);
        });


        //userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.user.observe(getViewLifecycleOwner(), user -> {
            userNameTextView.setText(user.getName());
        });
        userViewModel.getUser();
    }

    private void configureViews() {
        adapter = new HomeAdapter(getContext(), new HomeAdapterDelegate() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void itemClicked(Trip trip) {
                System.out.println("Item Clicked");
                Navigator.navigateToTripDetails(getContext(), trip);

            }

            @Override
            public void startClicked(Trip trip) {
                if(trip != null){
                            trip.setStatus(TripStatus.done.toString());
                            tripViewModel.updateTrip(trip, () -> {
                                UIHelper.startTrip(getContext(), trip);
                                MyAlarm.cancelAlarm(getContext(), trip.getId().hashCode());
                                viewWidgetButton(trip);
                            });

                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void updateItems(List<Trip> tripList) {
        if (adapter == null) {
            configureViews();
        }
        this.items = tripList;
        adapter.setItems(getStatusItems());
        adapter.notifyDataSetChanged();
    }

    public void updateItems() {
        adapter.setItems(getStatusItems());
        adapter.notifyDataSetChanged();
    }

    List<Trip> getStatusItems(){
        System.out.println(status.toString());
        List<Trip> trips = new ArrayList();
       for(Trip trip: items){
           if(trip.getStatus().equals(status.toString())){
               trips.add(trip);
           }
       }

       return trips;
    }

    TripStatus getStatus(int pos){
        TripStatus status;
        switch (pos){
            case 0:
               status = TripStatus.upcoming;
               break;
            case 1:
                status = TripStatus.done;
                break;
            default:
                status = TripStatus.cancelled;
        }
        return status;
    }
    void viewWidgetButton(Trip trip) {
        Intent intent = new Intent(getActivity(), FloatWidgetService.class);
        intent.putExtra("tripID", trip.getId());
        intent.putExtra("tripName", trip.getTitle());
        getContext().startService(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}