package com.example.todo_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface stickyNoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStickyNote(NoteModel noteModel);

    @Query("Select * from stickynote")
   LiveData<List<NoteModel>> getAllnote();

   @Update
   void updateStickyNote(NoteModel noteModel);

    @Delete
    void deleteStickyNote(NoteModel noteModel);

}
