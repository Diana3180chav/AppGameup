package com.example.levelup_gamer.ui.theme.screens.register

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass

@Composable
fun RegisterScreen() {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("RegisterScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> RegisterScreenCompact()
        WindowWidthSizeClass.Medium -> RegisterScreenMedium()
        WindowWidthSizeClass.Expanded -> RegisterScreenExpanded()
    }
}
