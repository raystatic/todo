package com.example.todoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        fabAdd = findViewById(R.id.floating_button_add);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddTaskActivity.class));
            }
        });

        getTasks();

    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void,Void, List<Task>>{

            @Override
            protected List<Task> doInBackground(Void... voids) {

                List<Task> tasks = DatabaseClient
                        .getInstance(MainActivity.this)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return tasks;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                TodoAdapter taskAdapter = new TodoAdapter(MainActivity.this,tasks);
                recyclerView.setAdapter(taskAdapter);
            }
        }

        GetTasks getTasks = new GetTasks();
        getTasks.execute();

    }
}
