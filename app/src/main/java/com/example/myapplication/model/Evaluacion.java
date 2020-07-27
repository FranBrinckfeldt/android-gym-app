package com.example.myapplication.model;

public class Evaluacion {

    private int id;
    private int uid;
    private String date;
    private double peso;
    private double imc;

    public Evaluacion(int id, int uid, String date, double peso, double imc) {
        this.id = id;
        this.uid = uid;
        this.date = date;
        this.peso = peso;
        this.imc = imc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

}
