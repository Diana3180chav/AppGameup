package com.example.levelup_gamer.model

//Define la clases de los datos y la lígica de negocio. Acá solo se maneja información

data class Usuario(
    val rut : String = "",
    val userNam : String = "",
    val email : String = "",
    val password : String = "",
    val errores: UsuarioErrores = UsuarioErrores()
)