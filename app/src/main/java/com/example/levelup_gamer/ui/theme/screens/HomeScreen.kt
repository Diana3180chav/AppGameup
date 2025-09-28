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
        WindowWidthSizeClass.Compact -> HomeScreenCompact()
        WindowWidthSizeClass.Medium -> HomeScreenMedium()
        WindowWidthSizeClass.Expanded -> HomeScreenExpanded()
    }
}