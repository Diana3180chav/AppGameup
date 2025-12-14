package com.example.levelup_gamer.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup_gamer.repository.data.OrdenRepository

class OrdenViewModelFactory( // Su única responsabilidad es crear el viewmodel ya que Android no sabía cómo crear un ViewModel que recibe parámetros en el constructor
    private val ordenRepository: OrdenRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdenViewModel::class.java)) {
            return OrdenViewModel(
                ordenRepository = ordenRepository,
                tokenProvider = {
                    sharedPreferences.getString("jwt_token", null)
                }
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
