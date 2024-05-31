package com.fluffykittens.lab8;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    TextView nameLabel;
    TextView bestScoreLabel;
    TextView countLabel;
    Button exit_btn;
    Button start_btn;
    Intent intent_sign_in;
    Intent intent_start;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        nameLabel = (TextView) findViewById(R.id.userName);
        exit_btn = (Button)findViewById(R.id.btnSignOut);
        start_btn = (Button)findViewById(R.id.btnStartGame);
        intent_sign_in = new Intent(Profile.this, com.fluffykittens.lab8.MainActivity.class);


        intent_start = new Intent(Profile.this, com.fluffykittens.lab8.Game.class);

        Bundle arguments = getIntent().getExtras();
        String user = arguments.get("username").toString();
        intent_start.putExtra("username", user);
        SharedPreferences mSettings = getSharedPreferences(getResources().getString(R.string.DB), Context.MODE_PRIVATE);
        String[] user_info = mSettings.getString(user, "").split("/");
        String[] user_name = user.split("@");
        nameLabel.setText(user_name[0]);
        intent_start.putExtra("password", user_info[0]);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.flicker);
        a.reset();
        start_btn.clearAnimation();
        start_btn.startAnimation(a);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_start);
            }
        });
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_sign_in);
            }
        });
    }
}