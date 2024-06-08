package com.fluffykittens.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fluffykittens.lab8.data.RecordData;
import com.fluffykittens.lab8.data.RecordRepository;

public class Records extends AppCompatActivity {
    RecordRepository recordRepository;
    Button back_to_profile;
    TextView records_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);
        recordRepository = new RecordRepository(this);

//        Ну тут нормальное отображение сделать короче и готово
//        recordRepository.getRecordsByUserId(1, records -> {
//            for (RecordData recordData : records) {
//                Toast.makeText(this, "there is a record!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        recordRepository.getRecordsByUsername("petrik3003@gmail.com", records -> {
//            for (RecordData recordData : records) {
//                Toast.makeText(this, "there is a record!", Toast.LENGTH_SHORT).show();
//            }
//        });

        back_to_profile = (Button) findViewById(R.id.bt_back_to_profile);
        records_info = (TextView) findViewById(R.id.tv_records);

        back_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        recordRepository.getRecordsByUsername(username, records -> {
            Integer i = 1;
            for (RecordData recordData : records) {
                records_info.append(i.toString() + ". " + recordData.result.toString() + "\n");
                i++;
            }
        });

    }
}
