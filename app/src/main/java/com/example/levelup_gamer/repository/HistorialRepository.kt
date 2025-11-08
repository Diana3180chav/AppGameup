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
import com.google.gson.Gson // Librería que usamos para convertir objetos a JSON y al revés
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * Aquí utilizamos Preferences DataStore.
 * Este DataStore solo guarda datos sencillos (Strings, Ints, Booleans).
 * Entonces como nosotros necesitamos guardar una LISTA de pedidos completa,
 * lo que hacemos es convertir esa lista a un solo String en formato JSON usando Gson.
 * Luego guardamos ese String.
 * Y cuando lo necesitamos leer, hacemos lo contrario: lo convertimos de JSON a objetos Kotlin otra vez.
 */

private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "historial_pedidos_prefs" // Nombre del archivo donde se guardará todo
)

/**
 * Esta clase es nuestro "Repositorio".
 * La idea es que los ViewModels no tengan que saber cómo guardamos los datos.
 * Solo le piden al repositorio: "Guarda esto" o "Dame esto".
 * Esto hace el código más limpio y fácil de mantener.
 */
class HistorialRepository(private val context: Context) {

    // Gson para transformar objetos <-> JSON
    private val gson = Gson()

    // Llave para guardar el historial en DataStore (como si fuera el nombre en un diccionario)
    private object Keys {
        val HISTORIAL_JSON = stringPreferencesKey("historial_pedidos_json")
    }

    /**
     * Flow para leer el historial guardado.
     * El ViewModel puede observar este Flow y cuando algo cambie, automáticamente se actualiza.
     */
    val historialFlow: Flow<HistorialDePedidos> = context.preferencesDataStore.data
        .catch { exception ->
            // Si hay un error leyendo (por ejemplo, archivo corrupto),
            // no rompemos la app, devolvemos una lista vacía.
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Aquí convertimos el JSON guardado a nuestros objetos Kotlin.
            val jsonString = preferences[Keys.HISTORIAL_JSON]

            if (jsonString.isNullOrEmpty()) {
                // Si aún no hay historial, devolvemos uno vacío.
                HistorialDePedidos(emptyList())
            } else {
                gson.fromJson(jsonString, HistorialDePedidos::class.java)
            }
        }

    /**
     * Esta función guarda un nuevo pedido en el historial.
     * Es 'suspend' porque se ejecuta dentro de una corrutina para no bloquear la pantalla.
     */
    suspend fun agregarPedido(invitado: Invitado, carrito: List<CarritoItem>, total: Double) {

        // Creamos el objeto del pedido con su ID y hora actual.
        val nuevoPedido = PedidoGuardado(
            id = System.currentTimeMillis().toString(),
            timestamp = System.currentTimeMillis(),
            invitado = invitado,
            items = carrito,
            total = total
        )

        // Editamos el DataStore (lectura -> modificación -> escritura)
        context.preferencesDataStore.edit { preferences ->

            try {
                // 1. Leemos el JSON actual (si existe)
                val jsonStringActual = preferences[Keys.HISTORIAL_JSON]

                // 2. Convertimos el JSON a una lista real de pedidos
                val historialActual = if (jsonStringActual.isNullOrEmpty()) {
                    HistorialDePedidos(emptyList())
                } else {
                    gson.fromJson(jsonStringActual, HistorialDePedidos::class.java)
                }

                // 3. Agregamos el nuevo pedido a la lista
                val pedidosActualizados = historialActual.pedidos + nuevoPedido
                val historialNuevo = HistorialDePedidos(pedidosActualizados)

                // 4. Convertimos la lista nueva nuevamente a JSON
                val nuevoJsonString = gson.toJson(historialNuevo)

                // Log para ver que todo se guardó bien
                Log.d("MI_APP_DEBUG", """
                    --- NUEVO PEDIDO GUARDADO ---
                    Cliente: ${nuevoPedido.invitado.nombre}
                    Total: $${"%.0f".format(nuevoPedido.total)}
                    ---------------------------------
                """.trimIndent())

                // 5. Guardamos el JSON actualizado
                preferences[Keys.HISTORIAL_JSON] = nuevoJsonString

            } catch (e: Exception) {
                // Si algo falla convirtiendo el JSON, lo mostramos en logs y no crashea.
                Log.e("MI_APP_DEBUG", "Error guardando el pedido", e)
            }
        }
    }
}
