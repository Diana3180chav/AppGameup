package com.example.levelup_gamer.dto

data class UsuarioDTO(
    val rut: String,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasena: String,
    val telefono: String,
    val direccion: String,
    val rol: String = "USER",
    val regionId: Long,
    val comunaId: Long
)
