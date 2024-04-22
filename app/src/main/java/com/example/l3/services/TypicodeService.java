package com.example.l3.services;

import com.example.l3.dto.Numeros;
import com.example.l3.dto.Peliculas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TypicodeService {

    @GET("/primeNumbers?len=999&order=1")
    Call<List<Numeros>> getNumeros();

//      para  orden inverso
//    @GET("/primeNumbers?len=999&order=-1&max=999&type=order")
//    Call<List<Numeros>> getNumeros();

    @GET("/?apikey=bf81d461&i=tt3896198")
    Call<Peliculas> getPeliculas();
}
