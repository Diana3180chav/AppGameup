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
// Imports de nuestros colores de tema
import com.example.levelup_gamer.ui.theme.homeBg
import com.example.levelup_gamer.ui.theme.fondoPrincipal

// --- IMPORTACIONES PARA LA ANIMACIÓN DEL BOTÓN ---
// Import para la animación de contenido (cambia entre texto y círculo)
import androidx.compose.animation.AnimatedContent
// Imports para manejar el estado (mutableStateOf, remember)
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
// Import para crear un "scope" o alcance para lanzar corrutinas (tareas asíncronas)
import androidx.compose.runtime.rememberCoroutineScope
// Import para tareas asíncronas (delay, launch)
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
// -----------------------------------------------------------------
// 1. IMPORT NECESARIO para que 'AnimatedContent' funcione sin crashear.
// Esto le dice a Kotlin que estamos usando una API de Animación "Experimental".
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.setValue

// -----------------------------------------------------------------


// 2. AÑADIMOS LAS ANOTACIONES EXPERIMENTALES
// Le decimos al compilador que aceptamos usar APIs experimentales.
// 'ExperimentalMaterial3Api' -> Para componentes de Material 3.
// 'ExperimentalAnimationApi' -> Para 'AnimatedContent'.
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CarritoScreenCompact(
    productoViewModel: ProductoViewModel, // El ViewModel que contiene la lógica y datos del carrito.
    onNavigateBack: () -> Unit // La función (lambda) para navegar hacia atrás.
) {
    // 1. OBSERVAMOS EL ESTADO DEL CARRITO
    // 'collectAsState()' convierte el Flow del ViewModel en un "State" que Compose
    // puede "observar". Cada vez que la lista en el ViewModel cambie,
    // esta variable 'carrito' se actualizará y la UI se recompondrá.
    val carrito by productoViewModel.estadoCarrito.collectAsState()

    // 2. CALCULAMOS EL TOTAL
    // Usamos 'sumOf' para multiplicar el precio de cada item por su cantidad
    // y luego sumar todos esos subtotales.
    val total = carrito.sumOf { it.producto.precio * it.cantidad }

    // 3. ESTADO PARA LA ANIMACIÓN DEL BOTÓN
    // 'isLoading' es un estado booleano. 'remember' hace que sobreviva a las
    // recomposiciones. Cuando sea 'true', mostraremos el círculo girando.
    var isLoading by remember { mutableStateOf(false) }
    // 'rememberCoroutineScope' nos da un "contexto" para lanzar tareas
    // asíncronas (como la simulación de pago) sin bloquear la UI.
    val scope = rememberCoroutineScope()

    // 4. ESTRUCTURA PRINCIPAL DE LA PANTALLA
    // 'Scaffold' provee la estructura básica de Material (TopBar, contenido, etc.)
    Scaffold(
        containerColor = homeBg, // Color de fondo
        topBar = {
            // La barra de navegación superior
            TopAppBar(
                title = { Text("Mi Carrito", color = MaterialTheme.colorScheme.onPrimary) },
                // Ícono de navegación (para volver atrás)
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { // Llama a la función que nos pasaron
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = fondoPrincipal
                )
            )
        }
    ) { innerPadding -> // 'innerPadding' es el espacio que deja el TopAppBar

        // 5. CONTENIDO PRINCIPAL
        // Columna que ocupa todo el espacio disponible
        Column(
            modifier = Modifier
                .padding(innerPadding) // Aplica el padding del TopAppBar
                .fillMaxSize()
                .padding(16.dp) // Un padding nuestro
        ) {
            // 6. LÓGICA CONDICIONAL: ¿CARRITO VACÍO O CON ITEMS?
            if (carrito.isEmpty()) {
                // Si está vacío, mostramos un mensaje centrado
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Tu carrito está vacío",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else {
                // Si tiene items, mostramos la lista y el total

                // 7. LISTA DE PRODUCTOS
                // 'LazyColumn' es como un RecyclerView: solo renderiza los items
                // que se ven en pantalla, haciéndolo muy eficiente.
                // 'modifier = Modifier.weight(1f)' hace que ocupe todo el espacio
                // vertical disponible, "empujando" el total hacia abajo.
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    // 'items' es el constructor de la lista.
                    // LA 'key' ES CRUCIAL:
                    // Le da un ID único a cada item (el id del producto).
                    // Esto evita que la app crashee cuando eliminamos o
                    // modificamos un item, porque Compose sabe exactamente cuál es cuál.
                    items(
                        items = carrito,
                        key = { item -> item.producto.idProducto } // Solución al crash
                    ) { item ->
                        // Por cada 'item' en la lista 'carrito', creamos un Composable
                        // 'ProductoItemCarrito' (definido más abajo).
                        ProductoItemCarrito(
                            item = item,
                            // Pasamos las funciones del ViewModel como lambdas.
                            // Esto se llama "elevar el estado" (State Hoisting).
                            onAumentar = { productoViewModel.agregarAlCarrito(item.producto) },
                            onDisminuir = { productoViewModel.disminuirCantidad(item) },
                            onEliminar = { productoViewModel.eliminarProductoDelCarrito(item) }
                        )
                        // Una línea divisoria entre items
                        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f))
                    }
                }

                // 8. SECCIÓN DEL TOTAL
                Spacer(modifier = Modifier.height(16.dp)) // Un espacio en blanco
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, // "Total:" a la izq, "$..." a la der.
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Total:",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        // "%.0f" significa 0 decimales.
                        "$ ${"%.0f".format(total)}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // 9. BOTÓN DE PAGAR (CON ANIMACIÓN)
                Button(
                    onClick = {
                        // 1. Inicia la animación
                        isLoading = true

                        // 2. Lanza una tarea asíncrona (para no bloquear la UI)
                        scope.launch {

                            // Aquí es donde debes agregar la lógica de pago.
                            // Por ejemplo:
                            //
                            // 1. Llamar a una función en el ViewModel:
                            //    val resultadoPago = productoViewModel.procesarPago(carrito, usuario)
                            //
                            // 2. Validar el resultado:
                            //    if (resultadoPago.esExitoso) {
                            //        // Navegar a pantalla de éxito
                            //        onNavigateToExito()
                            //        // Vaciar el carrito
                            //        productoViewModel.vaciarCarrito()
                            //    } else {
                            //        // Mostrar un mensaje de error
                            //        mostrarError(resultadoPago.mensaje)
                            //    }
                            //

                            // --------------------------------------------------
                            // SIMULACIÓN DE PRUEBA
                            // Dejamos el círculo girando por 3 segundos para
                            // simular una llamada a una API o base de datos.
                            delay(3000L)
                            // --------------------------------------------------

                            // 3. Al terminar (sea exitoso o no), detiene la animación
                            isLoading = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading, // Deshabilita el botón mientras 'isLoading' es 'true'
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    // 10. CONTENIDO ANIMADO DEL BOTÓN
                    // 'AnimatedContent' observa el 'targetState' (isLoading).
                    // Cuando cambia, aplica una animación de fundido (fade)
                    // entre el contenido antiguo y el nuevo.
                    AnimatedContent(
                        targetState = isLoading,
                        label = "AnimacionBotonPagar"
                    ) { estaCargando ->
                        if (estaCargando) {
                            // Si 'isLoading' es true, muestra el círculo
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp), // Tamaño del texto
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 3.dp
                            )
                        } else {
                            // Si 'isLoading' es false, muestra el texto
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
 * COMPOSABLE AISLADO PARA MOSTRAR UN ÚNICO ITEM EN EL CARRITO.
 * Recibe el 'item' a mostrar y las 3 funciones de acción (eventos).
 */
@Composable
fun ProductoItemCarrito(
    item: CarritoItem,
    onAumentar: () -> Unit,
    onDisminuir: () -> Unit,
    onEliminar: () -> Unit
) {
    // Calculamos el subtotal (precio * cantidad) solo para este item
    val subtotalItem = item.producto.precio * item.cantidad

    // Fila 1: Información (Nombre, Precio Unitario, Subtotal)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Columna para Nombre y Precio Unitario
        Column(
            modifier = Modifier.weight(1f) // 'weight(1f)' hace que ocupe todo el espacio
            // que dejen los otros elementos de la Row.
        ) {
            Text(
                text = item.producto.nombre,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                // Precio unitario
                text = "$ ${"%.0f".format(item.producto.precio)} c/u",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f) // Un poco transparente
            )
        }

        // Muestra el subtotal de este item
        Text(
            // Subtotal
            text = "$ ${"%.0f".format(subtotalItem)}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }

    // Fila 2: Controles (Botones +, -, y eliminar)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Separa los controles de cantidad y el de eliminar
    ) {
        // Controles de Cantidad (+ / -)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón -
            // 'OutlinedIconButton' es un botón solo con borde
            OutlinedIconButton(onClick = onDisminuir, modifier = Modifier.size(36.dp)) {
                Icon(
                    Icons.Filled.Remove,
                    "Disminuir",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            // Cantidad
            Text(
                text = "${item.cantidad}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(40.dp) // Ancho fijo para que no "salte"
            )
            // Botón +
            OutlinedIconButton(onClick = onAumentar, modifier = Modifier.size(36.dp)) {
                Icon(
                    Icons.Filled.Add,
                    "Aumentar",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // Botón Eliminar
        IconButton(onClick = onEliminar) {
            Icon(
                Icons.Filled.Delete,
                "Eliminar producto",
                tint = MaterialTheme.colorScheme.error // Usamos el color de "error" (rojo)
            )
        }
    }
}