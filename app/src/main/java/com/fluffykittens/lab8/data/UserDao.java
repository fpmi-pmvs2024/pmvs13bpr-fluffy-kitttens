package com.fluffykittens.lab8.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<UserData>> getAll();

    @Query("SELECT * FROM user WHERE username LIKE :username LIMIT 1")
    LiveData<UserData> getByUsername(String username);

    @Insert
    void insert(UserData user);

    @Delete
    void delete(UserData user);
}
