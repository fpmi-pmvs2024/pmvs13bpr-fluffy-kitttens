package com.fluffykittens.lab8.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class, RecordData.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract RecordDao recordDao();
}
