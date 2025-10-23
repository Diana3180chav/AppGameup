package com.example.levelup_gamer.model

data class CarritoItem(
    val producto: Producto,
    val cantidad: Int = 1 // Por defecto, la cantidad empieza en 1
)