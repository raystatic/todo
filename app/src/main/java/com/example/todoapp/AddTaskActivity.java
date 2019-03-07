package com.example.todoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    EditText etTask, etDesc, etFinishBy;
    Button btnSave;
    String task, desc, finishby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTask = findViewById(R.id.editTextTask);
        etDesc = findViewById(R.id.editTextDesc);
        etFinishBy = findViewById(R.id.editTextFinishBy);
        btnSave = findViewById(R.id.button_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = etTask.getText().toString().trim();
                desc = etDesc.getText().toString().trim();
                finishby = etFinishBy.getText().toString().trim();

                if (!TextUtils.isEmpty(task)){
                    if (!TextUtils.isEmpty(desc)){
                        if (!TextUtils.isEmpty(finishby)){
                            save(task, desc, finishby);
                        }else{
                            etFinishBy.setError("Finish By cannot be empty");
                        }
                    }else{
                        etDesc.setError("Description cannot be empty");
                    }
                }else {
                    etTask.setError("Task cannot be empty");
                }

            }
        });

    }

    private void save(final String task, final String desc, final String finishby) {
        class  SaveTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {

                Task task1 = new Task();
                task1.setTask(task);
                task1.setDesc(desc);
                task1.setFinishBy(finishby);
                task1.setFinished(false);

                //adding to database
                DatabaseClient.getInstance(AddTaskActivity.this).getAppDatabase()
                        .taskDao()
                        .insert(task1);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(AddTaskActivity.this,MainActivity.class));
                Toast.makeText(AddTaskActivity.this,"Task saved",Toast.LENGTH_SHORT).show();
            }
        }

        SaveTask saveTask = new SaveTask();
        saveTask.execute();

    }
}
