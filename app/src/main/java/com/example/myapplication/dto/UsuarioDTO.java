package com.example.myapplication.dto;

import com.example.myapplication.model.Usuario;

public class UsuarioDTO extends Usuario {

    public UsuarioDTO(int id, String usuario, String nombre, String apellido, String fechaNacimiento, double estatura) {
        super(id, usuario, nombre, apellido, fechaNacimiento, estatura);
    }

    public UsuarioDTO(String usuario, String nombre, String apellido, String fechaNacimiento, double estatura) {
        super(usuario, nombre, apellido, fechaNacimiento, estatura);
    }

    public UsuarioDTO() {
    }
}