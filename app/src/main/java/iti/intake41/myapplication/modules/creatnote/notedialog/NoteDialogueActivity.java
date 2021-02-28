package iti.intake41.myapplication.modules.creatnote.notedialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.note.Note;
import iti.intake41.myapplication.modules.creatnote.view.CreatNoteActivity;
import iti.intake41.myapplication.modules.creatnote.view.NoteAdapter;
import iti.intake41.myapplication.modules.creatnote.view.NoteClickListener;
import iti.intake41.myapplication.modules.creatnote.viewmodel.NoteViewModel;

public class NoteDialogueActivity extends AppCompatActivity implements NoteClickListener {
    //NoteDialogue Views
    private TextView txtViewTripName;
    private RecyclerView noteRecycler;


    private NoteAdapter noteAdapter;
    private ArrayList<Note> notesArrayList;

    //view model adapter
    NoteViewModel noteViewModel;

    String tripID, tripTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_dialogue);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initViews();
        setAdapter();


        noteViewModel.noteList.observe(this, new Observer<List<Note>>() {//getNotes(tripid)
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setData(notes);
            }
        });

        noteViewModel.message.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(NoteDialogueActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        noteViewModel.getNotes(tripID);
    }

    private void initViews() {
        tripID = getIntent().getStringExtra("tripID");
        tripTitle = getIntent().getStringExtra("tripName");
        txtViewTripName = findViewById(R.id.tvTripTitle);
        txtViewTripName.setText(tripTitle);
        noteRecycler = findViewById(R.id.notesRecycler);
        notesArrayList = new ArrayList<>();
        //view model object
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.addListener(tripID);
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

    @Override
    public void onUpdateState(Note note) {

            noteViewModel.updateNote(note,tripID);
    }
}