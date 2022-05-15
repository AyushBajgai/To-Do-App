package com.example.todo_app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "stickynote")
public class NoteModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String note_title;

    @ColumnInfo(name = "description")
    private  String  note_description;

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    private String note_date;

    //Paratemerized constructor
    @Ignore
    public NoteModel(int id, String note_title, String note_description) {
        this.id= id;
        this.note_title = note_title;
        this.note_description = note_description;
    }

    public NoteModel() {
    //Need to remove later on
    }

    //Getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_description() {
        return note_description;
    }

    public void setNote_description(String note_description) {
        this.note_description = note_description;
    }

    public String getNote_date() {
        return note_date;
    }

    public void setNote_date(String note_date) {
        this.note_date = note_date;
    }
}
