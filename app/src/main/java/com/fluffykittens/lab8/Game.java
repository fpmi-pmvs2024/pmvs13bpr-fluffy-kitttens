package com.fluffykittens.lab8;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class Game extends AppCompatActivity {

    String[] textVariants = new String[5];
    int[] textSizes = new int[5];

    TextView given_text;
    TextView msg_dialog;
    EditText print_text;

    Button btn_finish;
    Button btn_exit;

    Intent intent_profile;

    Animation animAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        given_text = (TextView) findViewById(R.id.inputText);
        btn_exit = (Button) findViewById(R.id.btnExitTraining);

        animAlpha = AnimationUtils.loadAnimation(this, R.anim.flicker);

        SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.DB), MODE_PRIVATE);


        intent_profile = new Intent(Game.this, com.fluffykittens.lab8.Profile.class);


        Animation a = AnimationUtils.loadAnimation(this, R.anim.flicker);
        a.reset();

        given_text.setText("Тема, здесь тип игра должна быть");

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_profile);
            }
        });

    }
}