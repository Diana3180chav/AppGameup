package com.example.levelup_gamer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.dto.OrdenItemRequest
import com.example.levelup_gamer.dto.OrdenRequest
import com.example.levelup_gamer.model.CarritoItem
import com.example.levelup_gamer.model.Invitado
import com.example.levelup_gamer.repository.data.OrdenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrdenViewModel(
    private val ordenRepository: OrdenRepository,
    private val tokenProvider: () -> String?
) : ViewModel() {

    private val _uiState = MutableStateFlow<OrdenUiState>(OrdenUiState.Idle)
    val uiState: StateFlow<OrdenUiState> = _uiState

    fun confirmarOrden(
        carrito: List<CarritoItem>,
        invitado: Invitado
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = OrdenUiState.Loading
                Log.d("ORDEN", "Entró a confirmarOrden()")

                val token = tokenProvider()
                if (token.isNullOrBlank()) {
                    _uiState.value = OrdenUiState.Error("Sesión expirada. Inicia sesión nuevamente.")
                    return@launch
                }

                val items = carrito.map {
                    OrdenItemRequest(
                        productoId = it.producto.idProducto.toLong(),
                        cantidad = it.cantidad,
                        precioUnitario = it.producto.precio
                    )
                }

                val total = carrito.sumOf { it.producto.precio * it.cantidad }

                val request = OrdenRequest(
                    items = items,
                    total = total,
                    metodoPago = "WEBPAY",
                    direccionEnvio = invitado.direccion,
                    telefono = invitado.telefono
                )


                ordenRepository.crearOrden(
                    token = token,
                    request = request
                )

                Log.d("ORDEN", "Orden creada correctamente")
                _uiState.value = OrdenUiState.Success

            } catch (e: Exception) {
                Log.e("ORDEN", "Error al crear orden", e)
                _uiState.value = OrdenUiState.Error(
                    e.message ?: "Error al crear la orden"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = OrdenUiState.Idle
    }
}