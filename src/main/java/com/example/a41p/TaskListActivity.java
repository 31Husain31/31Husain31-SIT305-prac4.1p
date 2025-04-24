package com.example.a41p;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays the list of tasks in a scrollable view using RecyclerView.
 */
public class TaskListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList; // Stores the tasks to be displayed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // Connect RecyclerView from layout
        recyclerView = findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical list

        // Prepare adapter with an empty list initially
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);

        // Load tasks from database
        loadTasksFromDatabase();

        // Add new task when FAB is clicked
        findViewById(R.id.addTaskFab).setOnClickListener(v -> {
            Intent intent = new Intent(TaskListActivity.this, AddEditTaskActivity.class);
            startActivity(intent);
        });
    }

    // Refresh task list whenever returning to this screen
    @Override
    protected void onResume() {
        super.onResume();
        loadTasksFromDatabase();
    }

    // Retrieves saved tasks from Room and updates the UI
    private void loadTasksFromDatabase() {
        TaskDatabase.getExecutor().execute(() -> {
            TaskDatabase db = TaskDatabase.getDatabase(getApplicationContext());
            List<Task> allTasks = db.taskDao().getAllTasks();

            runOnUiThread(() -> {
                taskList.clear();
                taskList.addAll(allTasks);
                taskAdapter.notifyDataSetChanged(); // Refresh RecyclerView
            });
        });
    }
}
