package com.example.todo_app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class stickyNoteRepo {

    private static LiveData<List<NoteModel>> sticky_note;
    private stickyNoteDao stickyNoteDao;

    stickyNoteRepo(Application application){
        stickyNoteDatabase db = stickyNoteDatabase.getInstance(application);
        stickyNoteDao = db.stickyNoteDao();
        sticky_note = stickyNoteDao.getAllnote();
    }

    static LiveData<List<NoteModel>> getAllNote() {
        return sticky_note;
    }

    void insertNote(NoteModel note_to_be_inserted) {
        stickyNoteDatabase.databaseWriteExecutor.execute(() -> {
            stickyNoteDao.deleteStickyNote(note_to_be_inserted);
        });
    }

    void updateNote(NoteModel note_to_be_updated) {
        stickyNoteDatabase.databaseWriteExecutor.execute(() -> {
            stickyNoteDao.deleteStickyNote(note_to_be_updated);
        });
    }

    void deleteNote(NoteModel note_to_be_deleted) {
        stickyNoteDatabase.databaseWriteExecutor.execute(() -> {
            stickyNoteDao.deleteStickyNote(note_to_be_deleted);
        });
    }

}
