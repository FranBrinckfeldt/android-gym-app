package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.dao.EvaluacionDAO;
import com.example.myapplication.dao.UsuarioDAO;
import com.example.myapplication.model.Usuario;
import com.example.myapplication.ui.DatePickerFragment;
import com.example.myapplication.utils.Validador;
import com.google.android.material.textfield.TextInputLayout;

public class RegistroUsuario extends AppCompatActivity {

    TextView tv_app_name, tv_signin;
    TextInputLayout til_user, til_name, til_lastname, til_date, til_estatura, til_password;
    Button btn_signin;
    UsuarioDAO dao;

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
                if (validar()) {
                    dao = new UsuarioDAO(view.getContext());
                    String user = til_user.getEditText().getText().toString();
                    String name = til_name.getEditText().getText().toString();
                    String lastname = til_lastname.getEditText().getText().toString();
                    String date = til_date.getEditText().getText().toString();
                    String estatura = til_estatura.getEditText().getText().toString();
                    String password = til_password.getEditText().getText().toString();
                    Usuario usuario = new Usuario(0, user, name, lastname, date, Double.parseDouble(estatura)).withClave(password);
                    if (dao.insert(usuario)) {
                        Intent intent = new Intent(getBaseContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(view.getContext(), "Se insertó el usuario", Toast.LENGTH_SHORT).show();
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
        String user = til_user.getEditText().getText().toString();
        String name = til_name.getEditText().getText().toString();
        String lastname = til_lastname.getEditText().getText().toString();
        String date = til_date.getEditText().getText().toString();
        String estatura = til_estatura.getEditText().getText().toString();
        String password = til_password.getEditText().getText().toString();

        boolean userValido;
        if (Validador.requerido(user)) {
            til_user.setError(null);
            userValido = true;
        } else {
            til_user.setError("El usuario ingresado no es válido");
            til_user.getEditText().setText("");
            userValido = false;
        }

        boolean nameValido;
        if (Validador.requerido(name)) {
            til_name.setError(null);
            nameValido = true;
        } else {
            til_name.setError("El nombre ingresado no es válido");
            til_name.getEditText().setText("");
            nameValido = false;
        }

        boolean lastnameValido;
        if (Validador.requerido(lastname)) {
            til_lastname.setError(null);
            lastnameValido = true;
        } else {
            til_lastname.setError("El apellido ingresado no es válido");
            til_lastname.getEditText().setText("");
            lastnameValido = false;
        }

        boolean dateValida;
        if (Validador.fecha(date)) {
            til_date.setError(null);
            dateValida = true;
        } else {
            til_date.setError("La fecha es inválida");
            til_date.getEditText().setText("");
            dateValida = false;
        }

        boolean estaturaValida;
        if (Validador.requerido(estatura) && Validador.mayorCero(estatura)) {
            til_estatura.setError(null);
            estaturaValida = true;
        } else {
            til_estatura.setError("La estatura ingresada no es válida");
            til_estatura.getEditText().setText("");
            estaturaValida = false;
        }

        boolean passwordValida;
        if (Validador.requerido(password)) {
            til_password.setError(null);
            passwordValida = true;
        } else {
            til_password.setError("La contraseña es inválida");
            til_password.getEditText().setText("");
            passwordValida = false;
        }

        return userValido && nameValido && lastnameValido && dateValida && estaturaValida && passwordValida;
    }

}