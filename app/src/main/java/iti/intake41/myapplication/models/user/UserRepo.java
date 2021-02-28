package iti.intake41.myapplication.models.user;

import iti.intake41.myapplication.models.FirebaseRepo;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;

public class UserRepo extends FirebaseRepo implements UserRepoInterface {

    @Override
    public void getUser(FirebaseRepoDelegate delegate) {
        mDatabase.child("users").child(getUid()).get()
                .addOnSuccessListener(dataSnapshot -> delegate.getObjSuccess(dataSnapshot.getValue(User.class)))
                .addOnFailureListener(e -> delegate.failed(e.getLocalizedMessage()));
    }
}
