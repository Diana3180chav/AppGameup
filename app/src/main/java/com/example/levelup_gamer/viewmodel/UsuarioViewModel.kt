package com.example.levelup_gamer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.Usuario
import com.example.levelup_gamer.model.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    //Este es el estado interno del mutable
    private val _estado = MutableStateFlow(Usuario())

    //Acá exponemos el estado para la UI
    val estado: StateFlow<Usuario> =_estado

    // Flag para indicar registro exitoso
    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso: StateFlow<Boolean> = _registroExitoso

    //Actualizaciones de estados

    //Rut
    fun onRutChange(valor : String) {
        _estado.update { it.copy(rut = valor, errores = it.errores.copy(rut = null))}
    }

    //Nombre
    fun onNombreChange(valor : String) {
        _estado.update { it.copy(userNam = valor, errores = it.errores.copy(userNam = null))}
    }

    //Correo
    fun onEmailChange(valor : String) {
        _estado.update { it.copy(email = valor, errores = it.errores.copy(email = null))}
    }

    //Contraseña
    fun onPasswordChange(valor : String) {
        _estado.update { it.copy(password = valor, errores = it.errores.copy(password = null))}
    }


    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        var errores = UsuarioErrores (
            rut = if (estadoActual.rut.isBlank()) "El rut es requerido" else null,
            userNam = if (estadoActual.userNam.isBlank()) "El nombre de usuario es requerido" else null,
            email = if (!estadoActual.email.contains("@")) "Correo inválido" else null,
            password = if (estadoActual.password.length < 6) "Debe tener al menos 6 caracteres" else null

        )

        val hayErrrores = listOfNotNull(
            errores.rut,
            errores.userNam,
            errores.email,
            errores.password
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrrores
    }

    fun limpiarCampos(){ //limpiamos los campos
        _estado.value = Usuario()
    }

    fun registrarUsuario(){ //función que nos ayuda a validar por consola el dato del usuarion registrado
        if(validarFormulario()){
            Log.d("UsuarioViewModel", "Usuario registrado: ${estado.value.userNam} - ${estado.value.email}")
            _registroExitoso.value = true
        } else  {
            _registroExitoso.value = false
        }
    }

    fun limpiarRegistroExitoso(){
        _registroExitoso.value = false
    }
}