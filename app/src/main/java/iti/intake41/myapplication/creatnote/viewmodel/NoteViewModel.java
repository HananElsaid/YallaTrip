package iti.intake41.myapplication.creatnote.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.note.Note;
import iti.intake41.myapplication.models.note.NoteRepo;
import iti.intake41.myapplication.models.note.NoteRepoInterface;

public class NoteViewModel extends ViewModel {
    NoteRepoInterface noteRepo;
    MutableLiveData<List<Note>> noteList;
    MutableLiveData<String> message;

    public NoteViewModel() {
        noteRepo = new NoteRepo();
        noteList = new MutableLiveData<>();
        message = new MutableLiveData<>();
    }

    public MutableLiveData<List<Note>> getNotes(String tripId) {
        noteRepo.getNotes(tripId, new FirebaseRepoDelegate() {
            @Override
            public <T> void getListSuccess(List<T> list) {
                List<Note> notes = (List<Note>) list;
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

    public void deletNote(String noteId,String tripId) {
        noteRepo.deleteNote(noteId, tripId, new FirebaseRepoDelegate() {
            @Override
            public void success(String mege) {
                message.setValue(mege);
            }
        });
    }
}
