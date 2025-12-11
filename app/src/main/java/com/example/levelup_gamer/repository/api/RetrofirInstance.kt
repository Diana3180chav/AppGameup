package com.example.levelup_gamer.repository.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    // Instancia única de Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Servicio general
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // Servicio de productos
    val productoApiService: ProductoApiService by lazy {
        retrofit.create(ProductoApiService::class.java)
    }
}
