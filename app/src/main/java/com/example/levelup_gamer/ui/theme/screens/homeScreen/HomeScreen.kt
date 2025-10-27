package com.example.levelup_gamer.ui.theme.screens.home

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@Composable
fun  HomeScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToCarrito: () -> Unit,
    productoViewModel: ProductoViewModel
) {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("HomeScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")
    when (windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenCompact(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToCarrito = onNavigateToCarrito,
            productoViewModel = productoViewModel
        )
        WindowWidthSizeClass.Medium -> HomeScreenMedium(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToCarrito = onNavigateToCarrito,
            productoViewModel = productoViewModel
        )
        WindowWidthSizeClass.Expanded -> HomeScreenExpanded(
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToCarrito = onNavigateToCarrito,
            productoViewModel = productoViewModel
        )
    }
}