package com.fluffykittens.lab8;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fluffykittens.lab8.data.RecordData;
import com.fluffykittens.lab8.data.RecordRepository;

public class Records extends AppCompatActivity {
    RecordRepository recordRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);
        recordRepository = new RecordRepository(this);
        // Ну тут нормальное отображение сделать короче и готово
        recordRepository.getRecordsByUserId(1, records -> {
            for (RecordData recordData : records) {
                Toast.makeText(this, "there is a record!", Toast.LENGTH_SHORT).show();
            }
        });
        recordRepository.getRecordsByUsername("petrik3003@gmail.com", records -> {
            for (RecordData recordData : records) {
                Toast.makeText(this, "there is a record!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
