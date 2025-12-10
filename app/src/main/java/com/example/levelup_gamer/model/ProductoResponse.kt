package com.example.levelup_gamer.model

data class ProductoResponse(
    val id: Long,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val imagenUrl: String?,
    val imagenFile: String?,
    val stock: Int,
    val categoria: String?,
    val activo: Boolean
)