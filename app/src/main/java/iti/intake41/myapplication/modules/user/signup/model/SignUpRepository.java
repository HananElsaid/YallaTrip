package iti.intake41.myapplication.modules.user.signup.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpRepository implements SignUpModelInterface {
    FirebaseAuth auth;
    DatabaseReference dbRef;
    //SignUpViewModel signViewModel;
    MutableLiveData<String> myrespnse;


    public SignUpRepository(/*SignUpViewModel signViewModel*/) {
        /*this.signViewModel = signViewModel;*/
        myrespnse = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();

        dbRef = FirebaseDatabase.getInstance().getReference().child("users");
    }


    public MutableLiveData<String> signUp(final String userName, final String email, final String password) {
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
                                    uploadUserData(userName, email);

                                    myrespnse.setValue("successful");

                                    // signViewModel.display("successful");
                                } else {
                                    //signViewModel.display(task.getException().getMessage().toString());
                                    myrespnse.setValue(task.getException().getMessage());
                                }
                            }
                        });
            }
        }.start();
        return myrespnse;
    }

    void uploadUserData(String username, String email) {
        String userID = auth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = dbRef.child(userID);

        currentUserDB.child("id").setValue(userID);
        currentUserDB.child("name").setValue(username);
        currentUserDB.child("email").setValue(email);
    }

    private void verifyEmail() {
        FirebaseUser currentUser = auth.getCurrentUser();
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    myrespnse.setValue("successful");
                } else
                    myrespnse.setValue(task.getException().toString());
            }
        });

    }
}
