package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Comuna
import com.example.levelup_gamer.model.Region
import com.example.levelup_gamer.repository.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class RegionComunaViewModel : ViewModel() {
    private val _regiones = MutableStateFlow<List<Region>>(emptyList())
    val regiones: StateFlow<List<Region>> = _regiones

    private val _comunas = MutableStateFlow<List<Comuna>>(emptyList())
    val comunas: StateFlow<List<Comuna>> = _comunas

    private val api = RetrofitInstance.api

    fun cargarRegiones(){
        viewModelScope.launch{
            try {
                _regiones.value = api.getRegiones()
                println("✅ Regiones cargadas")
            } catch (e: Exception){
                println("Error al cargar las regiones ${e.localizedMessage}")

            }
        }
    }

    fun cargarComunas(regionId : Long){
        viewModelScope.launch {
            try {
                val lista = api.getComunas(regionId)
                _comunas.value = lista
                println("✅ Comunas cargadas: ${lista.size}")

            } catch (e: Exception){
                println("Error al cargar las comunas ${e.localizedMessage}")
            }
        }
    }


}