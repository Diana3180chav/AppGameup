package com.example.levelup_gamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.levelup_gamer.ui.theme.Levelup_gamerTheme
import com.example.levelup_gamer.ui.theme.screens.HomeScreen
import com.example.levelup_gamer.ui.theme.screens.register.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Levelup_gamerTheme {
                //HomeScreen()
                RegisterScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Levelup_gamerTheme {
        RegisterScreen()
        //HomeScreen() //comentario para subir nueva rama
    }
}

