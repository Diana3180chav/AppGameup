package com.example.levelup_gamer.viewmodel

import com.example.levelup_gamer.datastore.HistorialRepository
import com.example.levelup_gamer.model.Invitado
import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.model.CarritoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import android.util.Log // <-- AÑADIR ESTE IMPORT

class ProductoViewModel(
    private val historialRepository: HistorialRepository
) : ViewModel() {

    // (El resto de tu código de _estadoCarrito no cambia)
    private val _estadoCarrito = MutableStateFlow<List<CarritoItem>>(emptyList())
    val estadoCarrito: StateFlow<List<CarritoItem>> = _estadoCarrito


    // (Tus funciones 'agregarAlCarrito', etc. no cambian)
    fun agregarAlCarrito(producto: Producto) {
        _estadoCarrito.update { listaActual ->
            val itemExistente = listaActual.find {
                it.producto.idProducto == producto.idProducto
            }

            if (itemExistente == null) {
                listaActual + CarritoItem(producto = producto, cantidad = 1)
            } else {
                listaActual.map { item ->
                    if (item.producto.idProducto == producto.idProducto) {
                        item.copy(cantidad = item.cantidad + 1)
                    } else {
                        item
                    }
                }
            }
        }
    }

    fun disminuirCantidad(item: CarritoItem) {
        _estadoCarrito.update { listaActual ->
            if (item.cantidad > 1) {
                listaActual.map {
                    if (it.producto.idProducto == item.producto.idProducto) {
                        it.copy(cantidad = it.cantidad - 1)
                    } else {
                        it
                    }
                }
            } else {
                listaActual.filterNot {
                    it.producto.idProducto == item.producto.idProducto
                }
            }
        }
    }

    fun eliminarProductoDelCarrito(item: CarritoItem) {
        _estadoCarrito.update { listaActual ->
            listaActual.filterNot {
                it.producto.idProducto == item.producto.idProducto
            }
        }
    }

    fun vaciarCarrito() {
        _estadoCarrito.value = emptyList()
    }


    // --- FUNCIÓN ACTUALIZADA CON LOG ---
    suspend fun guardarPedidoCompleto(invitado: Invitado, carrito: List<CarritoItem>, total: Double) {
        historialRepository.agregarPedido(invitado, carrito, total)
    }
}