package com.example.levelup_gamer.viewmodel

sealed class OrdenUiState { // ViewModel para manejar los estados de las órdenes
    object Idle : OrdenUiState()
    object Loading : OrdenUiState()
    object Success : OrdenUiState()
    data class Error(val message: String) : OrdenUiState()
}