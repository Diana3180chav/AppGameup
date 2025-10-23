package com.example.levelup_gamer.ui.theme.screens.carrito

import androidx.compose.runtime.Composable
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@Composable
fun CarritoScreenExpanded(
    productoViewModel: ProductoViewModel,
    onNavigateBack: () -> Unit
) {
    // Igualmente, usamos el diseño compacto.
    CarritoScreenCompact(
        productoViewModel = productoViewModel,
        onNavigateBack = onNavigateBack
    )
}