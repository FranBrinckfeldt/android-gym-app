package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.myapplication.controller.RetrofitClient;
import com.example.myapplication.dao.UsuarioDAO;
import com.example.myapplication.dto.AccessTokenDTO;
import com.example.myapplication.dto.LoginDTO;
import com.example.myapplication.model.Usuario;
import com.example.myapplication.services.UsuarioService;
import com.example.myapplication.utils.Validador;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    TextView tv_app_name, tv_login, tv_registrarse;
    TextInputLayout til_user, til_password;
    Button btn_login;
    UsuarioDAO dao;
    Usuario usuario;
    SharedPreferences preferences;
    Retrofit retrofit;

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
                    retrofit = RetrofitClient.getRetrofitInstance();
                    UsuarioService service = retrofit.create(UsuarioService.class);
                    Call<AccessTokenDTO> call = service.login(new LoginDTO(user, password));
                    call.enqueue(new Callback<AccessTokenDTO>() {
                        @Override
                        public void onResponse(Call<AccessTokenDTO> call, Response<AccessTokenDTO> response) {
                            if (response.isSuccessful()) {
                                String token = response.body().getAccessToken();
                                JWT decodedToken = new JWT(token);
                                int id = Integer.parseInt(decodedToken.getSubject());
                                double height = decodedToken.getClaim("height").asDouble();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("id", id);
                                editor.putString("height", Double.toString(height));
                                editor.putString("token", token);
                                editor.commit();
                                Intent intent = new Intent(getBaseContext(), Menu.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(view.getContext(), "Bienvenido/a", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(view.getContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessTokenDTO> call, Throwable t) {
                            Log.d("retrofit", "falló");
                            Toast.makeText(view.getContext(), "Falló la conexión con el servicio", Toast.LENGTH_SHORT).show();
                        }
                    });
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