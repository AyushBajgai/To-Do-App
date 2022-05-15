package com.example.todo_app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class stickyNoteViewModel extends AndroidViewModel {

    private stickyNoteRepo noteRepo;
    private final LiveData<List<NoteModel>> getAllNotes;

    public stickyNoteViewModel(@NonNull Application application) {
        super(application);
        noteRepo = new stickyNoteRepo(application);
        getAllNotes = stickyNoteRepo.getAllNote();
    }

    LiveData<List<NoteModel>> getAllNote() {
        return getAllNotes;
    }

    public void insert(NoteModel stickNote) {
        noteRepo.insertNote(stickNote);
    }

    public void update(NoteModel stickNote) {
        noteRepo.updateNote(stickNote);
    }

    public void delete(NoteModel stickNote) {
        noteRepo.deleteNote(stickNote);
    }

}
