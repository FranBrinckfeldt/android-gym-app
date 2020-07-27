package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapter.AdapterEvaluacion;
import com.example.myapplication.mock.EvaluacionMock;
import com.example.myapplication.ui.DatePickerFragment;
import com.google.android.material.textfield.TextInputLayout;

public class Registros extends AppCompatActivity {

    TextView tv_app_name, tv_registros;
    TextInputLayout til_fecha_inicio, til_fecha_termino;
    ListView lv_evaluaciones;
    AdapterEvaluacion adapter;
    Button btn_filtrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        tv_app_name = findViewById(R.id.tv_app_name);
        tv_registros = findViewById(R.id.tv_registros);
        til_fecha_inicio = findViewById(R.id.til_fecha_inicio);
        til_fecha_termino = findViewById(R.id.til_fecha_termino);
        btn_filtrar = findViewById(R.id.btn_filtrar);
        lv_evaluaciones = findViewById(R.id.lv_evaluaciones);

        adapter = new AdapterEvaluacion(getBaseContext(), EvaluacionMock.getAll());

        lv_evaluaciones.setAdapter(adapter);

        til_fecha_inicio.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(til_fecha_inicio);
            }
        });

        til_fecha_termino.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(til_fecha_termino);
            }
        });

        btn_filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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