package com.example.levelup_gamer.repository.data

import com.example.levelup_gamer.dto.UsuarioDTO
import com.example.levelup_gamer.model.Usuario
import com.example.levelup_gamer.repository.api.RetrofitInstance


class repository {
    suspend fun getUsuarios(): List<Usuario> {
        return RetrofitInstance.api.getUsuarios()
    }

    suspend fun crearUsuario(usuarioDTO: UsuarioDTO): Usuario {
        return RetrofitInstance.api.crearUsuario(usuarioDTO)
    }
}