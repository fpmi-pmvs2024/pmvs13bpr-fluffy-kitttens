package com.fluffykittens.lab8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.fluffykittens.lab8.data.UserRepository;

public class MainActivity extends AppCompatActivity {
    EditText etMail, pswd;
    Button sign_in_btn, sign_up_btn;
    UserRepository userRepository;
    Intent intent_sign_up;
    Intent intent_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        etMail = (EditText)findViewById(R.id.etEmail);
        pswd = (EditText)findViewById(R.id.etPassword);
        sign_in_btn = (Button)findViewById(R.id.btnSignIn);
        sign_up_btn = (Button)findViewById(R.id.btnSignUp);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.flicker);
        a.reset();
        sign_in_btn.clearAnimation();
        sign_in_btn.startAnimation(a);
        userRepository = new UserRepository(this);
        intent_sign_up = new Intent(MainActivity.this, com.fluffykittens.lab8.SignUp.class);
        intent_profile = new Intent(MainActivity.this, com.fluffykittens.lab8.Profile.class);
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etMail.getText().toString();
                String password = pswd.getText().toString();
                userRepository.checkUserExists(username, user -> {
                    if(user != null) {
                        String[] words = user.password.split("/");
                        if (words[0].equals(password))
                        {
                            Toast.makeText(getApplicationContext(), "Успешный вход",Toast.LENGTH_SHORT).show();
                            intent_profile.putExtra("username", username);
                            startActivity(intent_profile);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Пароль введён не верно",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Аккаунта с такой почтой не существует",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_sign_up);
            }
        });
    }
}