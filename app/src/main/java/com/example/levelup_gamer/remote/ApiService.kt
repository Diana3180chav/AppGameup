package com.example.levelup_gamer.remote

import com.example.levelup_gamer.model.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    //Nos ayuda a obtener los datos de la API y el listado de usuarios en este caso
    @GET("/usuarios")
    suspend fun getUsuarios(): List<Usuario>

    //Nos ayuda a crear un usuario
    @POST("/usuarios")
    suspend fun crearUsuario(@Body usuario: Usuario): Usuario

}