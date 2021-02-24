package iti.intake41.myapplication.models.user;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import iti.intake41.myapplication.models.FirebaseRepo;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;

public class UserRepo extends FirebaseRepo implements UserRepoInterface {

    @Override
    public void getUser(FirebaseRepoDelegate delegate) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.child("users").child(getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            User user = task.getResult().getValue(User.class);
                            delegate.getObjSuccess(user);
                        } else {
                            Log.e("firebase", "Error getting data", task.getException());
                            delegate.failed(task.getException().getLocalizedMessage());
                        }
                    }
                });


            }  }).start();
        }
    }
