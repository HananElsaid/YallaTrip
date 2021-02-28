package iti.intake41.myapplication.modules.main.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.UIHelper;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripStatus;
import iti.intake41.myapplication.models.user.UserRepoInterface;
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
    private UserRepoInterface userRepo;
    private TripStatus status = TripStatus.upcoming;
    private TripViewModel tripViewModel;
    private UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
        //Setup Recycler
        initViews(view);
        setupRecycler(view);
        setupTabs();
        setupViewModel();
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
        //tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);
        tripViewModel.setContext(getContext());
        tripViewModel.itemsList.observe(getViewLifecycleOwner(), items -> {
            updateItems(items);
        });
        tripViewModel.getTrips();

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
                    try {
                        Date date = new SimpleDateFormat("dd/MM/YYYY HH:mm a")
                                .parse(trip.getDate() + " " + trip.getTime());
                        if(date.after(new Date())){
                            System.out.println("date.after(new Date()");
                            trip.setStatus(TripStatus.done.toString());
                            tripViewModel.updateTrip(trip, () -> {
                                UIHelper.startTrip(getContext(), trip);
                                //checkPermission();
                            });
                        }else{
                            System.out.println("Toast Clicked");
                            Toast.makeText(getActivity().getApplicationContext(), "Invalid trip date, Please update it!", Toast.LENGTH_LONG);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

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

}