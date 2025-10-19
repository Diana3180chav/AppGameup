package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductoViewModel : ViewModel() {

    private val _estadoCarrito = MutableStateFlow<List<Producto>>(emptyList())

    val estadoCarrito: StateFlow<List<Producto>> = _estadoCarrito //creamos una lista de productos

    fun agregarAlCarrito(producto: Producto) {

        _estadoCarrito.value = _estadoCarrito.value + producto //esto nos ayuda a que cada vez que presionemos el botón, no se reemplace el producto anterior ya seleccionado
    }





}