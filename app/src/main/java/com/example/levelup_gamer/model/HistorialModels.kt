package com.example.levelup_gamer.model

/**
 * Representa el objeto raíz que se guarda en Preferences DataStore.
 *
 * Cuando usamos GSON (la librería de JSON), necesitamos un único objeto "contenedor"
 * que GSON pueda convertir a un string JSON. Esta clase cumple esa función.
 * Su único propósito es almacenar la lista completa de todos los pedidos.
 */
data class HistorialDePedidos(
    /**
     * La lista completa de todos los pedidos ([PedidoGuardado]) que
     * se han realizado y guardado en la app.
     */
    val pedidos: List<PedidoGuardado>
)

/**
 * Representa una "instantánea" (snapshot) de una sola compra que ya se completó.
 *
 * Esta clase captura toda la información relevante de un pedido en el
 * momento exacto en que el usuario presionó "Confirmar Pedido".
 */
data class PedidoGuardado(
    /**
     * Un identificador único para este pedido específico.
     * Usamos System.currentTimeMillis() para esto (la hora en milisegundos).
     */
    val id: String,

    /**
     * El objeto [Invitado] que contiene los datos del cliente que realizó
     * la compra (nombre, email, dirección, etc.).
     */
    val invitado: Invitado,

    /**
     * La lista de [CarritoItem] (productos y cantidades) que formaban
     * parte de este pedido.
     */
    val items: List<CarritoItem>,

    /**
     * El precio final (Double) que se pagó por este pedido.
     */
    val total: Double,

    /**
     * La fecha y hora exacta (en formato Long/milisegundos) en que
     * se guardó el pedido. Es útil para ordenar el historial.
     */
    val timestamp: Long
)