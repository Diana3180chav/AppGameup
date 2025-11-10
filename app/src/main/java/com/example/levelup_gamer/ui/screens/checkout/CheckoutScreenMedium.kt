package com.example.levelup_gamer.ui.screens.checkout

import androidx.compose.runtime.Composable
import com.example.levelup_gamer.viewmodel.InvitadoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@Composable
fun CheckoutScreenMedium(
    productoViewModel: ProductoViewModel,
    invitadoViewModel: InvitadoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPedidoExitoso: () -> Unit
) {
    // Reutilizamos la vista Compact por ahora
    CheckoutScreenCompact(
        productoViewModel = productoViewModel,
        invitadoViewModel = invitadoViewModel,
        onNavigateBack = onNavigateBack,
        onNavigateToPedidoExitoso = onNavigateToPedidoExitoso
    )
}