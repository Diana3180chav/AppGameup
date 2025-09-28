package com.example.levelup_gamer.ui.theme.utils

//importamnos
import android.app.Activity // necesario ara castear el contexto de Compose a Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi //Marca que estamos usanto una API experimental de Material 3
import androidx.compose.material3.windowsizeclass.WindowSizeClass //Clase que representa el tamaño de la ventana (Compact, Medium, Expanded)
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass // Función que recibe un Activity y devuelve la WindowSizeClass correspondiente
import androidx.compose.runtime.Composable //Permite acceder al contexto actual dentro de un Composable, lo que es necesario para obtener Activity
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class) // Esto inidca que estamos ocupando una API experimental
@Composable //Permite que la función se use dentro de otras funciones Componsables de JetpackCompose
fun obtenerWindowSizeClass(): WindowSizeClass { // fución que retorna la clasifición por tamaño de pantalla
    val activity = LocalContext.current as Activity //acá decidimos castear con LocalContext ya que teníamos problemas
                                                    //con LocalActivity. Con este código obtenemos activity desde un Composable
                                                    // luego casteamos a Activity
    return calculateWindowSizeClass(activity) //llama a calculateWindowSizeClass(activity)
}


