package com.fluffykittens.lab8.data;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

@Entity(
        tableName = "record",
        foreignKeys = @ForeignKey(
                entity = UserData.class,
                parentColumns = "uid",
                childColumns = "userId"
        )
)
public class RecordData {
    @PrimaryKey(autoGenerate = true)
    public Integer uid;
    public Integer userId;
    public Integer result;
}

