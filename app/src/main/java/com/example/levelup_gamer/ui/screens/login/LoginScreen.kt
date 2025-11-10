package com.example.levelup_gamer.ui.screens.login

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,                 // <- pasamos el VM
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("LoginScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact ->
            LoginScreenCompact(
                viewModel = viewModel,
                onLoginSuccess = onLoginSuccess,
                onNavigateToRegister = onNavigateToRegister,
                onNavigateToHome = onNavigateToHome
            )

        WindowWidthSizeClass.Medium  ->
            LoginScreenMedium(
                viewModel = viewModel,
                onLoginSuccess = onLoginSuccess,
                onNavigateToRegister = onNavigateToRegister,
                onNavigateToHome = onNavigateToHome
            )

        WindowWidthSizeClass.Expanded ->
            LoginScreenExpanded(
                viewModel = viewModel,
                onLoginSuccess = onLoginSuccess,
                onNavigateToRegister = onNavigateToRegister,
                onNavigateToHome = onNavigateToHome
            )
    }
}
