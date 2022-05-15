package com.example.todo_app;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo_app.databinding.ActivityNoteInstanceBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Date;

public class noteInstance extends AppCompatActivity {

    public static final String TITLE = "TITLE";
    public static final String DESC = "DESC";

    private ActivityNoteInstanceBinding binding;

    Context context;

    private int id;
    private String title;
    private String description;
    boolean isFromEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteInstanceBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//Getting data via intent
        Intent in = getIntent();

        if(in.getStringExtra("title") == null){
            isFromEdit = false;
        }else{
            id = in.getIntExtra("id",0);
            title = in.getStringExtra("title");
            description = in.getStringExtra("description");

            isFromEdit = true;
            binding.stickyNoteTitle.setText(title);
            binding.stickyNoteDescription.setText(description);
            Log.d("checkID", "checking "+title);

        }

      //  id = Integer.parseInt(in.getStringExtra("id").toString());






        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                Log.d("buttonpress", "pressedButton: "+isFromEdit);

                boolean check = true;
            //Action
                if(saveData() == true){
                        Toast.makeText(noteInstance.this,"Note Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button Navigation
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent in = new Intent(noteInstance.this, MainActivity.class);
                        startActivity(in);
                        return true;

                    case R.id.exit:
                        moveTaskToBack(true);
                        //android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                        return true;
                }
                return false;
            }
        });

    }

    private void updating(NoteModel model){


        binding.stickyNoteTitle.setText(model.getNote_title());
        binding.stickyNoteDescription.setText(model.getNote_description());

        model.setId(id);

        model.setNote_title(binding.stickyNoteTitle.getText().toString());
        model.setNote_description(binding.stickyNoteDescription.getText().toString());

        stickyNoteDatabase.getInstance(getApplicationContext()).stickyNoteDao().updateStickyNote(model);
      //  noteDao.updateStickyNote(model);
    }

    private boolean saveData(){
        String txt_Title = binding.stickyNoteTitle.getText().toString();
        String txt_Desc = binding.stickyNoteDescription.getText().toString();

        if(txt_Title.isEmpty()){
            binding.layoutTitle.setError("Title is required");
            binding.stickyNoteTitle.requestFocus();
            return false;
        }
        else if(txt_Title.length() < 5){
            binding.layoutTitle.setError("Title too short");
            binding.stickyNoteTitle.requestFocus();
            return false;
        }
        else if(txt_Desc.isEmpty()){
            binding.layoutDescription.setError("Description is required");
            binding.stickyNoteDescription.requestFocus();
            return false;
        }
        else{
            NoteModel noteModel = new NoteModel();
            noteModel.setNote_title(txt_Title);
            noteModel.setNote_description(txt_Desc);
            noteModel.setNote_date(new Date().toString());

            if(isFromEdit){
                noteModel.setId(id);
                stickyNoteDatabase.getInstance(getApplicationContext()).stickyNoteDao().updateStickyNote(noteModel);
                Log.d("ididfromupdate", "update: "+id);
            }else {
                stickyNoteDatabase.getInstance(getApplicationContext()).stickyNoteDao().insertStickyNote(noteModel);
                Log.d("ididfromupdate", "insert: "+id);
            }

            binding.layoutTitle.setError(null);
            binding.layoutTitle.setErrorEnabled(false);

            binding.layoutDescription.setError(null);
            binding.layoutDescription.setErrorEnabled(false);
        }
    return true;
    }

}