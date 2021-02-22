package iti.intake41.myapplication.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseRepo {
    //MARK: - Properties
    protected DatabaseReference mDatabase;
    protected   static FirebaseRepo instance;
    protected static final String TAG = "ReadAndWriteSnippets";

    public FirebaseRepo() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    protected String getUid() {
        return FirebaseAuth.getInstance().getUid();
    }
}


