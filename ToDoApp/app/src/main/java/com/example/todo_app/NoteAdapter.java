package com.example.todo_app;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder>{

    stickyNoteDatabase db;

    List<NoteModel> notesData = new ArrayList<>();
    private  Context context;

    private AdapterView.OnItemClickListener listener;

    //private ArrayList<NoteModel>noteModelArrayList;

    private onNoteListener monNoteListener;

    public NoteAdapter(Context context, List<NoteModel> notesDatan, onNoteListener monNoteListener) {
        this.context = context;
        this.notesData = notesData;
        this.monNoteListener=monNoteListener;
    }

    public void setNotesData(List<NoteModel> notesData) {
        Log.d("testadd", "setNotesData: "+notesData.size());
        this.notesData.clear();
        this.notesData.addAll(notesData);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);

        //passing the noteListener globally
        return new MyViewHolder(view,monNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NoteModel data =  notesData.get(position);
        holder.noteTitle.setText(notesData.get(position).getNote_title());
        holder.noteDescription.setText(notesData.get(position).getNote_description());
        holder.noteDate.setText(notesData.get(position).getNote_date());

        Log.d("testdata", "onBindViewHolder: "+notesData.get(position).getNote_title());
      //  holder.noteDate.setTime(noteModelArrayList.get(position).getNote_date().toString());

    }

    @Override
    public int getItemCount() {
        Log.d("tag", "Trying 50 test"+notesData.size());
        return notesData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView noteTitle, noteDescription;
        TextView noteDate;
        ImageView btnDelete, btnEdit;
        RelativeLayout row;
        onNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, onNoteListener onNoteListener) {
            super(itemView);

            noteTitle = (TextView) itemView.findViewById(R.id.note_subject);
            noteDescription = (TextView) itemView.findViewById(R.id.note_description);
            btnDelete = (ImageView) itemView.findViewById(R.id.delete_note);
            btnEdit = (ImageView) itemView.findViewById(R.id.edit_note);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (monNoteListener != null && position != RecyclerView.NO_POSITION) {
                        monNoteListener.onNoteClick(position,notesData.get(getAdapterPosition()));
                    }
                }
            });


            row = (RelativeLayout) itemView.findViewById(R.id.recycle_container);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickyNoteDatabase.getInstance(context).stickyNoteDao().deleteStickyNote(notesData.get(getAdapterPosition()));
                    int position = getAdapterPosition();

                    Toast.makeText(view.getContext(), "Deleted: "+notesData.get(position).getNote_title(), Toast.LENGTH_SHORT).show();
                    notesData.remove(position);
                    notifyDataSetChanged();

                }
            });

            //setting from constructor
            this.onNoteListener = onNoteListener;
            //noteDate = (Date) itemView.findViewById(R.id.add_note_icon);

            //Implementing onClick listener to entire viewHolder
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

           /* int position = getAdapterPosition();
            if(onNoteListener != null && getAdapterPosition()!= RecyclerView.NO_POSITION){
                onNoteListener.onNoteClick(position);
            }*/
        }
    }
}
