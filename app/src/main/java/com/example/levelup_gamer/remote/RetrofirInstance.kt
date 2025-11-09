package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: ApiService by lazy{
        Retrofit.Builder()
            .baseUrl("https:////localhost:3306/level_up_store_db") //URL Base de la API
            .addConverterFactory(GsonConverterFactory.create()) // Conversor JSON
            .build()
            .create(ApiService::class.java) //Implementa la interfaz ApiService
    }
}