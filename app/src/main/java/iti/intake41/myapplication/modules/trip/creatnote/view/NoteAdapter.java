package iti.intake41.myapplication.modules.trip.creatnote.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.intake41.myapplication.R;
import iti.intake41.myapplication.models.note.Note;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private Context context;
    private List<Note> noteList;
    private NoteClickListener noteClickListener;
    private String status=null;

    public void setNoteClickListener(NoteClickListener noteClickListener) {
        this.noteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_row, parent, false);
        NoteHolder holderClass = new NoteHolder(view);
        return holderClass;

    }


    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = noteList.get(position);
        holder.tvNoteText.setText(note.getTitle());

        holder.image_Delete.setEnabled(true);

        if (note.getDone()) {
            holder.checkBox2.setChecked(true);
        } else
            holder.checkBox2.setChecked(false);

        //
        if (status!=null&&!status.equals("upcoming")){
            //holder.image_Delete.setClickable(true);
            holder.checkBox2.setEnabled(false);
            holder.image_Delete.setEnabled(false);
        }

        holder.image_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteClickListener.onDeletNote(note);
            }
        });
        holder.checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = holder.checkBox2.isChecked();
                if (checked) {
                    holder.checkBox2.setChecked(true);
                    note.setDone(true);

                } else {
                    holder.checkBox2.setChecked(false);
                    note.setDone(false);

                }
                noteClickListener.onUpdateState(note);
            }
        });


    }


    @Override
    public int getItemCount() {

        return noteList != null ? noteList.size() : 0;
    }

    public void setData(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }
    public  void setTripStatus(String status){
        this.status=status;
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        TextView tvNoteText;
        ImageView image_Delete;
        ConstraintLayout item_Contaner;
        CheckBox checkBox2;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteText = itemView.findViewById(R.id.tvNoteText);
            image_Delete = itemView.findViewById(R.id.imageDelete);
            item_Contaner = itemView.findViewById(R.id.item_Contaner);
            checkBox2 = itemView.findViewById(R.id.checkBox2);
        }
    }
}





