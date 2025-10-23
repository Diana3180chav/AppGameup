package com.example.levelup_gamer.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//se creó RegisterViewModel para manejar lógica y estados de elementos que podrían necesitar ser controlados

class RegisterViewModel : ViewModel() {

    //Con esto manejamos el estado de la cámara
    private val _siCamaraActiva = MutableStateFlow(false)
    val siCamaraActiva: StateFlow<Boolean> = _siCamaraActiva.asStateFlow()

    //creamos el valor fotoUri para guardar la dirección de dónde está la dirección o dónde se encontrará guardado el archivo
    private val _fotoUri = MutableStateFlow<Uri?>(null)
    val fotoUri: StateFlow<Uri?> = _fotoUri


    fun toggleCamera(){
        _siCamaraActiva.value = !_siCamaraActiva.value
    }

    fun onTomarFoto(uri: Uri){
        _fotoUri.value = uri
    }

    fun limpiarFoto(){
        _fotoUri.value = null
    }


}