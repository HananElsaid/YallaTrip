package iti.intake41.myapplication.modules.trip.creatnote.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.helper.UIHelper;
import iti.intake41.myapplication.models.note.Note;
import iti.intake41.myapplication.modules.trip.creatnote.viewmodel.NoteViewModel;

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


        tripid = getIntent().getStringExtra("trip_id");
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
                Toast.makeText(CreatNoteActivity.this, s, Toast.LENGTH_SHORT).show();
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

        noteViewModel.getNotes(tripid);
    }


    private void initViews() {
        noteRecycler = findViewById(R.id.recyclerView);
        fABNewNote = findViewById(R.id.floatbtnAddNote);
        btnDone = findViewById(R.id.btnDone);
        //view model object
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.addListener(tripid);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatNoteActivity.this);
        builder.setMessage(R.string.areYouSureToDeleteThisNote);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                noteViewModel.deletNote(note.getId(), tripid);
                Toast.makeText(CreatNoteActivity.this, R.string.noteDeletedSuccessfully, Toast.LENGTH_SHORT).show();
            }
        })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    @Override
    public void onUpdateState(Note note) {
        noteViewModel.updateNote(note,tripid);
    }

}