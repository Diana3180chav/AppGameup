package com.example.levelup_gamer.ui.theme.screens

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass

@Composable
fun  HomeScreen() {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("HomeScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")
    when (windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenCompact() //versión para pantallas pequeñas (celulares).
        WindowWidthSizeClass.Medium -> HomeScreenMedium() //versión para pantallas medianas (tablets verticales).
        WindowWidthSizeClass.Expanded -> HomeScreenExpanded() //versión para pantallas grandes (tablets horizontales o desktop).
    }
}