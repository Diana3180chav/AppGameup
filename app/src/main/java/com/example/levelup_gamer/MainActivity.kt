package com.example.levelup_gamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.ui.theme.Levelup_gamerTheme
import com.example.levelup_gamer.ui.theme.screens.HomeScreen
import com.example.levelup_gamer.ui.theme.screens.login.LoginScreen
import com.example.levelup_gamer.ui.theme.screens.register.RegisterScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Levelup_gamerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") { //acá estamos creando el NavHost con las rutas home y register

                    composable("home") {
                        HomeScreen( //Homscreen recibe el lambda onNavigateToRegister = { navController.navigate("register") } y se lo pasa a HomeScreen compact
                            // Medium oi eXPANDEDD
                            onNavigateToRegister = { navController.navigate("register") },
                            onNavigateToLogin = { navController.navigate("iniciar session") }
                        )
                    }

                    composable("iniciar session") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("home") {
                                    popUpTo("iniciar session") { inclusive = true } // saca Login del back stack
                                    launchSingleTop = true
                                }
                            },
                            onNavigateToRegister = {
                                navController.navigate("register")
                            }
                        )
                    }

                    composable("register") {
                        RegisterScreen(
                            onNavigateToHome = { navController.navigate("home") }
                        ) //Eventualmente NavHost mostrará RegisterScreen()
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true) //Esta anotación de Jetpack Compose le dice a Android Studio que renderice
                                // este Composable en la vista previa del editor (la ventana Preview de Compose).
                                //showBackground = true significa que Android Studio dibujará un fondo detrás de la vista previa (usualmente gris) para que los elementos se vean mejor.
@Composable //Define un Composable que solo existe para ser mostrado en el panel de Preview de Android Studio.
fun GreetingPreview() {
    Levelup_gamerTheme { //Aquí se aplica el tema global (colores, tipografía, etc.) para que la preview se vea igual que en la app real.
        HomeScreen(onNavigateToRegister = {},
            onNavigateToLogin = {}) //Aquí renderizamos el HomeScreen dentro de la preview.
        //Como HomeScreen ahora necesita un parámetro onNavigateToRegister: () -> Unit, nes necesario pasarle algo
        // En la preview no se está navegando de verdad, así que le pasamos un lambda vacío ({}) para “rellenar” el parámetro.
    }
}

