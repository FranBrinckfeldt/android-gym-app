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

public class RegistroEvaluacion extends AppCompatActivity {

    TextView tv_app_name, tv_register_ev;
    TextInputLayout til_register_date, til_peso;
    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_evaluacion);

        tv_app_name = findViewById(R.id.tv_app_name);
        tv_register_ev = findViewById(R.id.tv_register_ev);
        til_register_date = findViewById(R.id.til_register_date);
        til_peso = findViewById(R.id.til_peso);
        btn_save = findViewById(R.id.btn_save);

        til_register_date.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(til_register_date);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Registros.class);
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