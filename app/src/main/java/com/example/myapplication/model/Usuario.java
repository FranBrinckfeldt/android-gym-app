package com.example.myapplication.model;

public class Usuario {

    private int id;
    private String usuario;
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String clave;
    private double estatura;

    public Usuario(int id, String usuario, String nombre, String apellido, String fechaNacimiento, double estatura) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estatura = estatura;
    }

    public Usuario(String usuario, String nombre, String apellido, String fechaNacimiento, double estatura) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.estatura = estatura;
    }

    public Usuario() {
    }

    public Usuario withId(int id) {
        this.id = id;
        return this;
    }

    public Usuario withClave(String clave) {
        this.clave = clave;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }
}
