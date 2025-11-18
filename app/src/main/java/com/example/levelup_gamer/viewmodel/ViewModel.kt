package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Usuario
import com.example.levelup_gamer.repository.data.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ViewModel : ViewModel(){

    private val repository = repository()
    //flujo mutable que contiene la lista de los usuarios (por el momento)

    private val _getList = MutableStateFlow<List<Usuario>>(emptyList())
    //Flujo público de solo lectura

    val getList: StateFlow<List<Usuario>> = _getList
    //Se llama automáticamente cuando se inicia

    init {
        fetchGet()
    }

    //función que obtiene los datos en segundo plano
    private fun fetchGet(){
        viewModelScope.launch {
            try {
                _getList.value = repository.getUsuarios()
            } catch (e: Exception){
                println("Error al obtener datos ${e.localizedMessage}")
            }
        }
    }


    /*fun fetchPost(usuario: Usuario){
        viewModelScope.launch {
            try {
                val nuevoUsuario = repository.crearUsuario(usuario)

                _getList.value = _getList.value + nuevoUsuario
            } catch (e: Exception){
                println("Error al cargar los datos ${e.localizedMessage}")
            }
        }
    }*/
}