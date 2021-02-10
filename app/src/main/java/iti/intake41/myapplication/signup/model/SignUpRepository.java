package iti.intake41.myapplication.signup.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import iti.intake41.myapplication.signup.viewmodel.SignUpViewModel;

public class SignUpRepository {
    FirebaseAuth auth;
    DatabaseReference dbRef;
    SignUpViewModel signViewModel;


    public SignUpRepository(SignUpViewModel signViewModel) {
        this.signViewModel = signViewModel;
        auth = FirebaseAuth.getInstance();

        //dbRef = FirebaseDatabase.getInstance().getReference().child("users");
    }


    public void signUp(final String email, final String password) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    verifyEmail();
                                    String userID = auth.getCurrentUser().getUid();
                                    //DatabaseReference currentUserDB = dbRef.child(userID);
                                    //currentUserDB.child("Name").setValue();
                                    signViewModel.getResponse().setValue("successful");
                                   // signViewModel.display("successful");
                                } else {
                                    //signViewModel.display(task.getException().getMessage().toString());
                                    signViewModel.getResponse().setValue(task.getException().getMessage());
                                }
                            }
                        });
            }
        }.start();

    }

    private void verifyEmail() {
        FirebaseUser currentUser = auth.getCurrentUser();
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    signViewModel.getResponse().setValue("successful");
                } else
                    signViewModel.getResponse().setValue(task.getException().toString());
            }
        });

    }
}
