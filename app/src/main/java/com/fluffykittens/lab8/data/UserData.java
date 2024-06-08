package com.fluffykittens.lab8.data;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

@Entity(tableName = "user")
public class UserData {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String username;
    public String password;
}

