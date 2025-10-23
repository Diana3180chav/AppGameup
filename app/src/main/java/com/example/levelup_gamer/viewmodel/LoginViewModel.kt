package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ErroresLogin(
    val email: String? = null,
    val password: String? = null
)

data class EstadoLogin(
    val email: String = "",
    val password: String = "",
    val errores: ErroresLogin = ErroresLogin()
)

class LoginViewModel : ViewModel() {

    private val _estadoLogin = MutableStateFlow(EstadoLogin())
    val estadoLogin: StateFlow<EstadoLogin> = _estadoLogin.asStateFlow()

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso.asStateFlow()

    fun onEmailChange(nuevo: String) {
        _estadoLogin.value = _estadoLogin.value.copy(
            email = nuevo,
            errores = _estadoLogin.value.errores.copy(email = null)
        )
    }

    fun onPasswordChange(nuevo: String) {
        _estadoLogin.value = _estadoLogin.value.copy(
            password = nuevo,
            errores = _estadoLogin.value.errores.copy(password = null)
        )
    }

    fun validarLogin(): Boolean {
        val st = _estadoLogin.value
        var emailErr: String? = null
        var passErr: String? = null

        if (st.email.isBlank()) emailErr = "El correo es obligatorio"
        else if (!st.email.contains("@")) emailErr = "Formato de correo inválido"

        if (st.password.length < 6) passErr = "Mínimo 6 caracteres"

        val ok = (emailErr == null && passErr == null)
        if (!ok) {
            _estadoLogin.value = st.copy(
                errores = ErroresLogin(email = emailErr, password = passErr)
            )
        }
        return ok
    }

    fun iniciarSesion(
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val st = _estadoLogin.value
        val credencialesOk = (st.email == "demo@demo.com" && st.password == "123456")
        if (credencialesOk) {
            _loginExitoso.value = true
            onSuccess()
        } else {
            _loginExitoso.value = false
            onError("Credenciales inválidas")
        }
    }

}
