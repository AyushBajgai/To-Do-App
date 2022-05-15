package com.example.todo_app;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Column Info
// (NoteModel) where the getter setter are placed
@Database(entities = {NoteModel.class}, exportSchema = false, version = 1)
public abstract class stickyNoteDatabase extends RoomDatabase {

    private static final String DB_name = "sticky_note_db";
    private static stickyNoteDatabase request;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized stickyNoteDatabase getInstance(Context context) {
        if (request == null) {
            request = Room.databaseBuilder(context.getApplicationContext(), stickyNoteDatabase.class, DB_name).allowMainThreadQueries().build();
        }
        return request;
    }

    public abstract stickyNoteDao stickyNoteDao();

}
