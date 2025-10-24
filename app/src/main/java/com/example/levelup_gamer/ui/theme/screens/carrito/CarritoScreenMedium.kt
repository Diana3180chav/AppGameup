package com.example.levelup_gamer.ui.theme.screens.carrito

import androidx.compose.runtime.Composable
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@Composable
fun CarritoScreenMedium(
    productoViewModel: ProductoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPedidoExitoso: () -> Unit
) {
    // Por ahora, usamos el mismo diseño que el compacto.
    // En el futuro, podrías cambiar esto por un diseño de tablet.
    CarritoScreenCompact(
        productoViewModel = productoViewModel,
        onNavigateBack = onNavigateBack,
        onNavigateToPedidoExitoso = onNavigateToPedidoExitoso
    )
}