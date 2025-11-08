package com.example.levelup_gamer.screens.checkout

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.InvitadoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@Composable
fun CheckoutScreen(
    productoViewModel: ProductoViewModel,
    invitadoViewModel: InvitadoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPedidoExitoso: () -> Unit
) {
    val windowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CheckoutScreenCompact(
                productoViewModel = productoViewModel,
                invitadoViewModel = invitadoViewModel,
                onNavigateBack = onNavigateBack,
                onNavigateToPedidoExitoso = onNavigateToPedidoExitoso
            )
        }
        WindowWidthSizeClass.Medium -> {
            CheckoutScreenMedium(
                productoViewModel = productoViewModel,
                invitadoViewModel = invitadoViewModel,
                onNavigateBack = onNavigateBack,
                onNavigateToPedidoExitoso = onNavigateToPedidoExitoso
            )
        }
        WindowWidthSizeClass.Expanded -> {
            CheckoutScreenExpanded(
                productoViewModel = productoViewModel,
                invitadoViewModel = invitadoViewModel,
                onNavigateBack = onNavigateBack,
                onNavigateToPedidoExitoso = onNavigateToPedidoExitoso
            )
        }
    }
}