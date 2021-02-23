package iti.intake41.myapplication.modules.main.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.NetworkClass;
import iti.intake41.myapplication.login.view.LoginActivity;
import iti.intake41.myapplication.models.user.User;
import iti.intake41.myapplication.tripsmap.MapTripsActivity;

public class ProfileFragment extends Fragment {
    User user= new User();

    private ProfileViewModel profileViewModel;
    LinearLayout mapCard ,logout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        //map card
        mapCard=root.findViewById(R.id.mapCard);
        //logout card
        logout= root.findViewById(R.id.logout);
        final TextView username = root.findViewById(R.id.userNameTV);
        final TextView email = root.findViewById(R.id.emailTV);

//        username.setText(user.getName());
//        email.setText(user.getEmail());

        profileViewModel.getNameText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                username.setText(s);
            }
        });

        profileViewModel.getEmailText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                email.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //open map activity
        mapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.gotoScreen(getActivity(), MapTripsActivity.class);
            }
        });

        //logout and redirect to login activity
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkClass.firebaseLogout();
                Navigator.gotoScreen(getActivity(), LoginActivity.class);
                getActivity().finish();
            }
        });

    }
}