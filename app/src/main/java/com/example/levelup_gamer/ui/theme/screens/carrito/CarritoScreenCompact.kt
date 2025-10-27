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
import androidx.compose.runtime.collectAsState // Para "observar" el Flow del ViewModel
import androidx.compose.runtime.getValue // Para poder usar 'by'
// Imports de alineación y modificadores
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
// Imports de nuestros propios modelos y ViewModels
import com.example.levelup_gamer.model.CarritoItem
import com.example.levelup_gamer.viewmodel.ProductoViewModel

// --- IMPORTACIONES DE NUESTROS COLORES ---
import com.example.levelup_gamer.ui.theme.loginBg
import com.example.levelup_gamer.ui.theme.neonBlue
import com.example.levelup_gamer.ui.theme.neonBlueDim
import com.example.levelup_gamer.ui.theme.textOnDark
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke // Para el borde de los botones +/-

// --- IMPORTACIONES PARA LA ANIMACIÓN DEL BOTÓN ---
import androidx.compose.animation.AnimatedContent // El Composable que anima
import androidx.compose.runtime.mutableStateOf // Para crear un estado
import androidx.compose.runtime.remember // Para que el estado "recuerde" su valor
import androidx.compose.runtime.rememberCoroutineScope // Para lanzar tareas en 2do plano
import kotlinx.coroutines.delay // La función que simula la espera
import kotlinx.coroutines.launch // Para iniciar la corrutina
import androidx.compose.animation.ExperimentalAnimationApi // Requerido para AnimatedContent
import androidx.compose.runtime.setValue // Para usar 'variable = valor' en vez de 'variable.value = valor'

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CarritoScreenCompact(
    productoViewModel: ProductoViewModel, // Recibimos el ViewModel compartido
    onNavigateBack: () -> Unit, // Función para volver atrás
    onNavigateToFormularioInvitado: () -> Unit // Función para ir al formulario
) {
    // 1. OBSERVAMOS EL VIEWMODEL:
    //    'collectAsState' conecta la UI al ViewModel.
    //    Si la lista en el ViewModel cambia, 'carrito' se actualiza
    //    y Compose redibuja esta pantalla automáticamente.
    val carrito by productoViewModel.estadoCarrito.collectAsState()

    // 2. ESTADO DERIVADO:
    //    El 'total' se calcula automáticamente cada vez que el 'carrito' cambia.
    val total = carrito.sumOf { it.producto.precio * it.cantidad }

    // 3. ESTADO LOCAL DE LA UI:
    //    'isLoading' es un estado que SÓLO le importa a esta pantalla
    //    para la animación del botón. No necesita estar en el ViewModel.
    var isLoading by remember { mutableStateOf(false) }

    // 4. CORRUTINA SCOPE:
    //    Necesitamos un 'scope' para lanzar la corrutina del 'delay' del botón
    //    (una tarea en segundo plano que no bloquea la UI).
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = loginBg, // Fondo oscuro
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito", color = neonBlue) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { // Llama a la función de navegar
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = textOnDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = loginBg // Fondo integrado
                )
            )
        }
    ) { innerPadding -> // Padding que deja la TopAppBar

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 5. LÓGICA CONDICIONAL:
            //    Si la lista 'carrito' está vacía, muestra un mensaje.
            if (carrito.isEmpty()) {
                Box( // 'Box' nos ayuda a centrar el texto
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
            } else { // Si hay items, muestra la lista

                // 6. LISTA DE PRODUCTOS:
                LazyColumn(
                    // 'weight(1f)' es clave: hace que la lista ocupe todo
                    // el espacio vertical, empujando el Total y el Botón
                    // hacia abajo.
                    modifier = Modifier.weight(1f)
                ) {
                    // 'items' es cómo se itera en LazyColumn.
                    // Usamos 'key' con el ID del producto. Esto es una
                    // optimización importante: le dice a Compose cómo
                    // identificar cada item. Si borramos uno,
                    // Compose sabe cuál borrar y no redibuja todo.
                    items(
                        items = carrito,
                        key = { item -> item.producto.idProducto }
                    ) { item ->
                        // 7. DELEGACIÓN DE EVENTOS:
                        //    Llamamos al 'helper' que dibuja cada item.
                        //    Le pasamos las *funciones* del ViewModel (ej. onEliminar).
                        //    El item (UI) no sabe *cómo* eliminar, solo sabe
                        //    que debe "avisar" al ViewModel cuando lo toquen.
                        ProductoItemCarrito(
                            item = item,
                            onAumentar = { productoViewModel.agregarAlCarrito(item.producto) },
                            onDisminuir = { productoViewModel.disminuirCantidad(item) },
                            onEliminar = { productoViewModel.eliminarProductoDelCarrito(item) }
                        )
                        HorizontalDivider(color = neonBlueDim.copy(alpha = 0.5f))
                    }
                }

                // 8. SECCIÓN TOTAL (fuera de la LazyColumn):
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Total:",
                        style = MaterialTheme.typography.headlineSmall,
                        color = textOnDark
                    )
                    Text( // Mostramos el total calculado
                        "$ ${"%.0f".format(total)}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = neonBlue // Acento en el precio
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // 9. BOTÓN DE PAGAR (CON ANIMACIÓN):
                Button(
                    onClick = {
                        // Lógica de la animación:
                        // 1. Activa el estado de carga
                        isLoading = true
                        // 2. Lanza la corrutina (tarea en 2do plano)
                        scope.launch {
                            // 3. Simula una espera (1.5 seg)
                            delay(1500L)
                            // 4. Desactiva el estado de carga
                            isLoading = false
                            // 5. Navega al formulario
                            onNavigateToFormularioInvitado()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    // Deshabilita el botón mientras 'isLoading' es true
                    // para evitar que el usuario haga clic varias veces.
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = neonBlue,
                        contentColor = Color.Black
                    )
                ) {
                    // 10. ANIMATED CONTENT:
                    //     Este Composable observa 'targetState' (nuestro 'isLoading').
                    //     Cuando 'isLoading' cambia (de false a true),
                    //     anima la transición entre el 'else' (Texto) y el 'if' (Círculo).
                    AnimatedContent(
                        targetState = isLoading,
                        label = "AnimacionBotonPagar"
                    ) { estaCargando ->
                        if (estaCargando) {
                            // Muestra el círculo de progreso
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.Black, // Contraste sobre fondo neonBlue
                                strokeWidth = 3.dp
                            )
                        } else {
                            // Muestra el texto
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

/**
 * COMPOSABLE HELPER (Ayudante):
 * Creamos este Composable privado para no ensuciar 'CarritoScreenCompact'.
 * Se encarga de dibujar UNA SOLA fila del carrito.
 */
@Composable
fun ProductoItemCarrito(
    item: CarritoItem,
    onAumentar: () -> Unit,
    onDisminuir: () -> Unit,
    onEliminar: () -> Unit
) {
    // Subtotal solo de este item
    val subtotalItem = item.producto.precio * item.cantidad

    // Fila 1: Información (Nombre, Precio Unitario, Subtotal)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f) // Ocupa el espacio sobrante
        ) {
            Text(
                text = item.producto.nombre,
                style = MaterialTheme.typography.titleMedium,
                color = textOnDark
            )
            Text( // Precio unitario
                text = "$ ${"%.0f".format(item.producto.precio)} c/u",
                style = MaterialTheme.typography.bodyMedium,
                color = textOnDark.copy(alpha = 0.7f) // Tono más suave
            )
        }
        Text( // Subtotal
            text = "$ ${"%.0f".format(subtotalItem)}",
            style = MaterialTheme.typography.titleMedium,
            color = neonBlue, // Acento
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }

    // Fila 2: Controles (Botones +, -, y eliminar)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        // 'SpaceBetween' alinea los +/- a la izquierda y el 'Delete' a la derecha.
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Controles de Cantidad (+ / -)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón -
            OutlinedIconButton( // Botón solo con borde
                onClick = onDisminuir, // Llama a la función del ViewModel
                modifier = Modifier.size(36.dp),
                border = BorderStroke(1.dp, neonBlueDim) // Borde con nuestro color
            ) {
                Icon(Icons.Filled.Remove, "Disminuir", tint = textOnDark)
            }
            // Cantidad
            Text(
                text = "${item.cantidad}",
                style = MaterialTheme.typography.titleMedium,
                color = textOnDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(40.dp) // Ancho fijo para que no "salte"
            )
            // Botón +
            OutlinedIconButton(
                onClick = onAumentar, // Llama a la función del ViewModel
                modifier = Modifier.size(36.dp),
                border = BorderStroke(1.dp, neonBlueDim)
            ) {
                Icon(Icons.Filled.Add, "Aumentar", tint = textOnDark)
            }
        }

        // Botón Eliminar
        IconButton(onClick = onEliminar) { // Llama a la función del ViewModel
            Icon(
                Icons.Filled.Delete,
                "Eliminar producto",
                tint = MaterialTheme.colorScheme.error // Usamos el color de "error" (rojo)
            )
        }
    }
}