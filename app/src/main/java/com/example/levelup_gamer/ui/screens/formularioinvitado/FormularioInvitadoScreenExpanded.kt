package com.example.levelup_gamer.ui.screens.formularioinvitado

import androidx.compose.runtime.Composable
import com.example.levelup_gamer.viewmodel.InvitadoViewModel

@Composable
fun FormularioInvitadoScreenExpanded(
    invitadoViewModel: InvitadoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToCheckout: () -> Unit
) {
    // Por ahora, reutilizamos la vista Compact.
    // Aquí podrías crear una UI diferente para pantallas grandes.
    FormularioInvitadoScreenCompact(
        invitadoViewModel = invitadoViewModel,
        onNavigateBack = onNavigateBack,
        onNavigateToCheckout = onNavigateToCheckout
    )
}