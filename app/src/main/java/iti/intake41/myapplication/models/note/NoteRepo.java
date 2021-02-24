package iti.intake41.myapplication.models.note;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iti.intake41.myapplication.models.FirebaseRepo;
import iti.intake41.myapplication.models.FirebaseRepoDelegate;

public class NoteRepo extends FirebaseRepo implements NoteRepoInterface {

    @Override
    public void addListener(String tripId, FirebaseRepoDelegate delegate){
        mDatabase.child("notes").child(tripId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Note> notes = new ArrayList();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Note note = postSnapshot.getValue(Note.class);
                    notes.add(note);
                }
                delegate.getListSuccess(notes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                delegate.failed(error.getMessage());
            }

        });
    }

    @Override
    public void getNotes(String tripId, FirebaseRepoDelegate delegate) {
        mDatabase.child("notes").child(tripId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    List<Note> notes = new ArrayList();
                    task.getResult().getChildren().forEach(i->{
                        notes.add(i.getValue(Note.class));
                    });

                    System.out.println("trips: " + task.getResult().getChildren());
                    delegate.getObjSuccess(notes);
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                    delegate.failed(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    @Override //#Done
    public void addNote(String tripId, String message, FirebaseRepoDelegate delegate) {

        String noteId = getNoteID(tripId);

        Note note = new Note(noteId , message, false);
        Map<String, Object> childUpdates = new HashMap<>();

        //childUpdates.put("/users/"+getUid()+"/trips/" + tripId + "/notes/" + noteId, note.toMap());
        childUpdates.put( "/notes/" + tripId + "/" + noteId, note.toMap());

        mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    delegate.success("Note added Successfully");
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                    delegate.failed(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void updateNote(String noteId, String tripId, Boolean done, FirebaseRepoDelegate delegate) {
        String path = "/notes/" + tripId + "/" + noteId;

        mDatabase.child(path).child("done").setValue(done).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    delegate.success("note updated Successfully");
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                    delegate.failed(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void deleteNote(String noteId, String tripId, FirebaseRepoDelegate delegate) {
        String path = "/notes/" + tripId + "/" + noteId;

        mDatabase.child(path).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    delegate.success("Note removed Successfully");
                } else {
                    Log.e("firebase", "Error getting data", task.getException());
                    delegate.failed(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    public String getNoteID(String tripId){
        return mDatabase.child("notes").child(tripId).push().getKey();
    }

}
