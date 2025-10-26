package com.example.levelup_gamer.ui.theme.screens.formularioinvitado

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.InvitadoViewModel

/**
 * Esta es la pantalla "principal" o "cabeza"
 * que decide qué Composable mostrar según el tamaño.
 */
@Composable
fun FormularioInvitadoScreen(
    invitadoViewModel: InvitadoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToCheckout: () -> Unit
) {
    val windowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            FormularioInvitadoScreenCompact(
                invitadoViewModel = invitadoViewModel,
                onNavigateBack = onNavigateBack,
                onNavigateToCheckout = onNavigateToCheckout
            )
        }
        WindowWidthSizeClass.Medium -> {
            FormularioInvitadoScreenMedium(
                invitadoViewModel = invitadoViewModel,
                onNavigateBack = onNavigateBack,
                onNavigateToCheckout = onNavigateToCheckout
            )
        }
        WindowWidthSizeClass.Expanded -> {
            FormularioInvitadoScreenExpanded(
                invitadoViewModel = invitadoViewModel,
                onNavigateBack = onNavigateBack,
                onNavigateToCheckout = onNavigateToCheckout
            )
        }
    }
}