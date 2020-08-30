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
import com.example.myapplication.model.IEvaluacion;

import java.util.ArrayList;
import java.util.List;

public class AdapterEvaluacion extends BaseAdapter {

    private Context context;
    private List<IEvaluacion> evaluaciones;

    public AdapterEvaluacion(Context context, List<IEvaluacion> evaluaciones) {
        this.context = context;
        this.evaluaciones = evaluaciones;
    }

    @Override
    public int getCount() {
        return evaluaciones.size();
    }

    @Override
    public IEvaluacion getItem(int i) {
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
        TextView id = view.findViewById(R.id.id);
        TextView fecha = view.findViewById(R.id.fecha);
        TextView peso = view.findViewById(R.id.peso);
        TextView imc = view.findViewById(R.id.imc);

        id.setText(Integer.toString(evaluaciones.get(i).getId()));
        fecha.setText(evaluaciones.get(i).getDate());
        peso.setText(Double.toString(evaluaciones.get(i).getPeso()));
        imc.setText(String.format("%.1f", evaluaciones.get(i).getImc()));

        return view;
    }
}
