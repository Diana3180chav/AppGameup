package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.model.CarritoItem // 1. Importar el nuevo modelo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProductoViewModel : ViewModel() {

    // 2. El estado ahora es una LISTA DE CarritoItem
    private val _estadoCarrito = MutableStateFlow<List<CarritoItem>>(emptyList())
    val estadoCarrito: StateFlow<List<CarritoItem>> = _estadoCarrito

    /**
     * Esta función ahora es más inteligente:
     * - Si el producto no está en el carrito, lo agrega con cantidad 1.
     * - Si el producto YA ESTÁ, simplemente incrementa su cantidad.
     */
    fun agregarAlCarrito(producto: Producto) {
        _estadoCarrito.update { listaActual ->
            // Buscamos si el item ya existe en el carrito
            val itemExistente = listaActual.find {
                it.producto.idProducto == producto.idProducto
            }

            if (itemExistente == null) {
                // No existe: lo agregamos como un nuevo CarritoItem
                listaActual + CarritoItem(producto = producto, cantidad = 1)
            } else {
                // Ya existe: creamos una nueva lista actualizada
                listaActual.map { item ->
                    if (item.producto.idProducto == producto.idProducto) {
                        // Encontramos el item, le aumentamos la cantidad
                        item.copy(cantidad = item.cantidad + 1)
                    } else {
                        // Este es otro item, lo dejamos como está
                        item
                    }
                }
            }
        }
    }

    /**
     * Función NUEVA: Disminuye la cantidad de un item.
     * - Si la cantidad es > 1, la reduce en 1.
     * - Si la cantidad es 1, lo elimina de la lista.
     */
    fun disminuirCantidad(item: CarritoItem) {
        _estadoCarrito.update { listaActual ->
            if (item.cantidad > 1) {
                // Simplemente reducimos la cantidad
                listaActual.map {
                    if (it.producto.idProducto == item.producto.idProducto) {
                        it.copy(cantidad = it.cantidad - 1)
                    } else {
                        it
                    }
                }
            } else {
                // La cantidad es 1, así que lo eliminamos
                listaActual.filterNot {
                    it.producto.idProducto == item.producto.idProducto
                }
            }
        }
    }

    /**
     * Función NUEVA: Elimina un producto del carrito,
     * sin importar la cantidad que tenga.
     */
    fun eliminarProductoDelCarrito(item: CarritoItem) {
        _estadoCarrito.update { listaActual ->
            listaActual.filterNot {
                it.producto.idProducto == item.producto.idProducto
            }
        }
    }

    /**
     * Vacía completamente el carrito.
     */
    fun vaciarCarrito() {
        _estadoCarrito.value = emptyList()
    }
}