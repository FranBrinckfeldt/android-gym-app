package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.RegistroEvaluacion;
import com.example.myapplication.model.Evaluacion;

import java.util.ArrayList;

public class AdapterEvaluacion extends BaseAdapter {

    private Context context;
    private ArrayList<Evaluacion> evaluaciones;

    public AdapterEvaluacion(Context context, ArrayList<Evaluacion> evaluaciones) {
        this.context = context;
        this.evaluaciones = evaluaciones;
    }

    @Override
    public int getCount() {
        return evaluaciones.size();
    }

    @Override
    public Evaluacion getItem(int i) {
        return evaluaciones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return evaluaciones.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.list_item_eva, null);
        }
        TextView fecha = view.findViewById(R.id.fecha);
        TextView peso = view.findViewById(R.id.peso);
        TextView imc = view.findViewById(R.id.imc);
        TextView btn_delete = view.findViewById(R.id.btn_delete);

        fecha.setText(evaluaciones.get(i).getDate());
        peso.setText(Double.toString(evaluaciones.get(i).getPeso()));
        imc.setText(Double.toString(evaluaciones.get(i).getImc()));

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
