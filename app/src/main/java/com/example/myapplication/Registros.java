package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapter.AdapterEvaluacion;
import com.example.myapplication.dao.EvaluacionDAO;
import com.example.myapplication.model.Evaluacion;
import com.example.myapplication.ui.DatePickerFragment;
import com.example.myapplication.utils.Validador;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Registros extends AppCompatActivity {

    TextView tv_app_name, tv_registros;
    TextInputLayout til_fecha_inicio, til_fecha_termino;
    ListView lv_evaluaciones;
    AdapterEvaluacion adapter;
    Button btn_filtrar, btn_eliminar_filtro;
    EvaluacionDAO dao;
    String[] fechas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        fechas = new String[2];
        tv_app_name = findViewById(R.id.tv_app_name);
        tv_registros = findViewById(R.id.tv_registros);
        til_fecha_inicio = findViewById(R.id.til_fecha_inicio);
        til_fecha_termino = findViewById(R.id.til_fecha_termino);
        btn_filtrar = findViewById(R.id.btn_filtrar);
        btn_eliminar_filtro = findViewById(R.id.btn_eliminar_filtro);
        lv_evaluaciones = findViewById(R.id.lv_evaluaciones);

        dao = new EvaluacionDAO(this);
        Bundle receivedObjects = getIntent().getExtras();

        if(receivedObjects != null) {
            fechas = receivedObjects.getStringArray("fechas");
            adapter = new AdapterEvaluacion(getBaseContext(), dao.findAllByDate(fechas));
            til_fecha_inicio.getEditText().setText(fechas[0]);
            til_fecha_termino.getEditText().setText(fechas[1]);
            btn_eliminar_filtro.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = lv_evaluaciones.getLayoutParams();
            params.height = 420;
            lv_evaluaciones.setLayoutParams(params);
        } else {
            adapter = new AdapterEvaluacion(getBaseContext(), dao.findAll());
            btn_eliminar_filtro.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = lv_evaluaciones.getLayoutParams();
            params.height = 640;
            lv_evaluaciones.setLayoutParams(params);
        }

        lv_evaluaciones.setAdapter(adapter);

        lv_evaluaciones.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Evaluacion evaluacion = adapter.getItem(i);
                Intent intent = new Intent(getBaseContext(), EditarEvaluacion.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("evaluacion", evaluacion);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
                if (validar()) {
                    fechas[0] = til_fecha_inicio.getEditText().getText().toString();
                    fechas[1] = til_fecha_termino.getEditText().getText().toString();
                    Intent intent = new Intent(view.getContext(), Registros.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("fechas", fechas);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_eliminar_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Registros.class);
                startActivity(intent);
                finish();
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

    private boolean validar () {
        String fecha_inicio = til_fecha_inicio.getEditText().getText().toString();
        String fecha_termino = til_fecha_termino.getEditText().getText().toString();

        boolean fechaInicioValida;
        if (Validador.fechaVacia(fecha_inicio) && Validador.requerido(fecha_inicio)) {
            til_fecha_inicio.setError(null);
            fechaInicioValida = true;
        } else {
            til_fecha_inicio.setError("La fecha es inválida");
            til_fecha_inicio.getEditText().setText("");
            fechaInicioValida = false;
        }

        boolean fechaTerminoValida;
        if (Validador.fechaVacia(fecha_termino) && Validador.requerido(fecha_termino)) {
            til_fecha_termino.setError(null);
            fechaTerminoValida = true;
        } else {
            til_fecha_termino.setError("La fecha es inválida");
            til_fecha_termino.getEditText().setText("");
            fechaTerminoValida = false;
        }

        return fechaInicioValida && fechaTerminoValida;
    }
}