// LoginScreen.kt
package com.example.levelup_gamer.ui.theme.screens.login

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("LoginScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> LoginScreenCompact(onLoginSuccess, onNavigateToRegister)
        WindowWidthSizeClass.Medium  -> LoginScreenMedium(onLoginSuccess, onNavigateToRegister)
        WindowWidthSizeClass.Expanded -> LoginScreenExpanded(onLoginSuccess, onNavigateToRegister)
    }
}
