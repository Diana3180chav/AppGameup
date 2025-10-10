package com.example.levelup_gamer.ui.theme.screens.register

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@Composable
fun RegisterScreen(onNavigateToHome: () -> Unit,
                   onNavigateToLogin: () -> Unit,
                   viewModel: UsuarioViewModel) {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("RegisterScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> RegisterScreenCompact(onNavigateToHome, onNavigateToLogin, viewModel)
        WindowWidthSizeClass.Medium -> RegisterScreenMedium(onNavigateToHome)
        WindowWidthSizeClass.Expanded -> RegisterScreenExpanded(onNavigateToHome)
    }
}
