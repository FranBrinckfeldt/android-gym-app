package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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

public class RegistroEvaluacion extends AppCompatActivity {

    TextView tv_imc;
    TextInputLayout til_register_date, til_peso;
    Button btn_save;
    EvaluacionDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_evaluacion);

        tv_imc = findViewById(R.id.tv_imc);
        til_register_date = findViewById(R.id.til_register_date);
        til_peso = findViewById(R.id.til_peso);
        btn_save = findViewById(R.id.btn_save);

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
                    // TODO: Modificar estatura cuando se tenga la base de datos.
                    tv_imc.setText(String.format("%.1f", peso / (1.60 * 1.60)));
                } else {
                    tv_imc.setText(getString(R.string.ingrese_peso));
                }
            }
        });

        til_register_date.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(til_register_date);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()) {
                    dao = new EvaluacionDAO(view.getContext());
                    String register_date = til_register_date.getEditText().getText().toString();
                    String peso = til_peso.getEditText().getText().toString();
                    // TODO: Estatura debe venir desde el usuario.
                    Evaluacion evaluacion = new Evaluacion(0, 0, register_date, Double.parseDouble(peso), 1.60);
                    if(dao.insert(evaluacion)) {
                        Intent intent = new Intent(getBaseContext(), Registros.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(view.getContext(), "Se insertó evaluación", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Hubo un error al insertar en la base de datos", Toast.LENGTH_SHORT).show();
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