package com.example.levelup_gamer.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.ui.theme.screens.home.HomeScreen
import com.example.levelup_gamer.ui.screens.login.LoginScreen
import com.example.levelup_gamer.ui.screens.register.RegisterScreen
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import androidx.compose.ui.platform.LocalContext
// 1. IMPORTAR EL VIEWMODEL DE PRODUCTO
import com.example.levelup_gamer.viewmodel.ProductoViewModel
// 2. IMPORTAR LA NUEVA PANTALLA DE CARRITO (que crearemos en el paso 3)
import com.example.levelup_gamer.ui.screens.carrito.CarritoScreen
import com.example.levelup_gamer.viewmodel.RegisterViewModel
import com.example.levelup_gamer.viewmodel.LoginViewModel
import com.example.levelup_gamer.ui.screens.pedidoExitoso.PedidoExitosoScreen

// --- NUEVOS IMPORTS ---
import com.example.levelup_gamer.viewmodel.InvitadoViewModel
import com.example.levelup_gamer.ui.screens.formularioinvitado.FormularioInvitadoScreen
import com.example.levelup_gamer.ui.screens.checkout.CheckoutScreen
import com.example.levelup_gamer.datastore.HistorialRepository
import com.example.levelup_gamer.viewmodel.ProductoViewModelFactory
// ----------------------


@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    // Contexto actual (lo necesita el repositorio)
    val context = LocalContext.current

    //  Creamos el repositorio
    val historialRepository = HistorialRepository(context)

    // Usamos la fábrica personalizada
    val productoViewModel: ProductoViewModel = viewModel(
        factory = ProductoViewModelFactory(historialRepository)
    )

    // Creamos los ViewModels aquí para compartirlos en todas las pantallas
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()

    // --- NUEVO VIEWMODEL DE INVITADO ---
    val invitadoViewModel: InvitadoViewModel = viewModel()
    // ---------------------------------


    NavHost(
        navController = navController,
        startDestination = "home"
    ){

        // ... composable "home" (sin cambios) ...
        composable("home") {
            HomeScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToLogin = { navController.navigate("iniciar session") },
                onNavigateToCarrito = { navController.navigate("carrito") },
                productoViewModel = productoViewModel
            )
        }

        // ... composable "iniciar session" (sin cambios) ...
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


        // ... composable "register" (sin cambios) ...
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
                productoViewModel = productoViewModel,
                onNavigateBack = { navController.popBackStack() },

                // --- MODIFICADO ---
                // Antes: onNavigateToPedidoExitoso = { navController.navigate("pedidoExitoso") }
                // Ahora:
                onNavigateToFormularioInvitado = { navController.navigate("formularioInvitado") }
            )
        }

        // --- NUEVA RUTA: FORMULARIO ---
        composable("formularioInvitado") {
            FormularioInvitadoScreen(
                invitadoViewModel = invitadoViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCheckout = { navController.navigate("checkout") }
            )
        }

        // --- NUEVA RUTA: CHECKOUT (RESUMEN) ---
        composable("checkout") {
            CheckoutScreen(
                productoViewModel = productoViewModel,
                invitadoViewModel = invitadoViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPedidoExitoso = { navController.navigate("pedidoExitoso") }
            )
        }

        // --- MODIFICADO: PEDIDO EXITOSO ---
        composable("pedidoExitoso") {
            PedidoExitosoScreen(
                // Le pasamos ambos ViewModels
                productoViewModel = productoViewModel,
                invitadoViewModel = invitadoViewModel,
                onFinalizar = {
                    // Limpiamos AMBOS viewmodels
                    productoViewModel.vaciarCarrito()
                    invitadoViewModel.limpiarDatos()

                    // Vuelve al home y limpia la pila
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}