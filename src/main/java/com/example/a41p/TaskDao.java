package com.example.a41p;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * DAO interface for interacting with the Task table.
 */
@Dao
public interface TaskDao {

    // Insert a task
    @Insert
    void insert(Task task);

    // Update a task
    @Update
    void update(Task task);

    // Delete a task
    @Delete
    void delete(Task task);

    // Get all tasks ordered by due date ascending
    @Query("SELECT * FROM task_table ORDER BY dueDate ASC")
    List<Task> getAllTasks();
}