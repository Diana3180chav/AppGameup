package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.Invitado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel que administra el estado del formulario del invitado.
 * Su única responsabilidad es guardar lo que el usuario escribe.
 */
class InvitadoViewModel : ViewModel() {

    // 1. ESTADO PRIVADO (Mutable):
    //    '_datosInvitado' es el estado interno que SÍ podemos cambiar.
    //    Es privado para que la UI no lo modifique por accidente.
    private val _datosInvitado = MutableStateFlow(Invitado())

    // 2. ESTADO PÚBLICO (Solo Lectura):
    //    'datosInvitado' es la versión pública que la UI "observa".
    //    La UI solo puede LEER de aquí.
    val datosInvitado: StateFlow<Invitado> = _datosInvitado.asStateFlow()

    // 3. FUNCIONES DE EVENTO (Mutaciones):
    //    La UI llama a estas funciones cuando el usuario escribe.
    //    Esta es la única forma de cambiar el estado.
    fun onNombreChange(nombre: String) {
        // 4. ACTUALIZACIÓN SEGURA (Inmutabilidad):
        //    Usamos '.update' y '.copy()' para crear un objeto de estado
        //    COMPLETAMENTE NUEVO en lugar de modificar el antiguo.
        //    Esto es clave para que StateFlow detecte el cambio.
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
     * 5. FUNCIÓN DE RESETEO:
     * Llamada desde AppNavigation después de una compra exitosa
     * para limpiar el formulario.
     */
    fun limpiarDatos() {
        _datosInvitado.value = Invitado() // Asigna un objeto nuevo y vacío
    }
}