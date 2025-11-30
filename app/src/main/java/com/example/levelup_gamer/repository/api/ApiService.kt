package com.example.levelup_gamer.repository.api

import com.example.levelup_gamer.dto.UsuarioDTO
import com.example.levelup_gamer.model.Comuna
import com.example.levelup_gamer.model.Region
import com.example.levelup_gamer.model.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //Nos ayuda a obtener los datos de la API y el listado de usuarios en este caso
    @GET("/all")
    suspend fun getUsuarios(): List<Usuario>

    //Nos ayuda a crear un usuario
    @POST("auth/register")
    suspend fun crearUsuario(@Body usuarioDTO: UsuarioDTO): Usuario

    @GET("regiones")
    suspend fun getRegiones(): List<Region>

    @GET("comunas/region/{id}")
    suspend fun getComunas(@Path("id") id: Long): List<Comuna>

}