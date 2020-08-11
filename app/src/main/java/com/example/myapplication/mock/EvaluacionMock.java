package com.example.myapplication.mock;

import com.example.myapplication.model.Evaluacion;

import java.util.ArrayList;

public class EvaluacionMock {

    public static ArrayList<Evaluacion> getAll() {
        ArrayList<Evaluacion> evaluaciones = new ArrayList<>();
        evaluaciones.add(new Evaluacion(1, 1, "2020/03/24", 70, 1.60));
        evaluaciones.add(new Evaluacion(2, 1, "2020/04/05", 69, 1.70));
        evaluaciones.add(new Evaluacion(3, 1, "2020/04/23", 68, 1.52));
        evaluaciones.add(new Evaluacion(4, 1, "2020/05/01", 67, 1.67));
        evaluaciones.add(new Evaluacion(5, 1, "2020/05/30", 66, 1.76));
        evaluaciones.add(new Evaluacion(6, 1, "2020/06/15", 80, 1.90));
        return evaluaciones;
    }
}
