package com.example.levelup_gamer.repository.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:8080/api/auth"

    val api: ApiService by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL) //URL Base de la API
            .addConverterFactory(GsonConverterFactory.create()) // Conversor JSON
            .build()
            .create(ApiService::class.java) //Implementa la interfaz ApiService
    }
}