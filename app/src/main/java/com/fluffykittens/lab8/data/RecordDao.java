package com.fluffykittens.lab8.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordDao {
    @Query("SELECT * FROM record WHERE userId = :userId")
    LiveData<List<RecordData>> getAllByUserId(Integer userId);

    @Query("SELECT * FROM record WHERE userId = (SELECT uid FROM user WHERE username = :username) ORDER BY result DESC LIMIT 5;")
    LiveData<List<RecordData>> getAllByUsername(String username);

    @Insert
    void insert(RecordData record);

    @Delete
    void delete(RecordData record);
}
