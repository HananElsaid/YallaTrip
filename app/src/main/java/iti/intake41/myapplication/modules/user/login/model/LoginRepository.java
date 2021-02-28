package iti.intake41.myapplication.modules.user.login.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import iti.intake41.myapplication.helper.NetworkClass;
import iti.intake41.myapplication.modules.user.login.viewmodel.LoginViewModel;

public class LoginRepository {

    LoginViewModel loginViewModel;
    FirebaseAuth mAuth;
    //DatabaseReference mDatabase;

    public LoginRepository(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        mAuth = FirebaseAuth.getInstance();

    }

    private void verifyEmail() {
        FirebaseUser currentUser = NetworkClass.getCurrentUser();
        if (currentUser!=null&&currentUser.isEmailVerified()) {
            //loginViewModel.loginSuccessfully();
            loginViewModel.display("login sucessfully");
        } else
            loginViewModel.display("you should verify your email");
    }

    public void login(final String email, final String password) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            verifyEmail();
                        }
                        else loginViewModel.display(task.getException().getMessage().toString());
                    }
                });
            }
        }.start();

    }
}
