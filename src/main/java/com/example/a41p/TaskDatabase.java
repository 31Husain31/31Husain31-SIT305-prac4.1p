package com.example.a41p;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Room database class for the app. Manages the Task table and DAO.
 */
@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class}) // We'll create this next to handle Date
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    private static volatile TaskDatabase INSTANCE;

    // We use a thread pool to run DB operations in the background
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static TaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TaskDatabase.class, "task_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static ExecutorService getExecutor() {
        return databaseWriteExecutor;
    }
}
