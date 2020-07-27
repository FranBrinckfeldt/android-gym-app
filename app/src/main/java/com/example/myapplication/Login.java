package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextView tv_app_name, tv_login, tv_registrarse;
    TextInputLayout til_user, til_password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_app_name = findViewById(R.id.tv_app_name);
        tv_login = findViewById(R.id.tv_login);
        til_user = findViewById(R.id.til_user);
        til_password = findViewById(R.id.til_password);
        btn_login = findViewById(R.id.btn_login);
        tv_registrarse = findViewById(R.id.tv_registrarse);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Menu.class);
                startActivity(intent);
            }
        });

        tv_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RegistroUsuario.class);
                startActivity(intent);
            }
        });
    }
}