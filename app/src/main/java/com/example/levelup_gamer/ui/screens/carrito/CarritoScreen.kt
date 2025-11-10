package com.example.levelup_gamer.ui.screens.carrito

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import androidx.compose.animation.ExperimentalAnimationApi

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CarritoScreen(
    productoViewModel: ProductoViewModel,
    onNavigateBack: () -> Unit,
    // --- MODIFICADO ---
    onNavigateToFormularioInvitado: () -> Unit // <- Renombrado
) {
    val windowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CarritoScreenCompact(
                productoViewModel = productoViewModel,
                onNavigateBack = onNavigateBack,
                // --- MODIFICADO ---
                onNavigateToFormularioInvitado = onNavigateToFormularioInvitado // <- Pasar el nuevo prop
            )
        }
        WindowWidthSizeClass.Medium -> {
            CarritoScreenMedium(
                productoViewModel = productoViewModel,
                onNavigateBack = onNavigateBack,
                // --- MODIFICADO ---
                onNavigateToFormularioInvitado = onNavigateToFormularioInvitado
            )
        }
        WindowWidthSizeClass.Expanded -> {
            CarritoScreenExpanded(
                productoViewModel = productoViewModel,
                onNavigateBack = onNavigateBack,
                // --- MODIFICADO ---
                onNavigateToFormularioInvitado = onNavigateToFormularioInvitado
            )
        }
    }
}