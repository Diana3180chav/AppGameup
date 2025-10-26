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
import androidx.compose.ui.graphics.Color // <-- IMPORTANTE
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.viewmodel.InvitadoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.ui.theme.* // Importa tus colores (neonBlue, loginBg, etc.)

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

    Scaffold(
        containerColor = loginBg, // <-- CAMBIO: Fondo oscuro
        topBar = {
            TopAppBar(
                title = { Text("Resumen del Pedido", color = neonBlue) }, // <-- CAMBIO: Color acento
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = textOnDark // <-- CAMBIO: Color texto normal
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = loginBg // <-- CAMBIO: Integrado con fondo
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
            // --- Sección 1: Datos del Invitado ---
            Text(
                "Datos de Envío",
                style = MaterialTheme.typography.titleLarge,
                color = neonBlue // <-- CAMBIO: Color acento
            )
            Spacer(modifier = Modifier.height(8.dp))
            // InfoInvitado ahora usará los colores del helper actualizado
            InfoInvitado(label = "Nombre:", valor = invitado.nombre)
            InfoInvitado(label = "Email:", valor = invitado.email)
            InfoInvitado(label = "Teléfono:", valor = invitado.telefono)
            InfoInvitado(label = "Dirección:", valor = invitado.direccion)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = neonBlueDim.copy(alpha = 0.5f) // <-- CAMBIO: Color divisor
            )

            // --- Sección 2: Resumen de Productos ---
            Text(
                "Resumen de Productos",
                style = MaterialTheme.typography.titleLarge,
                color = neonBlue // <-- CAMBIO: Color acento
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Lista de productos
            LazyColumn(
                modifier = Modifier.weight(1f) // Ocupa el espacio disponible
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
                            color = textOnDark // <-- CAMBIO: Color texto normal
                        )
                        Text(
                            text = "$ ${"%.0f".format(item.producto.precio * item.cantidad)}",
                            color = neonBlue // <-- CAMBIO: Color acento para precio
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = neonBlueDim.copy(alpha = 0.5f) // <-- CAMBIO: Color divisor
            )

            // --- Sección 3: Total ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total:",
                    style = MaterialTheme.typography.headlineSmall,
                    color = textOnDark, // <-- CAMBIO: Color texto normal
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "$ ${"%.0f".format(total)}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = neonBlue, // <-- CAMBIO: Destacamos el total
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Botón Confirmar Pedido ---
            Button(
                onClick = {
                    // ... lógica ...
                    onNavigateToPedidoExitoso()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonBlue, // <-- CAMBIO: Botón con acento
                    contentColor = Color.Black  // <-- CAMBIO: Texto oscuro sobre botón brillante
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
            color = textOnDark.copy(alpha = 0.7f), // <-- CAMBIO: Label
            modifier = Modifier.width(90.dp) // Ancho fijo para alinear
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge,
            color = textOnDark, // <-- CAMBIO: Valor
            fontWeight = FontWeight.Medium
        )
    }
}