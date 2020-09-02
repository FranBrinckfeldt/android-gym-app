package com.example.myapplication.services;

import com.example.myapplication.dto.EvaluacionDTO;
import com.example.myapplication.dto.ResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EvaluacionService {

    @GET("/evaluaciones")
    Call<List<EvaluacionDTO>> findAll(@Header("Authorization") String token);

    @GET("/evaluaciones/{id}")
    Call<EvaluacionDTO> findById(@Path("uid") String uid, @Header("Authorization") String token);

    @POST("/evaluaciones")
    Call<ResponseDTO> post(@Body EvaluacionDTO evaluacion, @Header("Authorization") String token);

    @DELETE("/evaluaciones/{id}")
    Call<ResponseDTO> delete(@Path("uid") String uid, @Header("Authorization") String token);

    @PUT("/evaluaciones/{id}")
    Call<ResponseDTO> put(@Path("uid") String uid, @Body EvaluacionDTO evaluacion, @Header("Authorization") String token);

}
