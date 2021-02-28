package iti.intake41.myapplication.modules.user.profile;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.NetworkClass;
import iti.intake41.myapplication.models.user.User;
import iti.intake41.myapplication.modules.user.login.view.LoginActivity;
import iti.intake41.myapplication.modules.map.tripsmap.MapTripsActivity;
import iti.intake41.myapplication.viewmodel.UserViewModel;

public class ProfileFragment extends Fragment {
    User user= new User();

    private UserViewModel profileViewModel;
    LinearLayout mapCard ,logout;
    TextView username, email, title;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(root);
        setupViewModel();
        addActions();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initViews(View root){
        mapCard=root.findViewById(R.id.mapCard);
        logout= root.findViewById(R.id.logout);
        username = root.findViewById(R.id.userNameTV);
        email = root.findViewById(R.id.emailTV);
        title = root.findViewById(R.id.userNameTextView);
    }

    public void setupViewModel(){
        profileViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        profileViewModel.user.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                username.setText(user.getName());
                title.setText(user.getName());
                email.setText(user.getEmail());
            }
        });
        profileViewModel.getUser();
    }

    public void addActions(){
        //open map activity
        mapCard.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Navigator.gotoScreen(getActivity(), MapTripsActivity.class);
                }
            }
        });

        //logout and redirect to login activity
        logout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                NetworkClass.firebaseLogout();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Navigator.gotoScreen(getActivity(), LoginActivity.class);
                }
                getActivity().finish();
            }
        });
    }
}