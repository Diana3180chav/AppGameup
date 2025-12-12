package com.example.levelup_gamer.model

import com.example.levelup_gamer.dto.UsuarioDTO   // importa tu DTO real

/**
 * Guarda en memoria al usuario actualmente logueado.
 * (Luego, si quieres, lo pasamos a DataStore para que persista).
 */
object UserSession {
    var token: String? = null
    var usuario: UsuarioDTO? = null

    fun clear() {
        token = null
        usuario = null
    }
}