package com.example.levelup_gamer.ui.theme.screens.register

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.RegisterViewModel
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@Composable
fun RegisterScreen(onNavigateToHome: () -> Unit,
                   onNavigateToLogin: () -> Unit,
                   viewModel: UsuarioViewModel,
                   RegisterViewModel: RegisterViewModel
) {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("RegisterScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> RegisterScreenCompact(onNavigateToHome, onNavigateToLogin, viewModel, RegisterViewModel)
        WindowWidthSizeClass.Medium -> RegisterScreenMedium(onNavigateToHome, onNavigateToLogin, viewModel)
        WindowWidthSizeClass.Expanded -> RegisterScreenExpanded(onNavigateToHome, onNavigateToLogin, viewModel)
    }
}
