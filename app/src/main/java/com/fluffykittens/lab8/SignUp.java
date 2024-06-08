package com.fluffykittens.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.fluffykittens.lab8.data.UserRepository;

public class SignUp extends AppCompatActivity {
    EditText etMail, pswd, pswd_conf;
    Button sign_up_btn;
    Intent intent_sign_in;
    UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        etMail = (EditText)findViewById(R.id.etEmail);
        pswd = (EditText)findViewById(R.id.etPassword);
        pswd_conf = (EditText)findViewById(R.id.etPasswordConfirm);
        sign_up_btn = (Button)findViewById(R.id.btnSignUpConfirm);
        userRepository = new UserRepository(this);
        intent_sign_in = new Intent(SignUp.this, com.fluffykittens.lab8.MainActivity.class);
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etMail.getText().toString();
                String password = pswd.getText().toString();
                String password_2 = pswd_conf.getText().toString();
                userRepository.checkUserExists(username, user -> {
                    if (user != null) {
                        Toast.makeText(getApplicationContext(),R.string.user_already_exists,Toast.LENGTH_SHORT).show();
                    } else {
                        if (username.contains("@"))
                        {
                            if (password.length() > 7)
                            {
                                if (password.equals(password_2))
                                {
                                    if (password.contains("/"))
                                    {
                                        Toast.makeText(getApplicationContext(),R.string.incorrect_symbols,Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        userRepository.insertUser(username, password + "/0/0/0");
                                        Toast.makeText(getApplicationContext(), R.string.success_registration,Toast.LENGTH_SHORT).show();
                                        startActivity(intent_sign_in);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),R.string.passwords_dont_match,Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),R.string.small_password,Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),R.string.incorrect_email,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}