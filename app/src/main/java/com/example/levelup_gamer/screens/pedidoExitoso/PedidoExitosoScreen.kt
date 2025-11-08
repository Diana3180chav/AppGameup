package com.example.levelup_gamer.screens.pedidoExitoso

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.viewmodel.InvitadoViewModel // <-- NUEVO IMPORT

@Composable
fun PedidoExitosoScreen(
    // --- MODIFICADO ---
    productoViewModel: ProductoViewModel, // <-- Renombrado para claridad
    invitadoViewModel: InvitadoViewModel, // <-- NUEVO
    onFinalizar: () -> Unit = {}
) {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("PedidoExitosoScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact ->
            PedidoExitosoScreenCompact(
                productoViewModel = productoViewModel,
                invitadoViewModel = invitadoViewModel, // <-- Pasar VM
                onFinalizar = onFinalizar
            )

        WindowWidthSizeClass.Medium  ->
            PedidoExitosoScreenMedium(
                productoViewModel = productoViewModel,
                invitadoViewModel = invitadoViewModel, // <-- Pasar VM
                onFinalizar = onFinalizar
            )

        WindowWidthSizeClass.Expanded ->
            PedidoExitosoScreenExpanded(
                productoViewModel = productoViewModel,
                invitadoViewModel = invitadoViewModel, // <-- Pasar VM
                onFinalizar = onFinalizar
            )
    }
}