package com.example.levelup_gamer.dto


data class OrdenRequest(
    val items: List<OrdenItemRequest>,
    val total: Double,
    val metodoPago: String,
    val direccionEnvio: String,
    val telefono: String,
    val notas: String? = null
)
