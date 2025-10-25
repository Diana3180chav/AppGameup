package com.example.levelup_gamer.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.State


//se creó RegisterViewModel para manejar lógica y estados de elementos que podrían necesitar ser controlados

class RegisterViewModel : ViewModel() {

    //Con esto manejamos el estado de la cámara
    private val _siCamaraActiva = MutableStateFlow(false)
    val siCamaraActiva: StateFlow<Boolean> = _siCamaraActiva.asStateFlow()

    //creamos el valor fotoUri para guardar la dirección de dónde está la dirección o dónde se encontrará guardado el archivo
    private val _fotoUri = MutableStateFlow<Uri?>(null)

    //acá tenemos la lógica la imagen seleccionada de la galería
    private val _selectedImageUri = mutableStateOf<Uri?>(null) //ocupamos estados para manejar el proceso del almacenamiento de la foto
    val selectedImageUri: State<Uri?> = _selectedImageUri //agregamos una variable para almacenar la imagen seleccionada

    private val _siGaleriaAbierta = MutableStateFlow(false)

    val galeriaAbierta: StateFlow<Boolean> = _siGaleriaAbierta.asStateFlow()




    val fotoUri: StateFlow<Uri?> = _fotoUri

    private val _fotoProcesada = mutableStateOf(false) //ocupamos estados para manejar el proceso de la foto
    val fotoProcesada: State<Boolean> = _fotoProcesada


    fun toggleCamera(){
        _siCamaraActiva.value = !_siCamaraActiva.value
    }

    fun activarCamara() { //sirve para activar la cámara
        _siCamaraActiva.value = true
    }

    fun onTomarFoto(uri: Uri){ //sirve para entregar estados a la vista y un valor a _fotoUri
        _fotoUri.value = uri
        _siCamaraActiva.value = false
        _fotoProcesada.value = false
    }

    fun marcarFotoProcesada() { //marca la foto como procesada
        _fotoProcesada.value = true
    }

    fun onLimpiarFoto(){ //limpia todo el proceso una vez realizado.
        _fotoUri.value = null
        _fotoProcesada.value = false
    }


    fun toggleGaleria(){ // con esta función aparecemos y desaparecemos el botón de la galería
        _siGaleriaAbierta.value = !_siGaleriaAbierta.value
    }

    //funciones de la galería

    fun activarGaleria(){
        _siGaleriaAbierta.value = true
    }


    fun setImageUri(uri: Uri?){ //acá se guarda la imagen seleccionada de la galería
        _selectedImageUri.value = uri
    }

    fun onlipiarGaleria(){
        _siGaleriaAbierta.value = false
        _selectedImageUri.value = null
    }

}
