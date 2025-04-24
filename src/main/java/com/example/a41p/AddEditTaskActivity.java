package com.example.a41p;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class AddEditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        // Connect UI elements from XML
        EditText titleInput = findViewById(R.id.editTextTitle);
        EditText descriptionInput = findViewById(R.id.editTextDescription);
        EditText dueDateInput = findViewById(R.id.editTextDueDate);
        Button saveButton = findViewById(R.id.buttonSaveTask);

        // Save button logic
        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String description = descriptionInput.getText().toString().trim();
            String dueDateStr = dueDateInput.getText().toString().trim();

            // Required field validation
            if (title.isEmpty() || dueDateStr.isEmpty()) {
                Toast.makeText(this, "Please enter a title and due date", Toast.LENGTH_SHORT).show();
                return;
            }

            // Parse due date string into Date object
            Date dueDate;
            try {
                dueDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(dueDateStr);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid date format. Use dd/MM/yyyy", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create new task (Room will assign ID automatically)
            Task task = new Task(title, description, dueDate);

            // Insert into database on background thread
            TaskDatabase.getExecutor().execute(() -> {
                TaskDatabase db = TaskDatabase.getDatabase(getApplicationContext());
                Log.d("TaskDebug", "Inserting task: " + title + " | " + dueDate);
                db.taskDao().insert(task);

                // Show confirmation and return to list
                runOnUiThread(() -> {
                    Toast.makeText(this, "Task saved!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        });
    }
}
