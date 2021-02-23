package iti.intake41.myapplication.creatnote.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.creatnote.viewmodel.NoteViewModel;
import iti.intake41.myapplication.helper.UIHelper;
import iti.intake41.myapplication.models.note.Note;
import iti.intake41.myapplication.tripsmap.viewmodel.DoneTripsViewModel;

public class CreatNoteActivity extends AppCompatActivity implements NoteClickListener {
    //views
    RecyclerView noteRecycler;
    FloatingActionButton fABNewNote;
    Button btnDone;

    private NoteAdapter noteAdapter;

    //view model adapter
    NoteViewModel noteViewModel;

    String tripid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_note);
        initViews();
        setAdapter();
        tripid = getIntent().getStringExtra("tripid");

        noteViewModel.getNotes(tripid).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setData(notes);
            }
        });

        //on click of the floating action button to add new note by opening cutom dialog
        fABNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = UIHelper.creatDialog(CreatNoteActivity.this,tripid);

                dialog.show();
            }
        });
    }


    private void initViews() {
        noteRecycler = findViewById(R.id.recyclerView);
        fABNewNote = findViewById(R.id.floatbtnAddNote);
        btnDone = findViewById(R.id.btnDone);
        //view model object
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
    }

    private void setAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        noteRecycler.setLayoutManager(manager);
        noteAdapter = new NoteAdapter();
        noteAdapter.setNoteClickListener(this);
        noteRecycler.setAdapter(noteAdapter);

    }

    @Override
    public void onDeletNote(Note note) {
        noteViewModel.deletNote(note.getId(), tripid);

    }

/*    private List<Note> creatDumyList(){
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("nh","njnj","jd"));
        notes.add(new Note("nh","njnj","jd"));
        notes.add(new Note("nh","njnk","jd"));
        notes.add(new Note("nh","njnj","jd"));
        notes.add(new Note("nh","njnj","jd"));
        notes.add(new Note("nh","njnk","jd"));
        notes.add(new Note("nh","njnj","jd"));
        notes.add(new Note("nh","njnj","jd"));
        notes.add(new Note("nh","njnk","jd"));
        return notes;
    }*/
}