package iti.intake41.myapplication.creatnote.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.creatnote.model.Note;
import iti.intake41.myapplication.helper.UIHelper;

public class CreatNoteActivity extends AppCompatActivity implements NoteClickListener {
    //views
    RecyclerView noteRecycler;
    FloatingActionButton fABNewNote;
    Button btnDone;

    private NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_note);
        initViews();
        setAdapter();
        noteAdapter.setData(creatDumyList());
        //on click of the floating action button to add new note by opening cutom dialog
        fABNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = UIHelper.creatDialog(CreatNoteActivity.this);
                dialog.show();
            }
        });
    }


    private void initViews() {
        noteRecycler = findViewById(R.id.recyclerView);
        fABNewNote = findViewById(R.id.floatbtnAddNote);
        btnDone = findViewById(R.id.btnDone);
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

    }

    private List<Note> creatDumyList(){
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
    }
}