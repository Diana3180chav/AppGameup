package com.example.levelup_gamer.ui.theme.screens.pedidoExitoso

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.R
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.ui.theme.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight // <-- NUEVO IMPORT
import com.example.levelup_gamer.viewmodel.InvitadoViewModel // <-- NUEVO IMPORT


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoExitosoScreenCompact(
    productoViewModel: ProductoViewModel,
    invitadoViewModel: InvitadoViewModel, // <-- NUEVO
    onFinalizar: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val carrito by productoViewModel.estadoCarrito.collectAsState()
    val invitado by invitadoViewModel.datosInvitado.collectAsState() // <-- NUEVO
    val total = carrito.sumOf { it.producto.precio * it.cantidad }

    Scaffold(
        containerColor = loginBg,
        topBar = {
            // ... (TopAppBar sin cambios) ...
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(19.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ... (Imagen y Texto "Pago realizado" sin cambios) ...
            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Logo Compra Exitosa",
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                "¡Pago realizado con éxito!",
                color = neonBlue,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(20.dp))


            // --- NUEVA SECCIÓN: DATOS DE ENVÍO ---
            Text(
                "Enviado a:",
                color = neonBlue,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            InfoInvitadoResumen(label = "Nombre:", valor = invitado.nombre)
            InfoInvitadoResumen(label = "Email:", valor = invitado.email)
            InfoInvitadoResumen(label = "Dirección:", valor = invitado.direccion)

            Divider(color = neonBlueDim.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 16.dp))
            // ------------------------------------

            Text(
                "Resumen de Compra",
                color = neonBlue,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // ... (items(carrito) ... sin cambios)
                items(carrito) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
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
                    Divider(color = neonBlueDim.copy(alpha = 0.5f))
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total:",
                    color = textOnDark,
                    style = MaterialTheme.typography.titleMedium // Estilo consistente
                )
                Text(
                    "$ ${"%.0f".format(total)}",
                    color = neonBlue,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold // Resaltar el total
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    // La lógica de limpiar ViewModels y navegar
                    // ya la definimos en AppNavigation.
                    // Aquí solo llamamos a la función.
                    onFinalizar()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonBlue,
                    contentColor = Color.Black
                )
            ) {
                Text("Finalizar")
            }
        }
    }
}

/**
 * Composable helper (similar al de Checkout) para
 * mostrar la info del invitado en el resumen final.
 */
@Composable
private fun InfoInvitadoResumen(label: String, valor: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = textOnDark.copy(alpha = 0.7f),
            modifier = Modifier.width(80.dp) // Ancho fijo para alinear
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            color = textOnDark,
            fontWeight = FontWeight.Medium
        )
    }
}