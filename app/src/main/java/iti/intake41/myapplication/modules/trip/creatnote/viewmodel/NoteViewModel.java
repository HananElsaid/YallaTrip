package iti.intake41.myapplication.modules.trip.creatnote.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.note.Note;
import iti.intake41.myapplication.models.note.NoteRepo;
import iti.intake41.myapplication.models.note.NoteRepoInterface;

public class NoteViewModel extends ViewModel {
    NoteRepoInterface noteRepo;
    public MutableLiveData<List<Note>> noteList;
    public MutableLiveData<String> message;

    public NoteViewModel() {
        noteRepo = new NoteRepo();
        noteList = new MutableLiveData<>();
        message = new MutableLiveData<>();
    }

    public void addListener(String tripId) {
        noteRepo.addListener(tripId, new FirebaseRepoDelegate() {
            @Override
            public <T> void getListSuccess(List<T> list) {
                List<Note> notes = (List<Note>) list;
                System.out.println("Notes count" + notes.size());
                noteList.setValue(notes);
            }

            @Override
            public void failed(String msg) {
                message.postValue(msg);
            }
        });
        //getNotes(tripId);
    }

    public MutableLiveData<List<Note>> getNotes(String tripId) {
        noteRepo.getNotes(tripId, new FirebaseRepoDelegate() {
            @Override
            public <T> void getListSuccess(List<T> list) {
                List<Note> notes = (List<Note>) list;
                System.out.println("Notes count" + notes.size());
                noteList.setValue(notes);
            }

            @Override
            public void success(String mesg) {
                message.postValue(mesg);
            }

            @Override
            public void failed(String msg) {
                message.postValue(msg);
            }
        });
        return noteList;
    }

    public void deletNote(String noteId, String tripId) {
        noteRepo.deleteNote(noteId, tripId, new FirebaseRepoDelegate() {
            @Override
            public void success(String mege) {
                message.setValue(mege);
            }
        });
    }

    //update note
    public void updateNote(Note note, String tripId) {
        noteRepo.updateNote(note.getId(), tripId, note.getDone(), new FirebaseRepoDelegate() {
            @Override
            public void success(String mes) {
                message.setValue(mes);
            }

            @Override
            public void failed(String msg) {
                message.setValue(msg);
            }
        });
    }
}
