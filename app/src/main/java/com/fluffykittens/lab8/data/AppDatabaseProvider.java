package com.fluffykittens.lab8.data;

import android.content.Context;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppDatabaseProvider {
    private static volatile AppDatabase db;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static synchronized AppDatabase getAppDb(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database-name")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }

    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }
}
