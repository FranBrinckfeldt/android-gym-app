package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.AdapterEvaluacion;
import com.example.myapplication.controller.RetrofitClient;
import com.example.myapplication.dao.EvaluacionDAO;
import com.example.myapplication.dto.AccessTokenDTO;
import com.example.myapplication.dto.EvaluacionDTO;
import com.example.myapplication.dto.LoginDTO;
import com.example.myapplication.model.Evaluacion;
import com.example.myapplication.model.IEvaluacion;
import com.example.myapplication.services.EvaluacionService;
import com.example.myapplication.services.UsuarioService;
import com.example.myapplication.ui.DatePickerFragment;
import com.example.myapplication.utils.BearerToken;
import com.example.myapplication.utils.LogOut;
import com.example.myapplication.utils.Validador;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Registros extends AppCompatActivity {

    TextView tv_app_name, tv_registros;
    TextInputLayout til_fecha_inicio, til_fecha_termino;
    ListView lv_evaluaciones;
    AdapterEvaluacion adapter;
    Button btn_filtrar, btn_eliminar_filtro;
    EvaluacionDAO dao;
    String[] fechas;
    Retrofit retrofit;
    SharedPreferences preferences;

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

        preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

        dao = new EvaluacionDAO(this);
        Bundle receivedObjects = getIntent().getExtras();

        if(receivedObjects != null) {
            fechas = receivedObjects.getStringArray("fechas");
            ArrayList<Evaluacion> evaluaciones = dao.findAllByDate(fechas);
            List<IEvaluacion> evaluacionesAdapter = new ArrayList<>();
            for (Evaluacion evaluacion : evaluaciones) {
                evaluacionesAdapter.add(evaluacion);
            }
            adapter = new AdapterEvaluacion(getBaseContext(), evaluacionesAdapter);
            til_fecha_inicio.getEditText().setText(fechas[0]);
            til_fecha_termino.getEditText().setText(fechas[1]);
            btn_eliminar_filtro.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = lv_evaluaciones.getLayoutParams();
            params.height = 420;
            lv_evaluaciones.setLayoutParams(params);
        } else {
            String token = preferences.getString("token", "");
            retrofit = RetrofitClient.getRetrofitInstance();
            EvaluacionService service = retrofit.create(EvaluacionService.class);
            Call<List<EvaluacionDTO>> call = service.findAll(BearerToken.get(token));
            call.enqueue(new Callback<List<EvaluacionDTO>>() {
                @Override
                public void onResponse(Call<List<EvaluacionDTO>> call, Response<List<EvaluacionDTO>> response) {
                    if(response.isSuccessful()) {
                        List<EvaluacionDTO> evaluaciones = response.body();
                        List<IEvaluacion> evaluacionesAdapter = new ArrayList<>();
                        for (EvaluacionDTO evaluacion : evaluaciones) {
                            evaluacionesAdapter.add(evaluacion);
                            Log.d("evaluacion", evaluacion.toString());
                        }
                        adapter = new AdapterEvaluacion(getBaseContext(), evaluacionesAdapter);
                        btn_eliminar_filtro.setVisibility(View.GONE);
                        ViewGroup.LayoutParams params = lv_evaluaciones.getLayoutParams();
                        params.height = 640;
                        lv_evaluaciones.setLayoutParams(params);

                        lv_evaluaciones.setAdapter(adapter);

                        lv_evaluaciones.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Evaluacion evaluacion = new Evaluacion(adapter.getItem(i));
                                Intent intent = new Intent(getBaseContext(), EditarEvaluacion.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("evaluacion", evaluacion);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    } else {
                        LogOut.exec(getBaseContext(), preferences, null);
                    }
                }

                @Override
                public void onFailure(Call<List<EvaluacionDTO>> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "Error al conectarse con el servicio", Toast.LENGTH_SHORT);
                }
            });
        }

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