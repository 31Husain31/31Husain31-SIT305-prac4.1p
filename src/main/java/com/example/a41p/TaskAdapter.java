package com.example.a41p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This class is the adapter that connects task data to the RecyclerView.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList; // Holds the list of tasks to be shown

    // Constructor that takes the task list
    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * This method is called when a new ViewHolder is created.
     * It inflates the item_task layout for each task.
     */
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single item in the list
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    /**
     * This method binds data to each item view.
     */
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // Set title, description, and due date into the view
        holder.titleTextView.setText(task.getTitle());
        holder.descriptionTextView.setText(task.getDescription());

        // Format the date before displaying it
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.dueDateTextView.setText("Due: " + sdf.format(task.getDueDate()));
    }

    /**
     * Returns the total number of items in the list.
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /**
     * ViewHolder class holds references to views for each item.
     */
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dueDateTextView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            // Connect XML views to Java variables
            titleTextView = itemView.findViewById(R.id.taskTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.taskDescriptionTextView);
            dueDateTextView = itemView.findViewById(R.id.taskDueDateTextView);
        }
    }
}
