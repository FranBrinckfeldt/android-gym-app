package com.example.myapplication.model;

import java.io.Serializable;

public class Evaluacion implements Serializable {

    private int id;
    private int uid;
    private String date;
    private double peso;
    private double estatura;

    public Evaluacion(int id, int uid, String date, double peso, double estatura) {
        this.id = id;
        this.uid = uid;
        this.date = date;
        this.peso = peso;
        this.estatura = estatura;
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

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double calcularIMC() {
        try {
            return this.peso / (this.estatura * this.estatura);
        } catch (Exception e) {
            return 0;
        }
    }

}
