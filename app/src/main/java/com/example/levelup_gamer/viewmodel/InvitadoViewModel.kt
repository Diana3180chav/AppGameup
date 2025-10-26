package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.Invitado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InvitadoViewModel : ViewModel() {

    // Estado privado que guarda los datos del invitado
    private val _datosInvitado = MutableStateFlow(Invitado())
    // Estado público (solo lectura) para que la UI lo observe
    val datosInvitado: StateFlow<Invitado> = _datosInvitado.asStateFlow()

    // Funciones para actualizar cada campo
    fun onNombreChange(nombre: String) {
        _datosInvitado.update { it.copy(nombre = nombre) }
    }

    fun onEmailChange(email: String) {
        _datosInvitado.update { it.copy(email = email) }
    }

    fun onTelefonoChange(telefono: String) {
        _datosInvitado.update { it.copy(telefono = telefono) }
    }

    fun onDireccionChange(direccion: String) {
        _datosInvitado.update { it.copy(direccion = direccion) }
    }

    /**
     * Limpia los datos del formulario después de
     * que el pedido se completa.
     */
    fun limpiarDatos() {
        _datosInvitado.value = Invitado()
    }
}