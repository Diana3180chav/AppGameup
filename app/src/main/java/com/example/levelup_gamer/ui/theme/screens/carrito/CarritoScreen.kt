package com.example.levelup_gamer.ui.theme.screens.carrito

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import androidx.compose.animation.ExperimentalAnimationApi

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CarritoScreen(
    productoViewModel: ProductoViewModel,
    onNavigateBack: () -> Unit
) {
    val windowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            // Esta llamada ahora es segura porque la función padre (CarritoScreen)
            // también está marcada como experimental.
            CarritoScreenCompact(
                productoViewModel = productoViewModel,
                onNavigateBack = onNavigateBack
            )
        }
        WindowWidthSizeClass.Medium -> {
            CarritoScreenMedium(
                productoViewModel = productoViewModel,
                onNavigateBack = onNavigateBack
            )
        }
        WindowWidthSizeClass.Expanded -> {
            CarritoScreenExpanded(
                productoViewModel = productoViewModel,
                onNavigateBack = onNavigateBack
            )
        }
    }
}