package com.example.levelup_gamer.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.ui.theme.screens.home.HomeScreen
import com.example.levelup_gamer.ui.theme.screens.login.LoginScreen
import com.example.levelup_gamer.ui.theme.screens.register.RegisterScreen
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
// 1. IMPORTAR EL VIEWMODEL DE PRODUCTO
import com.example.levelup_gamer.viewmodel.ProductoViewModel
// 2. IMPORTAR LA NUEVA PANTALLA DE CARRITO (que crearemos en el paso 3)
import com.example.levelup_gamer.ui.theme.screens.carrito.CarritoScreen
import com.example.levelup_gamer.viewmodel.RegisterViewModel
import com.example.levelup_gamer.viewmodel.LoginViewModel


@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    // Creamos los ViewModels aquí para compartirlos en todas las pantallas
    val usuarioViewModel: UsuarioViewModel = viewModel()
    // 3. CREAR EL PRODUCTOVIEWMODEL AQUÍ
    val productoViewModel: ProductoViewModel = viewModel()

    val registerViewModel: RegisterViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = "home"
    ){

        // redirección al home
        composable("home") {
            HomeScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToLogin = { navController.navigate("iniciar session") },
                // 4. AÑADIR NAVEGACIÓN AL CARRITO
                onNavigateToCarrito = { navController.navigate("carrito") },
                // 5. PASAR EL VIEWMODEL A LA HOME
                productoViewModel = productoViewModel
            )
        }

        // 🔑redirección al login
        composable("iniciar session") {
            val LoginViewModel: LoginViewModel = viewModel()   // instancia del V
            LoginScreen(
                viewModel = LoginViewModel,
                onLoginSuccess = {
                    navController.navigate("home") { // env´ía al usuario a la pantalla homw
                        popUpTo("iniciar session") { inclusive = true } //Elimina de la pila todas las pantallas hasta iniciar session
                        //e inclusive = true elimina también la pantalla iniciar sesion de la pila
                        launchSingleTop = true // evita que se creen instancias duplicadas de una misma pantalla
                    }
                },
                onNavigateToRegister = { navController.navigate("register")
                },
                onNavigateToHome = {
                    navController.navigate("home")
                }
            )
        }


        // redirección a registro
        composable("register") {
            RegisterScreen(
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToLogin = {navController.navigate("iniciar session")},
                viewModel = usuarioViewModel,
                RegisterViewModel = registerViewModel
            )
        }

        composable("carrito") {
            CarritoScreen(
                // Le pasamos el mismo ViewModel
                productoViewModel = productoViewModel,
                // Le damos una función para volver atrás
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}