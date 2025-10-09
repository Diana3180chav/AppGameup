package com.example.levelup_gamer.model

//Define la clases de los datos y la lígica de negocio. Acá solo se maneja información

data class Usuario(
    val id : Int,
    val nombre : String,
    var correo : String,
    var contrasena : String
    //completé con estos datos, basándome en la interfaz de registro.
)