package com.example.levelup_gamer.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.levelup_gamer.model.CarritoItem
import com.example.levelup_gamer.model.HistorialDePedidos
import com.example.levelup_gamer.model.Invitado
import com.example.levelup_gamer.model.PedidoGuardado
import com.google.gson.Gson // Importamos GSON, la librería que nos ayuda a convertir objetos a JSON
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Ojo aquí, compañeros: Preferences DataStore (el que usamos) solo puede guardar
 * datos simples como Strings, Ints o Booleans. No puede guardar una List<PedidoGuardado>.
 *
 * La 'trampa' que hicimos fue:
 * 1. Usar GSON para convertir nuestra lista de pedidos completa a UN SOLO STRING en formato JSON.
 * 2. Guardar ese único string en DataStore.
 * 3. Para leer, hacemos lo inverso: leemos el string y GSON lo vuelve a convertir
 * en nuestros objetos Kotlin.
 */


/**
 * Aquí creamos la instancia de DataStore. Usamos 'by preferencesDataStore'
 * para que se cree como un Singleton (una sola instancia para toda la app).
 * El 'name' es el nombre del archivo físico donde se guardarán los datos.
 */
private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "historial_pedidos_prefs"
)

/**
 * Esta es nuestra clase Repositorio.
 * El trabajo de esta clase es ser la ÚNICA que sabe cómo hablar con el DataStore.
 * Los ViewModels no tienen idea de si guardamos en JSON, en SQL o en la nube.
 * Solo le piden al repositorio "guarda este pedido".
 * Esto se llama Abstracción y es una buena práctica.
 */
class HistorialRepository(private val context: Context) {

    // 1. Instancia de Gson que usaremos para convertir a/desde JSON.
    private val gson = Gson()

    // 2. Aquí definimos la 'llave' (el nombre) con la que vamos a guardar
    //    nuestro string JSON en el DataStore. Es como una llave de un HashMap.
    private object Keys {
        val HISTORIAL_JSON = stringPreferencesKey("historial_pedidos_json")
    }

    // 3. Flow de lectura
    /**
     * Este 'Flow' es para LEER los datos.
     * El ViewModel puede "observar" este flujo. Si los datos en el DataStore
     * cambian, este Flow lo emitirá automáticamente.
     */
    val historialFlow: Flow<HistorialDePedidos> = context.preferencesDataStore.data
        .catch { exception ->
            // Si hay un error al leer (ej. archivo corrupto), en vez de crashear,
            // le decimos que emita una lista vacía.
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // DataStore nos da un objeto 'Preferences'. Usamos '.map' para
            // transformarlo en lo que nuestra app necesita: un 'HistorialDePedidos'.

            // Leemos el string JSON usando nuestra llave.
            val jsonString = preferences[Keys.HISTORIAL_JSON]

            if (jsonString.isNullOrEmpty()) {
                // Si no hay nada guardado (ej. la primera vez que se usa la app),
                // devolvemos una lista vacía.
                HistorialDePedidos(emptyList())
            } else {
                // Aquí GSON hace la "deserialización":
                // convierte el string JSON de vuelta a nuestras clases de Kotlin.
                gson.fromJson(jsonString, HistorialDePedidos::class.java)
            }
        }

    /**
     * Esta es la función CLAVE para GUARDAR.
     * La marcamos como 'suspend' porque debe llamarse desde una corrutina (en el ViewModel).
     * Esto evita que el guardado (que es lento) bloquee la pantalla del usuario.
     */
    suspend fun agregarPedido(invitado: Invitado, carrito: List<CarritoItem>, total: Double) {

        // Creamos el objeto 'PedidoGuardado' con los datos que nos llegaron
        // y le ponemos un ID y timestamp (la hora actual).
        val nuevoPedido = PedidoGuardado(
            id = System.currentTimeMillis().toString(),
            timestamp = System.currentTimeMillis(),
            invitado = invitado,
            items = carrito,
            total = total
        )

        // Usamos '.edit' porque es la forma segura y transaccional de escribir en DataStore.
        context.preferencesDataStore.edit { preferences ->

            // ¡IMPORTANTE! Ponemos todo en un 'try-catch'.
            // Si el JSON guardado antes está 'corrupto' (ej. cambiamos una clase),
            // 'gson.fromJson' fallará. Con esto, evitamos que la app crashee.
            try {
                // --- Este es el patrón: LEER-MODIFICAR-ESCRIBIR ---

                // 1. LEER el string JSON que ya estaba guardado.
                val jsonStringActual = preferences[Keys.HISTORIAL_JSON]

                // 2. MODIFICAR: Convertimos ese JSON a nuestros objetos Kotlin.
                val historialActual = if (jsonStringActual.isNullOrEmpty()) {
                    HistorialDePedidos(emptyList()) // O creamos uno vacío si no había nada.
                } else {
                    gson.fromJson(jsonStringActual, HistorialDePedidos::class.java)
                }

                // Creamos una lista NUEVA que tiene los pedidos antiguos + el nuevo.
                val pedidosActualizados = historialActual.pedidos + nuevoPedido
                val historialNuevo = HistorialDePedidos(pedidosActualizados)

                // Convertimos la lista COMPLETA (con el pedido nuevo) de vuelta a JSON.
                val nuevoJsonString = gson.toJson(historialNuevo)

                // --- LOG FORMATEADO ---
                // Este es nuestro log formateado para ver en la terminal
                // que los datos se guardaron correctamente.
                val logFormateado = """
                    
                    -------------------------------------------
                    NUEVO PEDIDO GUARDADO (ID: ${nuevoPedido.id})
                    -------------------------------------------
                    CLIENTE:
                       Nombre:    ${nuevoPedido.invitado.nombre}
                       Email:     ${nuevoPedido.invitado.email}
                       Teléfono:  ${nuevoPedido.invitado.telefono}
                       Dirección: ${nuevoPedido.invitado.direccion}
                    PEDIDO:
                       Total:     $ ${"%.0f".format(nuevoPedido.total)}
                       Productos: ${nuevoPedido.items.joinToString { it.producto.nombre + " x" + it.cantidad }}
                    -------------------------------------------
                """.trimIndent() // trimIndent() limpia los espacios iniciales

                Log.d("MI_APP_DEBUG", logFormateado)

                // 3. ESCRIBIR: Sobrescribimos el JSON antiguo con el JSON nuevo.
                preferences[Keys.HISTORIAL_JSON] = nuevoJsonString

            } catch (e: Exception) {
                // Si el 'try' falla (probablemente por Gson), imprimimos el error
                // en Logcat en lugar de crashear la app.
                Log.e("MI_APP_DEBUG", "¡¡CRASH DENTRO DEL REPOSITORIO!!", e)
            }
        }
    }
}