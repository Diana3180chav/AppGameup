package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup_gamer.datastore.HistorialRepository

/**
 * Esta es la "fábrica" que construye tu ProductoViewModel.
 * Recibe el repositorio y se lo pasa al ViewModel cuando lo crea.
 */
class ProductoViewModelFactory(
    private val repository: HistorialRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}