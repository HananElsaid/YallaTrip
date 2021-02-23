package iti.intake41.myapplication.modules.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripRepo;
import iti.intake41.myapplication.models.trip.TripRepoInterface;
import iti.intake41.myapplication.models.trip.TripStatus;
import iti.intake41.myapplication.models.user.User;
import iti.intake41.myapplication.models.user.UserRepo;
import iti.intake41.myapplication.models.user.UserRepoInterface;

public class HomeFragment extends Fragment implements HomeAdapterDelegate{

    //MArk: - UIComponents
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView userNameTextView;
    private TabLayout tabLayout;

    //Mark :- Properties
    private HomeAdapter adapter;
    private List<Trip> items;
    private TripRepoInterface tripRepo;
    private UserRepoInterface userRepo;
    private TripStatus status = TripStatus.upcoming;

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
        userRepo = new UserRepo();
        tripRepo = new TripRepo();
        initViews(view);
        setupRecycler(view);
        setupTabs();
        getUser();
        getItems();
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
                updateItems();
            }
            public void onTabUnselected(TabLayout.Tab tab) {}
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void configureViews() {
        adapter = new HomeAdapter(getContext(), new HomeAdapterDelegate() {
            @Override
            public void itemClicked(String id) {

            }

            @Override
            public void deleteClicked(String id) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                Toast.makeText(getContext(), "on Move", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                Toast.makeText(getContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
//                //Remove swiped item from list and notify the RecyclerView
//                int position = viewHolder.getAdapterPosition();
//                items.remove(position);
//                adapter.notifyItemRemoved(position);
//
//            }
//        };


//        SwipeHelper swipeHelper = new SwipeHelper(this.getContext(), recyclerView) {
//            @Override
//            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
//                underlayButtons.add(new SwipeHelper.UnderlayButton(
//                        "Delete",
//                        0,
//                        Color.parseColor("#FF3C30"),
//                        new SwipeHelper.UnderlayButtonClickListener() {
//                            @Override
//                            public void onClick(int pos) {
//                                // TODO: onDelete
//                            }
//                        }
//                ));
//
//                underlayButtons.add(new SwipeHelper.UnderlayButton(
//                        "Transfer",
//                        0,
//                        Color.parseColor("#FF9502"),
//                        new SwipeHelper.UnderlayButtonClickListener() {
//                            @Override
//                            public void onClick(int pos) {
//                                // TODO: OnTransfer
//                            }
//                        }
//                ));
//                underlayButtons.add(new SwipeHelper.UnderlayButton(
//                        "Unshare",
//                        0,
//                        Color.parseColor("#C7C7CB"),
//                        new SwipeHelper.UnderlayButtonClickListener() {
//                            @Override
//                            public void onClick(int pos) {
//                                // TODO: OnUnshare
//                            }
//                        }
//                ));
//            }
//        };
//
//        swipeHelper.attachSwipe();
//        recyclerView.setAdapter(adapter);

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



    void getItems() {
        tripRepo.getTrips(new FirebaseRepoDelegate() {
            @Override
            public <T> void getListSuccess(List<T> list) {
                updateItems((List<Trip>) list);
            }

            @Override
            public void failed(String message) {
                showToast(message);
            }
        });
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


    void getUser(){
        userRepo.getUser(new FirebaseRepoDelegate() {
            @Override
            public <T> void getObjSuccess(T obj) {
                userNameTextView.setText(((User) obj).getName());
            }
            @Override
            public void failed(String message) {
                showToast(message);
            }
        });
    }

    @Override
    public void itemClicked(String id) {
        System.out.println("Item Clicked");
        Navigator.navigateToTripDetails(this.getContext(), id);
    }

    @Override
    public void deleteClicked(String id) {
        tripRepo.deleteTrip(id, new FirebaseRepoDelegate() {
            @Override
            public void success(String message) {
                getItems();
            }
            @Override
            public void failed(String message) {
               Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showToast(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}