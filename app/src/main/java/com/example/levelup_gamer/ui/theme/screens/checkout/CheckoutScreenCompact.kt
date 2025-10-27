package com.example.levelup_gamer.ui.theme.screens.checkout

// Imports normales de Compose
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
// Nuestros ViewModels
import com.example.levelup_gamer.viewmodel.InvitadoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
// Nuestros colores del tema (neonBlue, loginBg, etc.)
import com.example.levelup_gamer.ui.theme.*

// --- IMPORTS IMPORTANTES PARA EL BOTÓN DE GUARDAR ---
// 'rememberCoroutineScope' nos da un "scope" (ámbito) para lanzar tareas en segundo plano
import androidx.compose.runtime.rememberCoroutineScope
// 'launch' es la función que usamos para iniciar esa tarea en segundo plano
import kotlinx.coroutines.launch
// (Quitamos el Log de depuración, pero aquí iría el 'import android.util.Log')
// ------------------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreenCompact(
    // Recibimos los ViewModels que creamos en AppNavigation
    productoViewModel: ProductoViewModel, // Para saber qué hay en el carrito
    invitadoViewModel: InvitadoViewModel, // Para saber los datos del cliente

    // Estas son las funciones "lambda" que nos pasa AppNavigation para poder navegar
    onNavigateBack: () -> Unit, // Para volver atrás
    onNavigateToPedidoExitoso: () -> Unit // Para ir a la pantalla de éxito
) {
    /**
     * Esta es la parte clave de Compose.
     * Usamos 'collectAsState()' para "suscribirnos" a los ViewModels.
     * 'carrito' e 'invitado' ahora son variables de estado.
     * Si los datos en el ViewModel cambian, Compose lo sabe y "redibuja"
     * esta pantalla automáticamente con la nueva información.
     */
    val carrito by productoViewModel.estadoCarrito.collectAsState()
    val invitado by invitadoViewModel.datosInvitado.collectAsState()

    // Calculamos el total sumando (precio * cantidad) de cada item en el carrito
    val total = carrito.sumOf { it.producto.precio * it.cantidad }

    /**
     * ¡Súper importante!
     * Creamos un 'scope' de Corrutina. Necesitamos esto porque guardar en DataStore
     * es una operación lenta (de "I/O") y no podemos hacerla en el hilo principal
     * de la UI (porque congelaría la app).
     * Este 'scope' nos permite lanzar esa tarea de guardado en segundo plano.
     */
    val scope = rememberCoroutineScope()

    // 'Scaffold' es la plantilla principal de la pantalla (con TopBar, etc.)
    Scaffold(
        containerColor = loginBg, // Nuestro fondo oscuro "gamer"
        topBar = {
            TopAppBar(
                title = { Text("Resumen del Pedido", color = neonBlue) },
                navigationIcon = {
                    // El botón de "atrás" que usa la función que nos pasaron
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = textOnDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = loginBg // Fondo de la barra integrado
                )
            )
        }
    ) { innerPadding -> // 'innerPadding' es el espacio que deja la TopBar
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // --- Sección 1: Datos del Invitado ---
            // Aquí solo leemos los datos del 'invitado' (que obtuvimos del ViewModel)
            // y los mostramos.
            Text(
                "Datos de Envío",
                style = MaterialTheme.typography.titleLarge,
                color = neonBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Usamos nuestro 'helper' para que se vea ordenado
            InfoInvitado(label = "Nombre:", valor = invitado.nombre)
            InfoInvitado(label = "Email:", valor = invitado.email)
            InfoInvitado(label = "Teléfono:", valor = invitado.telefono)
            InfoInvitado(label = "Dirección:", valor = invitado.direccion)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = neonBlueDim.copy(alpha = 0.5f)
            )

            // --- Sección 2: Resumen de Productos ---
            Text(
                "Resumen de Productos",
                style = MaterialTheme.typography.titleLarge,
                color = neonBlue
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Usamos 'LazyColumn' porque pueden ser muchos productos.
            // Es la versión eficiente de un 'Column' con scroll.
            LazyColumn(
                modifier = Modifier.weight(1f) // 'weight(1f)' hace que ocupe todo el espacio sobrante
            ) {
                // 'items(carrito)' itera sobre nuestra lista del carrito
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
                            color = neonBlue // Destacamos el precio
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                color = neonBlueDim.copy(alpha = 0.5f)
            )

            // --- Sección 3: Total ---
            // Mostramos el 'total' que calculamos arriba
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
                    color = neonBlue, // Destacamos el total final
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- !! ESTE ES EL BOTÓN MÁS IMPORTANTE !! ---
            //aquí es donde pasa la magia del guardado.
            Button(
                onClick = {
                    /**
                     * Aquí usamos el 'scope' que creamos arriba.
                     * Lanzamos (launch) una corrutina. Esto es clave:
                     * El código dentro de 'scope.launch' NO bloquea la UI.
                     */
                    scope.launch {
                        /**
                         * PASO 1: Llamamos a nuestra función 'suspend' en el ViewModel.
                         * Le pasamos los datos del invitado, el carrito y el total.
                         * Como la función es 'suspend', la corrutina va a
                         * "esperar" aquí hasta que el guardado en DataStore termine.
                         */
                        productoViewModel.guardarPedidoCompleto(invitado, carrito, total)

                        /**
                         * PASO 2: SOLO DESPUÉS de que 'guardarPedidoCompleto' haya terminado,
                         * ejecutamos esta línea y navegamos a la pantalla de éxito.
                         * Esto asegura que no naveguemos ANTES de que los datos se guarden.
                         */
                        onNavigateToPedidoExitoso()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonBlue, // Nuestro color de acento
                    contentColor = Color.Black  // Texto oscuro para que contraste
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

@Composable
private fun InfoInvitado(label: String, valor: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = textOnDark.copy(alpha = 0.7f), // Un poco más oscuro
            modifier = Modifier.width(90.dp) // Ancho fijo para que todo se alinee
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge,
            color = textOnDark,
            fontWeight = FontWeight.Medium
        )
    }
}