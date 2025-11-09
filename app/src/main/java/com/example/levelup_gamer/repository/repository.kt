package com.example.levelup_gamer.repository

import com.example.levelup_gamer.model.Usuario
import com.example.levelup_gamer.remote.RetrofitInstance


class repository {
    suspend fun getUsuarios(): List<Usuario> {
        return RetrofitInstance.api.getUsuarios()
    }

    suspend fun crearUsuario(usuario: Usuario): Usuario {
        return RetrofitInstance.api.crearUsuario(usuario)
    }
}