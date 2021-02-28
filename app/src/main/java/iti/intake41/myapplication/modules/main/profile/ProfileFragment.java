package iti.intake41.myapplication.modules.main.profile;

import android.os.Build;
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

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.NetworkClass;
import iti.intake41.myapplication.models.user.User;
import iti.intake41.myapplication.modules.login.view.LoginActivity;
import iti.intake41.myapplication.modules.tripsmap.MapTripsActivity;
import iti.intake41.myapplication.viewmodel.UserViewModel;

public class ProfileFragment extends Fragment {
    User user= new User();

    private UserViewModel profileViewModel;
    LinearLayout mapCard ,logout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //profileViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        profileViewModel = UserViewModel.getInstance(getContext());
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        //map card
        mapCard=root.findViewById(R.id.mapCard);
        //logout card
        logout= root.findViewById(R.id.logout);
        final TextView username = root.findViewById(R.id.userNameTV);
        final TextView email = root.findViewById(R.id.emailTV);

        profileViewModel.user.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                username.setText(user.getName());
                email.setText(user.getEmail());
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Navigator.gotoScreen(getActivity(), MapTripsActivity.class);
                }
            }
        });

        //logout and redirect to login activity
        logout.setOnClickListener(new View.OnClickListener() {
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