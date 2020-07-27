package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.myapplication.ui.DatePickerFragment;
import com.google.android.material.textfield.TextInputLayout;

public class RegistroUsuario extends AppCompatActivity {

    TextView tv_app_name, tv_signin;
    TextInputLayout til_user, til_name, til_lastname, til_date, til_estatura, til_password;
    Button btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        tv_app_name = findViewById(R.id.tv_app_name);
        tv_signin = findViewById(R.id.tv_signin);
        til_user = findViewById(R.id.til_user);
        til_name = findViewById(R.id.til_name);
        til_lastname = findViewById(R.id.til_lastname);
        til_date = findViewById(R.id.til_date);
        til_estatura = findViewById(R.id.til_estatura);
        til_password = findViewById(R.id.til_password);
        btn_signin = findViewById(R.id.btn_signin);

        til_date.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(til_date);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog (final TextInputLayout til) {
        DatePickerFragment datePicker = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = String.format("%d/%02d/%02d", year, month+1, day);
                til.getEditText().setText(selectedDate);
            }
        });
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }
}