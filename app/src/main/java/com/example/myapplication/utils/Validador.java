package com.example.myapplication.utils;

import java.util.regex.Pattern;

public class Validador {

    public static boolean requerido (String texto) {
        return texto.trim().length() > 0;
    }

    public static boolean entero (String texto) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        return pattern.matcher(texto).matches();
    }

    public static boolean mayorCero (String texto) {
        try {
            return Double.parseDouble(texto) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean fecha (String texto) {
        Pattern pattern = Pattern.compile("([12]\\d{3}/(0[1-9]|1[0-2])/([12]\\d|3[01]|0[1-9]))");
        return pattern.matcher(texto).matches();
    }

    public static boolean fechaVacia (String texto) {
        Pattern pattern = Pattern.compile("([12]\\d{3}/(0[1-9]|1[0-2])/([12]\\d|3[01]|0[1-9]))");
        return texto.isEmpty() || pattern.matcher(texto).matches();
    }
}
