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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidoExitosoScreenMedium(
    productoViewModel: ProductoViewModel,
    onFinalizar: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val carrito by productoViewModel.estadoCarrito.collectAsState()
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
                                .height(45.dp)
                                .padding(end = 10.dp)
                                .clickable { onNavigateToHome() },
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            "Compra Exitosa",
                            color = neonBlue,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .padding(32.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // 🟦 Columna izquierda: lista de productos
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(end = 24.dp)
            ) {
                Text(
                    "Detalles del pedido:",
                    color = neonBlue,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(16.dp))

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

            // 🟩 Columna derecha: total + botón
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.logo),
                    contentDescription = "Logo Compra Exitosa",
                    modifier = Modifier
                        .height(180.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    "¡Pago realizado con éxito!",
                    color = neonBlue,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    "Total pagado: $ ${"%.0f".format(total)}",
                    color = neonBlue,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        productoViewModel.vaciarCarrito()
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
}
