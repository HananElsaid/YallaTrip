package iti.intake41.myapplication.modules.map.searchplace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.Place.model.Place;
import iti.intake41.myapplication.viewmodel.PlaceViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceSearchFragment extends Fragment implements  PlaceAdapterDelegate{

    //MARK: - UIComponents
    private RecyclerView recyclerView;
    private SearchView searchView;

    //MARK - Properties
    private PlaceAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private PlaceViewModel viewModel;

    //MARK: - Properties
    private static final String SEARCH_TEXT = "search";
    private String searchText;
    private PlaceAdapterDelegate delegate;

    public PlaceSearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PlaceSearchFragment newInstance(String searchText, PlaceAdapterDelegate delegate) {
        PlaceSearchFragment fragment = new PlaceSearchFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_TEXT, searchText);
        fragment.setArguments(args);
        fragment.delegate = delegate;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchText = getArguments().getString(SEARCH_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_search, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycler();
        configureViews();
        setupViewModel();
        setupSearchView();
    }

    public void initViews(View view){
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recycler_view);

    }

    public void setupSearchView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.onQueryTextSubmit(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                viewModel.onQueryTextChange(text);
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                close();
                return true;
            }
        });

        if(!searchText.isEmpty()){
            searchView.setQuery(searchText, true);
        }

        //Set SearchView Focus
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
    }

    public void setupViewModel(){
        viewModel = new ViewModelProvider(this).get(PlaceViewModel.class);
        viewModel.itemsList.observe(getViewLifecycleOwner(), items -> {
            updateItems(items);
        });
    }

    //MARK: - Methods
    private void setupRecycler(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void configureViews(){
        adapter = new PlaceAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);
    }

        //MARK: - Implement ViewDelegate Methods
        @Override
        public void itemClicked(Place place) {
            delegate.itemClicked(place);
            close();

        }


        public void updateItems(List<Place> items) {
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }

        public void showToast(String message) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.dispose();
    }

    public void close(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(PlaceSearchFragment.this).commit();
    }
}