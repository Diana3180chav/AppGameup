package com.example.levelup_gamer.ui.theme.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.viewmodel.InvitadoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.ui.theme.*

// --- IMPORTS NUEVOS Y NECESARIOS ---
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import android.util.Log // Para los logs de depuración
// ------------------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreenCompact(
    productoViewModel: ProductoViewModel,
    invitadoViewModel: InvitadoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPedidoExitoso: () -> Unit
) {
    // Observamos AMBOS ViewModels
    val carrito by productoViewModel.estadoCarrito.collectAsState()
    val invitado by invitadoViewModel.datosInvitado.collectAsState()
    val total = carrito.sumOf { it.producto.precio * it.cantidad }

    // --- ¡NECESARIO PARA LA CORRUTINA DEL BOTÓN! ---
    val scope = rememberCoroutineScope()

    Scaffold(
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
            // --- (Sección 1, 2 y 3 no cambian) ---
            Text(
                "Datos de Envío",
                style = MaterialTheme.typography.titleLarge,
                color = neonBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoInvitado(label = "Nombre:", valor = invitado.nombre)
            InfoInvitado(label = "Email:", valor = invitado.email)
            InfoInvitado(label = "Teléfono:", valor = invitado.telefono)
            InfoInvitado(label = "Dirección:", valor = invitado.direccion)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = neonBlueDim.copy(alpha = 0.5f)
            )

            Text(
                "Resumen de Productos",
                style = MaterialTheme.typography.titleLarge,
                color = neonBlue
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(carrito) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.producto.nombre} x${item.cantidad}",
                            color = textOnDark
                        )
                        Text(
                            text = "$ ${"%.0f".format(item.producto.precio * item.cantidad)}",
                            color = neonBlue
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = neonBlueDim.copy(alpha = 0.5f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total:",
                    style = MaterialTheme.typography.headlineSmall,
                    color = textOnDark,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "$ ${"%.0f".format(total)}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = neonBlue,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- !! BOTÓN CORREGIDO !! ---
            Button(
                onClick = {

                    scope.launch {

                        productoViewModel.guardarPedidoCompleto(invitado, carrito, total)

                        onNavigateToPedidoExitoso()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonBlue,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    "Confirmar Pedido",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

/**
 * Composable helper para mostrar la info del invitado
 * de forma alineada (Colores actualizados).
 */
@Composable
private fun InfoInvitado(label: String, valor: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = textOnDark.copy(alpha = 0.7f),
            modifier = Modifier.width(90.dp)
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge,
            color = textOnDark,
            fontWeight = FontWeight.Medium
        )
    }
}