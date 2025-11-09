package com.example.levelup_gamer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Usuario
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.repository.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    //Este es el estado interno del mutable
    private val _estado = MutableStateFlow(Usuario())

    private val repository = repository()

    private val _getList = MutableStateFlow<List<Usuario>>(emptyList())

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

    fun onApellidoChange(valor : String){
        _estado.update { it.copy(apellido = valor, errores = it.errores.copy(apellido = null))}
    }

    //Correo
    fun onEmailChange(valor : String) {
        _estado.update { it.copy(email = valor, errores = it.errores.copy(email = null))}
    }

    //Contraseña
    fun onPasswordChange(valor : String) {
        _estado.update { it.copy(password = valor, errores = it.errores.copy(password = null))}
    }

    fun onTelefonoChange(valor : String) {
        _estado.update { it.copy(telefono = valor, errores = it.errores.copy(telefono = null))}
    }

    fun onDireccionChange(valor : String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null))}
    }

    fun onRegionChange( valor : String ){
        _estado.update { it.copy(region = valor, errores = it.errores.copy(region = null))}

    }

    fun onComunaChange( valor : String ){
        _estado.update { it.copy(comuna = valor, errores = it.errores.copy(comuna = null))}
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

    fun fetchPost(usuario: Usuario){
        viewModelScope.launch {
            try {
                val nuevoUsuario = repository.crearUsuario(usuario)

                _getList.value = _getList.value + nuevoUsuario
            } catch (e: Exception){
                println("Error al cargar los datos ${e.localizedMessage}")
            }
        }
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

