package com.example.levelup_gamer.model

import com.example.levelup_gamer.model.Producto // Asegúrate de que este import apunte a tu clase Producto

/**
 * Representa un solo ítem dentro del carrito de compras.
 *
 * Esta clase 'data' agrupa un [Producto] específico con la [cantidad]
 * de ese producto que el usuario desea comprar.
 *
 * Por ejemplo: "3 Teclados Gamer" sería un CarritoItem.
 */
data class CarritoItem(
    /**
     * El objeto [Producto] completo que se está agregando.
     * Contiene toda la información del producto (nombre, precio, id, etc.).
     */
    val producto: Producto,

    /**
     * La cantidad de unidades de este [producto] que el usuario ha agregado al carrito.
     * Se inicializa en 1 por defecto, ya que cuando un usuario agrega
     * un producto por primera vez, siempre empieza con una unidad.
     */
    val cantidad: Int = 1
)