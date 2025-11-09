package com.example.levelup_gamer.model

//Define la clases de los datos y la lígica de negocio. Acá solo se maneja información

data class Usuario(
    val rut : String = "",
    val userNam : String = "",
    val apellido : String = "",
    val email : String = "",
    val password : String = "",
    var telefono: String = "",
    val direccion : String = "",
    val region : String = "",
    val comuna : String = "",
    val errores: UsuarioErrores = UsuarioErrores()
)

//puse algunos datos comentados ya que se necesita la validación de otros desarrolladores, ya que la bbdd solicita más datos