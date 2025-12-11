package com.example.levelup_gamer.ui.screens.login

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.activity.ComponentActivity
import com.example.levelup_gamer.viewmodel.LoginViewModel
import com.example.levelup_gamer.dto.LoginResponse
import com.example.levelup_gamer.dto.UsuarioDTO
import org.junit.Rule
import org.junit.Test
import io.mockk.mockk
import io.mockk.verify

class LoginScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    class LoginViewModelFake : LoginViewModel() {
        override fun iniciarSesion(
            onSuccess: (LoginResponse) -> Unit,
            onError: (String) -> Unit
        ) {
            _loginExitoso.value = true
            // Creamos un LoginResponse con datos dummy
            onSuccess(
                LoginResponse(
                    token = "demoToken123",
                    usuario = UsuarioDTO(
                        rut = "12345678-9",
                        nombre = "Demo",
                        apellido = "User",
                        correo = "demo@demo.com",
                        contrasena = "123456",
                        telefono = "123456789",
                        direccion = "Calle Falsa 123",
                        rol = "CLIENTE",
                        regionId = 1L,
                        comunaId = 1L
                    )
                )
            )
        }
    }


    @Test
    fun login_exitoso_cuando_credenciales_correctas() {
        // ViewModel real (sin lógica de red)
        val vm = LoginViewModelFake()

        // Mock de navegación:
        val onSuccess = mockk<() -> Unit>(relaxed = true)

        // Composable bajo prueba
        rule.setContent {
            LoginScreenCompact(
                viewModel = vm,
                onLoginSuccess = onSuccess
            )
        }

        // ---- ACT ----
        rule.onNodeWithText("Correo")
            .performTextInput("demo@demo.com")

        rule.onNodeWithText("Contraseña")
            .performTextInput("123456")

        rule.onNodeWithText("Entrar")
            .performClick()

        // ---- ASSERT ----
        verify { onSuccess.invoke() }
    }

    @Test
    fun muestra_error_si_email_es_invalido() {
        val vm = LoginViewModel()

        rule.setContent {
            LoginScreenCompact(viewModel = vm)
        }

        // Escribe email inválido
        rule.onNodeWithText("Correo")
            .performTextInput("correo-invalido")

        rule.onNodeWithText("Contraseña")
            .performTextInput("123456")

        // Presiona "Entrar"
        rule.onNodeWithText("Entrar").performClick()

        // Debe aparecer el error correspondiente
        rule.onNodeWithText("Formato de correo inválido")
            .assertIsDisplayed()
    }

    @Test
    fun muestra_error_si_password_muy_corta() {
        val vm = LoginViewModel()

        rule.setContent {
            LoginScreenCompact(viewModel = vm)
        }

        rule.onNodeWithText("Correo").performTextInput("demo@demo.com")
        rule.onNodeWithText("Contraseña").performTextInput("123")

        rule.onNodeWithText("Entrar").performClick()

        rule.onNodeWithText("Mínimo 6 caracteres")
            .assertIsDisplayed()
    }
}
