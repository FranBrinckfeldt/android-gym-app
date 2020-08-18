package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.dao.EvaluacionDAO;
import com.example.myapplication.model.Evaluacion;
import com.example.myapplication.ui.DatePickerFragment;
import com.example.myapplication.utils.Validador;
import com.google.android.material.textfield.TextInputLayout;

public class EditarEvaluacion extends AppCompatActivity {

    TextView tv_app_name, tv_editar_ev, tv_imc;
    TextInputLayout til_register_date, til_peso;
    Button btn_editar, btn_eliminar;
    Evaluacion evaluacion;
    EvaluacionDAO dao;
    SharedPreferences preferences;
    double estatura;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evaluacion);

        preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

        tv_app_name = findViewById(R.id.tv_app_name);
        tv_editar_ev = findViewById(R.id.tv_editar_ev);
        tv_imc = findViewById(R.id.tv_imc);
        til_register_date = findViewById(R.id.til_register_date);
        til_peso = findViewById(R.id.til_peso);
        btn_editar = findViewById(R.id.btn_editar);
        btn_eliminar = findViewById(R.id.btn_eliminar);
        dao = new EvaluacionDAO(this);

        estatura = Double.parseDouble(preferences.getString("height", "1.0"));
        uid = preferences.getInt("id", 0);

        Bundle bundle = getIntent().getExtras();
        evaluacion = null;

        if (bundle != null) {
            evaluacion = (Evaluacion) bundle.getSerializable("evaluacion");
        } else {
            Intent intent = new Intent(getBaseContext(), Registros.class);
            startActivity(intent);
            Toast.makeText(getBaseContext(), getString(R.string.mensaje_error_bundle), Toast.LENGTH_LONG).show();
        }

        til_peso.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String strPeso = til_peso.getEditText().getText().toString();
                if (!strPeso.isEmpty()) {
                    double peso = Double.parseDouble(strPeso);
                    tv_imc.setText(String.format("%.1f", peso / (estatura * estatura)));
                } else {
                    tv_imc.setText(getString(R.string.ingrese_peso));
                }
            }
        });

        til_register_date.getEditText().setText(evaluacion.getDate());
        til_peso.getEditText().setText(Double.toString(evaluacion.getPeso()));

        tv_editar_ev.setText(getString(R.string.editar_ev, evaluacion.getId()));

        til_register_date.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(til_register_date);
            }
        });

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()) {
                    String register_date = til_register_date.getEditText().getText().toString();
                    String peso = til_peso.getEditText().getText().toString();
                    Evaluacion evaluacionEditada = new Evaluacion(evaluacion.getId(), 0, register_date, Double.parseDouble(peso), estatura);
                    if(dao.update(evaluacionEditada)){
                        Toast.makeText(view.getContext(), "Se editó evaluación", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), Registros.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(view.getContext(), "Hubo un error al editar la evaluación", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()) {
                    if(dao.deleteById(evaluacion.getId())) {
                        Toast.makeText(view.getContext(), "Se eliminó evaluación", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), Registros.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(view.getContext(), "Hubo un error al eliminar la evaluación", Toast.LENGTH_SHORT).show();
                    }
                }
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
        String register_date = til_register_date.getEditText().getText().toString();
        String peso = til_peso.getEditText().getText().toString();

        boolean fechaValida;
        if (Validador.fecha(register_date)) {
            til_register_date.setError(null);
            fechaValida = true;
        } else {
            til_register_date.setError("La fecha es inválida");
            til_register_date.getEditText().setText("");
            fechaValida = false;
        }

        boolean pesoValido;
        if (Validador.requerido(peso) && Validador.mayorCero(peso)) {
            til_peso.setError(null);
            pesoValido = true;
        } else {
            til_peso.setError("El peso ingresado no es válido");
            til_peso.getEditText().setText("");
            pesoValido = false;
        }

        return fechaValida && pesoValido;
    }
}