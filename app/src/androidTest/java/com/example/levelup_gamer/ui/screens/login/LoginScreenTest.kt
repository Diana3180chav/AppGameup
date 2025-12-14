package com.example.levelup_gamer.ui.screens.login

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.levelup_gamer.dto.LoginResponse
import com.example.levelup_gamer.dto.UsuarioDTO
import com.example.levelup_gamer.viewmodel.LoginViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Fake ViewModel alineado con LoginViewModel real
     * - No usa Retrofit
     * - No usa SharedPreferences reales
     */
    class FakeLoginViewModel(
        application: Application
    ) : LoginViewModel(application) {

        override fun iniciarSesion(
            onSuccess: (LoginResponse) -> Unit,
            onError: (String) -> Unit
        ) {
            // Simula login exitoso
            _loginExitoso.value = true

            onSuccess(
                LoginResponse(
                    token = "fake-token-123",
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
        val context = rule.activity.application
        val vm = FakeLoginViewModel(context)

        val onSuccess = mockk<() -> Unit>(relaxed = true)

        rule.setContent {
            LoginScreenCompact(
                viewModel = vm,
                onLoginSuccess = onSuccess
            )
        }

        // ACT
        rule.onNodeWithText("Correo")
            .performTextInput("demo@demo.com")

        rule.onNodeWithText("Contraseña")
            .performTextInput("123456")

        rule.onNodeWithText("Entrar")
            .performClick()

        // ASSERT
        verify { onSuccess.invoke() }
    }

    @Test
    fun muestra_error_si_email_es_invalido() {
        val context = rule.activity.application
        val vm = FakeLoginViewModel(context)

        rule.setContent {
            LoginScreenCompact(viewModel = vm)
        }

        rule.onNodeWithText("Correo")
            .performTextInput("correo-invalido")

        rule.onNodeWithText("Contraseña")
            .performTextInput("123456")

        rule.onNodeWithText("Entrar")
            .performClick()

        rule.onNodeWithText("Formato de correo inválido")
            .assertIsDisplayed()
    }

    @Test
    fun muestra_error_si_password_muy_corta() {
        val context = rule.activity.application
        val vm = FakeLoginViewModel(context)

        rule.setContent {
            LoginScreenCompact(viewModel = vm)
        }

        rule.onNodeWithText("Correo")
            .performTextInput("demo@demo.com")

        rule.onNodeWithText("Contraseña")
            .performTextInput("123")

        rule.onNodeWithText("Entrar")
            .performClick()

        rule.onNodeWithText("Mínimo 6 caracteres")
            .assertIsDisplayed()
    }
}