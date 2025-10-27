package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup_gamer.datastore.HistorialRepository

/**
Normalmente, creamos un ViewModel así: `viewModel()`.
Pero nuestro 'ProductoViewModel' AHORA necesita un 'HistorialRepository'
en su constructor (para poder guardar en DataStore).
Esta "Fábrica" es la clase que le dice a Android:
"Oye, cuando vayas a crear un 'ProductoViewModel', yo te digo cómo.
Tienes que pasarle este 'repository' que te estoy dando".
La usamos en 'AppNavigation' al momento de crear el ViewModel.
 */
class ProductoViewModelFactory(
    // 1. Recibimos el Repositorio (nuestro conector a DataStore)
    //    AppNavigation es quien nos pasa este repositorio.
    private val repository: HistorialRepository
) : ViewModelProvider.Factory {

    /**
     Este es el método obligatorio que Android llama
     cuando le pedimos un ViewModel usando esta fábrica.
     */

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Verificamos si la clase que nos piden crear
        // (modelClass) es nuestro 'ProductoViewModel'.
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            // ¡SÍ ES! Entonces, lo creamos manualmente
            // y le "inyectamos" (pasamos) el 'repository' en el constructor.
            @Suppress("UNCHECKED_CAST") // Suprimimos un warning de casteo
            return ProductoViewModel(repository) as T
        }

        // Si nos piden crear cualquier otro ViewModel
        // (que no sea ProductoViewModel), lanzamos un error.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}