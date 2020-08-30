package com.example.myapplication.model;

import java.io.Serializable;

public class Evaluacion implements Serializable, IEvaluacion {

    private int id;
    private int uid;
    private String date;
    private double peso;
    private double estatura;
    private double imc;

    public Evaluacion(int id, int uid, String date, double peso, double estatura, double imc) {
        this.id = id;
        this.uid = uid;
        this.date = date;
        this.peso = peso;
        this.estatura = estatura;
        this.imc = imc;
    }

    public Evaluacion(int uid, String date, double peso, double estatura, double imc) {
        this.uid = uid;
        this.date = date;
        this.peso = peso;
        this.estatura = estatura;
        this.imc = imc;
    }

    public Evaluacion() {
    }

    public Evaluacion(IEvaluacion evaluacion) {
        this.id = evaluacion.getId();
        this.uid = evaluacion.getUid();
        this.date = evaluacion.getDate();
        this.peso = evaluacion.getPeso();
        this.estatura = evaluacion.getEstatura();
        this.imc = evaluacion.getImc();
    }

    @Override
    public Evaluacion withId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public double getPeso() {
        return peso;
    }

    @Override
    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public double getEstatura() {
        return estatura;
    }

    @Override
    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    @Override
    public double getImc() {
        return imc;
    }

    @Override
    public void setImc(double imc) {
        this.imc = imc;
    }

    @Override
    public String toString() {
        return "Evaluacion{" +
                "id=" + id +
                ", uid=" + uid +
                ", date='" + date + '\'' +
                ", peso=" + peso +
                ", estatura=" + estatura +
                ", imc=" + imc +
                '}';
    }
}
