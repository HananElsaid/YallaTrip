package iti.intake41.myapplication.models.note;

import iti.intake41.myapplication.models.FirebaseRepoDelegate;

public interface NoteRepoInterface {
    void addListener(String tripId, FirebaseRepoDelegate delegate);
    void getNotes(String tripId, FirebaseRepoDelegate delegate);
    void addNote(String tripId, String message, FirebaseRepoDelegate delegate);
    void updateNote(String noteId, String tripId, Boolean done, FirebaseRepoDelegate delegate);
    void deleteNote(String noteId, String tripId, FirebaseRepoDelegate delegate);
}
