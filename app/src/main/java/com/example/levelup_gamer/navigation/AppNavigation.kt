package com.example.levelup_gamer.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.ui.theme.screens.homeScreen.HomeScreen
import com.example.levelup_gamer.ui.theme.screens.login.LoginScreen
import com.example.levelup_gamer.ui.theme.screens.register.RegisterScreen
import com.example.levelup_gamer.viewmodel.UsuarioViewModel


@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    //aquí solo una vez creamos el viewmodel
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){

        // redirección al home
        composable("home") {
            HomeScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToLogin = { navController.navigate("iniciar session") }
            )
        }

        // 🔑redirección al login
        composable("iniciar session") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") { // env´ía al usuario a la pantalla homw
                        popUpTo("iniciar session") { inclusive = true } //Elimina de la pila todas las pantallas hasta iniciar session
                         //e inclusive = true elimina también la pantalla iniciar sesion de la pila
                        launchSingleTop = true // evita que se creen instancias duplicadas de una misma pantalla
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // redirección a registro
        composable("register") {
            RegisterScreen(
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToLogin = {navController.navigate("iniciar session")},
                viewModel = usuarioViewModel
            )
        }
    }
}