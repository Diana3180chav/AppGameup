package com.example.levelup_gamer.ui.screens.carrito

import androidx.compose.runtime.Composable
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@Composable
fun CarritoScreenExpanded(
    productoViewModel: ProductoViewModel,
    onNavigateBack: () -> Unit,
    // 1. CORRECCIÓN: El parámetro debe coincidir con el nuevo flujo
    onNavigateToFormularioInvitado: () -> Unit
) {
    // 2. CORRECCIÓN: No definas otra función aquí.
    //    Simplemente llama a 'CarritoScreenCompact'

    // Igualmente, usamos el diseño compacto.
    CarritoScreenCompact(
        productoViewModel = productoViewModel,
        onNavigateBack = onNavigateBack,
        // 3. CORRECCIÓN: Pasar el parámetro correcto
        onNavigateToFormularioInvitado = onNavigateToFormularioInvitado
    )
}