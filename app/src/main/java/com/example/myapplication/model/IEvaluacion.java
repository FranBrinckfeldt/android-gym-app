package com.example.myapplication.model;

public interface IEvaluacion {

    IEvaluacion withId(int id);

    int getId();

    void setId(int id);

    int getUid();

    void setUid(int uid);

    String getDate();

    void setDate(String date);

    double getPeso();

    void setPeso(double peso);

    double getEstatura();

    void setEstatura(double estatura);

    double getImc();

    void setImc(double imc);

}
