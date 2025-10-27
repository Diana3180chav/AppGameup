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
// --- IMPORTS AÑADIDOS ---
import androidx.compose.ui.text.font.FontWeight
import com.example.levelup_gamer.viewmodel.InvitadoViewModel
// ------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoExitosoScreenExpanded(
    productoViewModel: ProductoViewModel,
    invitadoViewModel: InvitadoViewModel, // <-- 1. AÑADIR ESTE PARÁMETRO
    onFinalizar: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val carrito by productoViewModel.estadoCarrito.collectAsState()
    val invitado by invitadoViewModel.datosInvitado.collectAsState() // <-- 2. OBSERVAR INVITADO
    val total = carrito.sumOf { it.producto.precio * it.cantidad }

    Scaffold(
        containerColor = loginBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = loginBg,
                    titleContentColor = neonBlue
                ),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.mipmap.logo),
                            contentDescription = "Logo App Level-Up Gamer",
                            modifier = Modifier
                                .height(50.dp)
                                .padding(end = 12.dp)
                                .clickable { onNavigateToHome() },
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            "Compra Exitosa",
                            color = neonBlue,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .padding(40.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🟦 Columna 1: Info + lista de productos
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 32.dp)
            ) {
                // --- 3. SECCIÓN DE DATOS DE ENVÍO AÑADIDA ---
                Text(
                    "Enviado a:",
                    color = neonBlue,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(8.dp))
                InfoInvitadoResumen(label = "Nombre:", valor = invitado.nombre)
                InfoInvitadoResumen(label = "Email:", valor = invitado.email)
                InfoInvitadoResumen(label = "Dirección:", valor = invitado.direccion)

                Divider(color = neonBlueDim.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 20.dp))
                // ---------------------------------------------

                Text(
                    "Resumen de productos:",
                    color = neonBlue,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(20.dp))

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(carrito) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("${item.producto.nombre} x${item.cantidad}", color = textOnDark)
                            Text(
                                "$ ${"%.0f".format(item.producto.precio * item.cantidad)}",
                                color = neonBlue
                            )
                        }
                        Divider(color = neonBlueDim.copy(alpha = 0.5f))
                    }
                }
            }

            // 🟩 Columna 2: mensaje + total + botón
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.logo),
                    contentDescription = "Logo Compra Exitosa",
                    modifier = Modifier
                        .height(220.dp)
                        .padding(bottom = 20.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    "¡Pago completado con éxito!",
                    color = neonBlue,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))
                Text(
                    "Total pagado: $ ${"%.0f".format(total)}",
                    color = neonBlue,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(40.dp))
                Button(
                    onClick = {
                        productoViewModel.vaciarCarrito()
                        onFinalizar()
                    },
                    modifier = Modifier.fillMaxWidth(0.6f),
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
}

/**
 * 4. AÑADIR ESTA FUNCIÓN HELPER AL FINAL (necesaria para la sección de datos)
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
            modifier = Modifier.width(90.dp) // Ancho fijo para alinear
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            color = textOnDark,
            fontWeight = FontWeight.Medium
        )
    }
}