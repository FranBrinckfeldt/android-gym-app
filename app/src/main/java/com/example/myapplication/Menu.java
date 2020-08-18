package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    TextView tv_app_name, tv_main, tv_cerrar_sesion;
    Button btn_new_register, btn_registros;
    long mBackPressed;
    static final int TIME_INTERVAL = 2000;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tv_app_name = findViewById(R.id.tv_app_name);
        tv_main = findViewById(R.id.tv_main);
        btn_new_register = findViewById(R.id.btn_new_register);
        btn_registros = findViewById(R.id.btn_registros);
        tv_cerrar_sesion = findViewById(R.id.tv_cerrar_sesion);

        preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

        btn_new_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RegistroEvaluacion.class);
                startActivity(intent);
            }
        });

        btn_registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Registros.class);
                startActivity(intent);
            }
        });

        tv_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("id");
                editor.remove("username");
                editor.remove("firstname");
                editor.remove("lastname");
                editor.remove("height");
                editor.commit();
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            finishAffinity();
            System.exit(0);
        } else {
            Toast.makeText(getBaseContext(), getString(R.string.press_exit), Toast.LENGTH_SHORT).show(); }
        mBackPressed = System.currentTimeMillis();
    }
}