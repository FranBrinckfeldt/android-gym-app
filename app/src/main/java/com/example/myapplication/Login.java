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

import com.example.myapplication.dao.UsuarioDAO;
import com.example.myapplication.model.Usuario;
import com.example.myapplication.utils.Validador;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextView tv_app_name, tv_login, tv_registrarse;
    TextInputLayout til_user, til_password;
    Button btn_login;
    UsuarioDAO dao;
    Usuario usuario;
    SharedPreferences preferences;

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

        preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

        if (preferences.contains("username")) {
            Intent intent = new Intent(getBaseContext(), Menu.class);
            startActivity(intent);
            finish();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()) {
                    String user = til_user.getEditText().getText().toString();
                    String password = til_password.getEditText().getText().toString();
                    dao = new UsuarioDAO(view.getContext());
                    usuario = dao.login(user, password);
                    if (usuario != null) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("id", usuario.getId());
                        editor.putString("username", usuario.getUsuario());
                        editor.putString("firstname", usuario.getNombre());
                        editor.putString("lastname", usuario.getApellido());
                        editor.putString("height", Double.toString(usuario.getEstatura()));
                        editor.commit();
                        Intent intent = new Intent(getBaseContext(), Menu.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(view.getContext(), "Bienvenido/a " + usuario.getNombre(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Usuario o contraseña incorrectos ", Toast.LENGTH_SHORT).show();
                    }
                }
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

    private boolean validar () {
        String user = til_user.getEditText().getText().toString();
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

        boolean passwordValida;
        if (Validador.requerido(password)) {
            til_password.setError(null);
            passwordValida = true;
        } else {
            til_password.setError("La contraseña es inválida");
            til_password.getEditText().setText("");
            passwordValida = false;
        }
        return userValido && passwordValida;
    }
}