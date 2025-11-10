package com.example.levelup_gamer.ui.screens.carrito

import androidx.compose.runtime.Composable
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@Composable
fun CarritoScreenMedium(
    productoViewModel: ProductoViewModel,
    onNavigateBack: () -> Unit,
    // 1. CORRECCIÓN: El parámetro debe coincidir con el nuevo flujo
    onNavigateToFormularioInvitado: () -> Unit
) {
    // Por ahora, usamos el mismo diseño que el compacto.
    // En el futuro, podrías cambiar esto por un diseño de tablet.
    CarritoScreenCompact(
        productoViewModel = productoViewModel,
        onNavigateBack = onNavigateBack,
        // 2. CORRECCIÓN: Pasar el parámetro correcto
        onNavigateToFormularioInvitado = onNavigateToFormularioInvitado
    )
}