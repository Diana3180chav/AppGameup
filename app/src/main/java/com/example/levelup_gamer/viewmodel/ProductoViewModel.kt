package com.example.levelup_gamer.viewmodel

// Importamos el Repositorio (nuestro "conector" con DataStore)
import com.example.levelup_gamer.datastore.HistorialRepository
import com.example.levelup_gamer.model.Invitado
import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.model.CarritoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
// (Este import de Log ya no es necesario si quitamos los logs de depuración)
// import android.util.Log

/**
 * Este ViewModel maneja todo lo relacionado con el carrito de compras.
 *
 * 1. Mantiene la lista de productos (el estado del carrito).
 * 2. Recibe el 'HistorialRepository' (desde AppNavigation) para poder guardar el pedido.
 */
class ProductoViewModel(
    // 1. INYECCIÓN DE DEPENDENCIAS:
    //    Recibimos el Repositorio en el constructor.
    //    No creamos el repositorio aquí, solo lo usamos.
    //    Esto nos lo "inyecta" AppNavigation (usando la ViewModelFactory).
    private val historialRepository: HistorialRepository
) : ViewModel() {

    // 2. ESTADO PRIVADO (Mutable):
    //    '_estadoCarrito' es la lista "real". Es 'Mutable' (se puede cambiar)
    //    y 'privada' (solo el ViewModel la puede modificar).
    private val _estadoCarrito = MutableStateFlow<List<CarritoItem>>(emptyList())

    // 3. ESTADO PÚBLICO (Solo Lectura):
    //    'estadoCarrito' es la versión pública que la UI "observa".
    //    La UI (CarritoScreen, CheckoutScreen) usa 'collectAsState()'
    //    sobre esta variable para dibujarse.
    val estadoCarrito: StateFlow<List<CarritoItem>> = _estadoCarrito


    // 4. FUNCIONES DE CARRITO (Manejo de Estado):
    //    Estas son las funciones que la UI (CarritoScreen) llama.
    //    Usamos '.update' y '.copy()' para actualizar el estado
    //    de forma "inmutable" (creamos una lista nueva, no modificamos la vieja).
    //    Esto es clave para que StateFlow detecte el cambio y avise a la UI.
    fun agregarAlCarrito(producto: Producto) {
        _estadoCarrito.update { listaActual ->
            val itemExistente = listaActual.find {
                it.producto.idProducto == producto.idProducto
            }

            if (itemExistente == null) {
                // Agrega un item nuevo a la lista
                listaActual + CarritoItem(producto = producto, cantidad = 1)
            } else {
                // Crea una lista nueva, actualizando solo el item que cambió
                listaActual.map { item ->
                    if (item.producto.idProducto == producto.idProducto) {
                        item.copy(cantidad = item.cantidad + 1) // Inmutabilidad
                    } else {
                        item
                    }
                }
            }
        }
    }

    // Lógica para disminuir la cantidad
    fun disminuirCantidad(item: CarritoItem) {
        _estadoCarrito.update { listaActual ->
            if (item.cantidad > 1) {
                // Si la cantidad es > 1, solo resta
                listaActual.map {
                    if (it.producto.idProducto == item.producto.idProducto) {
                        it.copy(cantidad = it.cantidad - 1)
                    } else {
                        it
                    }
                }
            } else {
                // Si la cantidad es 1, lo elimina de la lista
                listaActual.filterNot {
                    it.producto.idProducto == item.producto.idProducto
                }
            }
        }
    }

    // Elimina un producto de la lista (sin importar la cantidad)
    fun eliminarProductoDelCarrito(item: CarritoItem) {
        _estadoCarrito.update { listaActual ->
            listaActual.filterNot {
                it.producto.idProducto == item.producto.idProducto
            }
        }
    }

    // Resetea el carrito (lo llamamos desde AppNavigation al finalizar)
    fun vaciarCarrito() {
        _estadoCarrito.value = emptyList()
    }


    // --- FUNCIÓN DE GUARDADO (LA MÁS IMPORTANTE) ---
    /**
     * 5. FUNCIÓN 'SUSPEND' PARA GUARDAR:
     * Esta es la función que llama 'CheckoutScreen' (la pantalla de resumen).
     *
     * Es 'suspend' (una función de suspensión). Esto es clave.
     * Significa que SÓLO puede ser llamada desde una corrutina (el 'scope.launch'
     * en la UI).
     *
     * La UI "esperará" a que esta función termine ANTES de navegar.
     *
     * Fíjense que el ViewModel no sabe *cómo* se guarda (si es JSON, SQL, etc.).
     * Solo le "delega" el trabajo al repositorio.
     */
    suspend fun guardarPedidoCompleto(invitado: Invitado, carrito: List<CarritoItem>, total: Double) {
        // (Quitamos los logs de depuración)
        // Solo llamamos al repositorio y esperamos a que termine.
        historialRepository.agregarPedido(invitado, carrito, total)
    }
}