package com.example.myapplication.dto;

import com.example.myapplication.model.Evaluacion;
import com.example.myapplication.model.IEvaluacion;

public class EvaluacionDTO extends Evaluacion implements IEvaluacion {

    public EvaluacionDTO(int id, int uid, String date, double peso, double estatura, double imc) {
        super(id, uid, date, peso, estatura, imc);
    }

    public EvaluacionDTO(int uid, String date, double peso, double estatura, double imc) {
        super(uid, date, peso, estatura, imc);
    }

    public EvaluacionDTO() {
    }

    public EvaluacionDTO(IEvaluacion evaluacion) {
        super(evaluacion.getId(), evaluacion.getUid(), evaluacion.getDate(), evaluacion.getPeso(), evaluacion.getEstatura(), evaluacion.getImc());
    }

}
