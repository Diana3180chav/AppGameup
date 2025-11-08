package com.example.levelup_gamer.screens.formularioinvitado

import androidx.compose.runtime.Composable
import com.example.levelup_gamer.viewmodel.InvitadoViewModel

@Composable
fun FormularioInvitadoScreenMedium(
    invitadoViewModel: InvitadoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToCheckout: () -> Unit
) {
    // Por ahora, reutilizamos la vista Compact.
    // Aquí podrías crear una UI diferente para tablets.
    FormularioInvitadoScreenCompact(
        invitadoViewModel = invitadoViewModel,
        onNavigateBack = onNavigateBack,
        onNavigateToCheckout = onNavigateToCheckout
    )
}