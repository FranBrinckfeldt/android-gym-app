package com.example.myapplication.services;

import com.example.myapplication.dto.AccessTokenDTO;
import com.example.myapplication.dto.LoginDTO;
import com.example.myapplication.dto.ResponseDTO;
import com.example.myapplication.dto.UsuarioDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioService {

    @POST("/login")
    Call<AccessTokenDTO> login(@Body LoginDTO login);

    @POST("/register")
    Call<ResponseDTO> register(@Body UsuarioDTO usuario);

}
