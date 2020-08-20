package com.example.myapplication.utils;

public class Imc {

    public static double calcular(double peso, double estatura) {
        try {
            return peso / (estatura * estatura);
        } catch (Exception e) {
            return 0;
        }
    }
}
