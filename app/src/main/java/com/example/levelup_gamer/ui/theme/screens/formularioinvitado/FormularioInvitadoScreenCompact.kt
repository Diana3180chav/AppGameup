package com.example.levelup_gamer.ui.theme.screens.formularioinvitado

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // <-- CAMBIO: Necesario
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.viewmodel.InvitadoViewModel
import com.example.levelup_gamer.ui.theme.* // Importa tus colores

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioInvitadoScreenCompact(
    invitadoViewModel: InvitadoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToCheckout: () -> Unit
) {
    val invitado by invitadoViewModel.datosInvitado.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = loginBg, // <-- CAMBIO
        topBar = {
            TopAppBar(
                title = { Text("Datos de Invitado", color = neonBlue) }, // <-- CAMBIO
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = textOnDark // <-- CAMBIO
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = loginBg // <-- CAMBIO
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Completa tus datos para continuar con el envío",
                style = MaterialTheme.typography.titleMedium,
                color = textOnDark // <-- CAMBIO
            )

            // Campo de Nombre
            OutlinedTextField(
                value = invitado.nombre,
                onValueChange = { invitadoViewModel.onNombreChange(it) },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = textFieldColors() // <-- CAMBIO: Colores del helper
            )

            // Campo de Email
            OutlinedTextField(
                value = invitado.email,
                onValueChange = { invitadoViewModel.onEmailChange(it) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = textFieldColors() // <-- CAMBIO: Colores del helper
            )

            // Campo de Teléfono
            OutlinedTextField(
                value = invitado.telefono,
                onValueChange = { invitadoViewModel.onTelefonoChange(it) },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = textFieldColors() // <-- CAMBIO: Colores del helper
            )

            // Campo de Dirección
            OutlinedTextField(
                value = invitado.direccion,
                onValueChange = { invitadoViewModel.onDireccionChange(it) },
                label = { Text("Dirección de Envío") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = textFieldColors() // <-- CAMBIO: Colores del helper
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón Continuar
            Button(
                onClick = {
                    onNavigateToCheckout()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonBlue, // <-- CAMBIO
                    contentColor = Color.Black // <-- CAMBIO
                )
            ) {
                Text(
                    "Continuar al Resumen",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

/**
 * Función helper para unificar los colores de los
 * OutlinedTextField y que coincidan con tu tema "gamer".
 */
@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    // Color del texto y cursor
    focusedTextColor = textOnDark,
    unfocusedTextColor = textOnDark,
    cursorColor = neonBlue,

    // Colores de la etiqueta (Label)
    focusedLabelColor = neonBlue,
    unfocusedLabelColor = textOnDark.copy(alpha = 0.7f),

    // Colores del borde
    focusedBorderColor = neonBlue,
    unfocusedBorderColor = neonBlueDim.copy(alpha = 0.5f),

    // Colores de fondo (mejor transparentes para que se vea 'loginBg')
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent
)