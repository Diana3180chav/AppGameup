package com.example.levelup_gamer.repository.api

import com.example.levelup_gamer.dto.OrdenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OrdenApiService {

    @POST("ordenes/crear")
    suspend fun crearOrden(
        @Header("Authorization") token: String,
        @Body ordenRequest: OrdenRequest
    ): retrofit2.Response<Any>
}
