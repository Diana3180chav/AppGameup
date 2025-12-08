package com.example.levelup_gamer.repository.api

import com.example.levelup_gamer.model.ProductoResponse
import retrofit2.http.GET

interface ProductoApiService {
    @GET("productos")
    suspend fun obtenerProductos(): List<ProductoResponse>
}