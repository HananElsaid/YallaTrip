package iti.intake41.myapplication.modules.main.ui.home;

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

import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.Trip;
import iti.intake41.myapplication.models.trip.TripRepo;

public class HomeFragment extends Fragment {

    //MArk: - UIComponents
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView userNameTextView;

    //Mark :- Properties
    private HomeAdapter adapter;
    private List<Trip> items;
    private TripRepo repo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    public void initViews(View view) {
        userNameTextView = view.findViewById(R.id.userNameTV);
        recyclerView = view.findViewById(R.id.profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Setup Recycler
        repo = new TripRepo();
        initViews(view);
        setupRecycler(view);
        getItems();
    }

    private void setupRecycler(View view) {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
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
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }

    void getItems() {

        repo.getTrips(new FirebaseRepoDelegate() {
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
//
//    void getUser(){
//        repo.getUser(new FirebaseRepoDelegate() {
//
//            @Override
//            public void getUserSuccess(User user) {
//                //userNameTextView.setText(user.name);
//            }
//
//            @Override
//            public void failed(String message) {
//                showToast(message);
//            }
//        });
//    }

    public void itemClicked(String id) {
//        Intent i = new Intent(this, DetailsActivity.class);
//        i.putExtra("id", id);
//        this.startActivity(i);
    }

    public void deleteClicked(String id) {
        //presenter.deleteItem(id);
    }

    public void showToast(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}