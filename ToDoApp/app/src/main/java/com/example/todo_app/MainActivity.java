package com.example.todo_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.todo_app.databinding.ActivityMainBinding;
import com.example.todo_app.databinding.RowBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity<activityResultLauncher> extends AppCompatActivity implements onNoteListener{

    private ActivityMainBinding binding;

    static AlertDialog.Builder builder;

    List<NoteModel> notesData;

    private static stickyNoteViewModel noteViewModel;

    RowBinding rowBinding;

    RecyclerView recycler_view;

    ActivityResultLauncher<Intent> activityResultLauncher;

 //   myViewModel = new void ViewModelProvider(this).get(NoteMeViewModel.class);

    stickyNoteDatabase db;

    BottomNavigationView bottomNavigationView;

    //private ArrayList<NoteModel>noteModelArrayList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getRoomData();

        //Redirecting user to note_instance_activity
        binding.addNoteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, noteInstance.class);
                startActivity(i);
            }
        });

        //Editing Note


        //Button Navigation
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent in = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(in);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.exit:
                        moveTaskToBack(true);
                        System.exit(0);
                        return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onNoteClick(int position,NoteModel model) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("tag",model);

        Intent in = new Intent(MainActivity.this, noteInstance.class);
        in.putExtra("tag",model);
        in.putExtra("id", model.getId());
        in.putExtra("title", model.getNote_title());
        in.putExtra("description", model.getNote_description());
        in.putExtra("CURRENT_TIMESTAMP", model.getNote_date());
        startActivity(in);
       // updateNote.setId();
    }

    public void getRoomData(){

        notesData=new ArrayList<>();
       // List<NoteModel> nm = db.stickyNoteDao().getAllnote();
//        stickyNoteDatabase.getInstance(getApplicationContext()).stickyNoteDao().getAllnote();

        recycler_view=findViewById(R.id.recycle_container);
        NoteAdapter noteAdapter = new NoteAdapter(this, notesData,this);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.addItemDecoration(new DividerItemDecoration(recycler_view.getContext(), DividerItemDecoration.VERTICAL));
        recycler_view.setAdapter(noteAdapter);

        noteViewModel = new ViewModelProvider(this).get(stickyNoteViewModel.class);
        noteViewModel.getAllNote().observe(this, new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(List<NoteModel> noteModels) {
                Log.d("testlog", noteModels.size()+"");
                noteAdapter.setNotesData(noteModels);
                noteAdapter.notifyDataSetChanged();
            }
        });
    }
}