package com.example.levelup_gamer.repository.data

import android.util.Log
import com.example.levelup_gamer.dto.OrdenRequest
import com.example.levelup_gamer.repository.api.OrdenApiService

class OrdenRepository(
    private val api: OrdenApiService
) {
    suspend fun crearOrden(token: String, request: OrdenRequest) {
        val response = api.crearOrden(
            token = "Bearer $token",
            ordenRequest = request
        )

        Log.d("ORDEN", "HTTP code: ${response.code()}")

        if (!response.isSuccessful) {
            throw Exception("Error HTTP ${response.code()}")
        }
    }
}
