package com.example.levelup_gamer.dto


data class OrdenItemRequest(
    val productoId: Long,
    val cantidad: Int,
    val precioUnitario: Double
)
