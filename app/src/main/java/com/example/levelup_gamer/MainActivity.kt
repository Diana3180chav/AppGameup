package com.example.levelup_gamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.levelup_gamer.navigation.AppNavigation
import com.example.levelup_gamer.ui.theme.Levelup_gamerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Levelup_gamerTheme {
                AppNavigation() // Minimizamos el código de Main Activity centrando solo la navegación
            }
        }
    }
}
