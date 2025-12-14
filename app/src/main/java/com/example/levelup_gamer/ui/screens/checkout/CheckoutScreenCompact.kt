package com.example.levelup_gamer.ui.screens.checkout

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.ui.theme.*
import com.example.levelup_gamer.viewmodel.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreenCompact(
    productoViewModel: ProductoViewModel,
    invitadoViewModel: InvitadoViewModel,
    ordenViewModel: OrdenViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPedidoExitoso: () -> Unit
) {
    // ---------- STATE ----------
    val carrito by productoViewModel.estadoCarrito.collectAsState()
    val invitado by invitadoViewModel.datosInvitado.collectAsState()
    val uiState by ordenViewModel.uiState.collectAsState()

    val total = carrito.sumOf { it.producto.precio * it.cantidad }
    val snackbarHostState = remember { SnackbarHostState() }

    // ---------- EFFECTOS ----------
    LaunchedEffect(uiState) {
        when (uiState) {
            is OrdenUiState.Success -> {
                ordenViewModel.resetState()
                onNavigateToPedidoExitoso()
            }

            is OrdenUiState.Error -> {
                snackbarHostState.showSnackbar(
                    (uiState as OrdenUiState.Error).message
                )
                ordenViewModel.resetState()
            }

            else -> Unit
        }
    }

    // ---------- UI ----------
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = loginBg,
        topBar = {
            TopAppBar(
                title = { Text("Resumen del Pedido", color = neonBlue) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = textOnDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = loginBg
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            // ---------- DATOS INVITADO ----------
            Text(
                "Datos de Envío",
                style = MaterialTheme.typography.titleLarge,
                color = neonBlue
            )
            Spacer(modifier = Modifier.height(8.dp))

            InfoInvitado("Nombre:", invitado.nombre)
            InfoInvitado("Email:", invitado.email)
            InfoInvitado("Teléfono:", invitado.telefono)
            InfoInvitado("Dirección:", invitado.direccion)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = neonBlueDim.copy(alpha = 0.5f)
            )

            // ---------- RESUMEN PRODUCTOS ----------
            Text(
                "Resumen de Productos",
                style = MaterialTheme.typography.titleLarge,
                color = neonBlue
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(carrito) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${item.producto.nombre} x${item.cantidad}",
                            color = textOnDark
                        )
                        Text(
                            "$ ${"%.0f".format(item.producto.precio * item.cantidad)}",
                            color = neonBlue
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = neonBlueDim.copy(alpha = 0.5f)
            )

            // ---------- TOTAL ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total:",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = textOnDark
                )
                Text(
                    "$ ${"%.0f".format(total)}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = neonBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------- BOTÓN CONFIRMAR ----------
            Button(
                onClick = {
                    ordenViewModel.confirmarOrden(
                        carrito = carrito,
                        invitado = invitado
                    )
                    Log.d("CHECKOUT", "Botón Confirmar Pedido presionado")
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is OrdenUiState.Loading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonBlue,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = if (uiState is OrdenUiState.Loading)
                        "Procesando..."
                    else
                        "Confirmar Pedido",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

/* ---------- COMPONENTE AUXILIAR ---------- */
@Composable
private fun InfoInvitado(label: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.width(90.dp),
            color = textOnDark.copy(alpha = 0.7f)
        )
        Text(
            text = valor,
            color = textOnDark,
            fontWeight = FontWeight.Medium
        )
    }
}
