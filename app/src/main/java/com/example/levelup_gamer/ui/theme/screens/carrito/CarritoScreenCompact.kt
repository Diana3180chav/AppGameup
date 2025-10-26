package com.example.levelup_gamer.ui.theme.screens.carrito

// Imports de layout (Column, Row, Box, etc.)
import androidx.compose.foundation.layout.*
// Import para la lista perezosa (eficiente)
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
// Imports de íconos de Material Design
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
// Imports de componentes de Material 3 (Button, Scaffold, Text, etc.)
import androidx.compose.material3.*
// Imports principales de Compose (Composable, State, etc.)
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
// Imports de alineación y modificadores
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
// Imports de nuestros propios modelos y ViewModels
import com.example.levelup_gamer.model.CarritoItem
import com.example.levelup_gamer.viewmodel.ProductoViewModel

// --- IMPORTACIONES DE TUS COLORES ---
import com.example.levelup_gamer.ui.theme.loginBg // <-- CAMBIO
import com.example.levelup_gamer.ui.theme.neonBlue // <-- CAMBIO
import com.example.levelup_gamer.ui.theme.neonBlueDim // <-- CAMBIO
import com.example.levelup_gamer.ui.theme.textOnDark // <-- CAMBIO
import androidx.compose.ui.graphics.Color // <-- CAMBIO: Necesario para el botón
import androidx.compose.foundation.BorderStroke // <-- CAMBIO: Para bordes de botones

// --- IMPORTACIONES PARA LA ANIMACIÓN DEL BOTÓN ---
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CarritoScreenCompact(
    productoViewModel: ProductoViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToFormularioInvitado: () -> Unit
) {
    val carrito by productoViewModel.estadoCarrito.collectAsState()
    val total = carrito.sumOf { it.producto.precio * it.cantidad }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = loginBg, // <-- CAMBIO: Fondo oscuro
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito", color = neonBlue) }, // <-- CAMBIO
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
                    containerColor = loginBg // <-- CAMBIO: Fondo integrado
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            if (carrito.isEmpty()) {
                // ... (lógica carrito vacío, asegúrate de que el texto use 'textOnDark')
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleLarge,
                        color = textOnDark.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(
                        items = carrito,
                        key = { item -> item.producto.idProducto }
                    ) { item ->
                        ProductoItemCarrito( // Los colores se actualizan dentro de este Composable
                            item = item,
                            onAumentar = { productoViewModel.agregarAlCarrito(item.producto) },
                            onDisminuir = { productoViewModel.disminuirCantidad(item) },
                            onEliminar = { productoViewModel.eliminarProductoDelCarrito(item) }
                        )
                        HorizontalDivider(color = neonBlueDim.copy(alpha = 0.5f)) // <-- CAMBIO
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Total:",
                        style = MaterialTheme.typography.headlineSmall,
                        color = textOnDark // <-- CAMBIO
                    )
                    Text(
                        "$ ${"%.0f".format(total)}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = neonBlue // <-- CAMBIO: Acento en el precio
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // BOTÓN DE PAGAR (CON ANIMACIÓN)
                Button(
                    onClick = {
                        isLoading = true
                        scope.launch {
                            delay(1500L)
                            isLoading = false
                            onNavigateToFormularioInvitado()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = neonBlue, // <-- CAMBIO
                        contentColor = Color.Black // <-- CAMBIO
                    )
                ) {
                    AnimatedContent(
                        targetState = isLoading,
                        label = "AnimacionBotonPagar"
                    ) { estaCargando ->
                        if (estaCargando) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.Black, // <-- CAMBIO: Para que combine con el texto
                                strokeWidth = 3.dp
                            )
                        } else {
                            Text(
                                "Ir a Pagar",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoItemCarrito(
    item: CarritoItem,
    onAumentar: () -> Unit,
    onDisminuir: () -> Unit,
    onEliminar: () -> Unit
) {
    val subtotalItem = item.producto.precio * item.cantidad

    // Fila 1: Información
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.producto.nombre,
                style = MaterialTheme.typography.titleMedium,
                color = textOnDark // <-- CAMBIO
            )
            Text(
                text = "$ ${"%.0f".format(item.producto.precio)} c/u",
                style = MaterialTheme.typography.bodyMedium,
                color = textOnDark.copy(alpha = 0.7f) // <-- CAMBIO
            )
        }
        Text(
            text = "$ ${"%.0f".format(subtotalItem)}",
            style = MaterialTheme.typography.titleMedium,
            color = neonBlue, // <-- CAMBIO: Acento en el subtotal
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }

    // Fila 2: Controles
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedIconButton(
                onClick = onDisminuir,
                modifier = Modifier.size(36.dp),
                border = BorderStroke(1.dp, neonBlueDim) // <-- CAMBIO
            ) {
                Icon(
                    Icons.Filled.Remove,
                    "Disminuir",
                    tint = textOnDark // <-- CAMBIO
                )
            }
            Text(
                text = "${item.cantidad}",
                style = MaterialTheme.typography.titleMedium,
                color = textOnDark, // <-- CAMBIO
                textAlign = TextAlign.Center,
                modifier = Modifier.width(40.dp)
            )
            OutlinedIconButton(
                onClick = onAumentar,
                modifier = Modifier.size(36.dp),
                border = BorderStroke(1.dp, neonBlueDim) // <-- CAMBIO
            ) {
                Icon(
                    Icons.Filled.Add,
                    "Aumentar",
                    tint = textOnDark // <-- CAMBIO
                )
            }
        }

        IconButton(onClick = onEliminar) {
            Icon(
                Icons.Filled.Delete,
                "Eliminar producto",
                // El color de error (rojo) suele estar bien, incluso en temas gamer
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}