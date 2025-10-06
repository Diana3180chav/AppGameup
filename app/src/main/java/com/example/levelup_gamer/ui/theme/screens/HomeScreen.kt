package com.example.levelup_gamer.ui.theme.screens

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass

@Composable
fun  HomeScreen( onNavigateToRegister: () -> Unit,) {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("HomeScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")
    when (windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenCompact(onNavigateToRegister) //versión para pantallas pequeñas (celulares).
        WindowWidthSizeClass.Medium -> HomeScreenMedium(onNavigateToRegister) //versión para pantallas medianas (tablets verticales).
        WindowWidthSizeClass.Expanded -> HomeScreenExpanded(onNavigateToRegister) //versión para pantallas grandes (tablets horizontales o desktop).
    }
}