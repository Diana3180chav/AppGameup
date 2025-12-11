package com.example.levelup_gamer.repository.data

import com.example.levelup_gamer.repository.api.ProductoApiService

class ProductoRepository(private val api: ProductoApiService) {

    suspend fun obtenerProductos() = api.obtenerProductos()
}