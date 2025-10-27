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
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.ui.theme.screens.carrito.CarritoScreen
import com.example.levelup_gamer.viewmodel.RegisterViewModel
import com.example.levelup_gamer.viewmodel.LoginViewModel
import com.example.levelup_gamer.ui.theme.screens.pedidoExitoso.PedidoExitosoScreen
import com.example.levelup_gamer.viewmodel.InvitadoViewModel
import com.example.levelup_gamer.ui.theme.screens.formularioinvitado.FormularioInvitadoScreen
import com.example.levelup_gamer.ui.theme.screens.checkout.CheckoutScreen

// --- IMPORTS PARA LA INYECCIÓN DE DEPENDENCIAS (DataStore) ---
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.levelup_gamer.datastore.HistorialRepository
import com.example.levelup_gamer.viewmodel.ProductoViewModelFactory
// ---------------------------------------------


/**
 * Este es el Composable principal que define el "mapa" de navegación de la app.
 * Utiliza un NavHost para gestionar qué pantalla se muestra en cada momento.
 */
@Composable
fun AppNavigation(){
    /**
     * navController: Es el "cerebro" que controla la navegación.
     * Sabe cómo ir a una pantalla ("navigate"), volver atrás ("popBackStack")
     * y mantiene el historial de pantallas (la "pila de navegación").
     * 'rememberNavController' se asegura de que este controlador sobreviva
     * a las recomposiciones (actualizaciones de la UI).
     */
    val navController = rememberNavController()

    // --- INYECCIÓN DE DEPENDENCIAS PARA EL VIEWMODEL DE PRODUCTOS ---
    // Explicación: ProductoViewModel AHORA necesita un HistorialRepository
    // para funcionar (para guardar en DataStore). No podemos crearlo con
    // un simple viewModel(). Necesitamos una "Fábrica" que sepa cómo construirlo.

    // 1. Obtenemos el Contexto de la aplicación.
    // Lo necesitamos para que el Repositorio sepa dónde encontrar el archivo DataStore.
    val context = LocalContext.current.applicationContext

    // 2. Creamos nuestro Repositorio.
    // 'remember' se asegura de que solo creemos UNA instancia del repositorio
    // y la reutilicemos en toda la app (Patrón Singleton).
    val historialRepository = remember { HistorialRepository(context) }

    // 3. Creamos la "Fábrica" (Factory).
    // Le pasamos el repositorio a la fábrica, para que sepa qué
    // inyectarle al ViewModel cuando lo cree.
    val productoViewModelFactory = remember { ProductoViewModelFactory(historialRepository) }

    // 4. ¡Creamos el ProductoViewModel usando la Fábrica!
    // Esta es la clave: le decimos a Compose que use nuestra fábrica
    // personalizada para construir el ViewModel.
    val productoViewModel: ProductoViewModel = viewModel(factory = productoViewModelFactory)

    // --- FIN DE LA INYECCIÓN DE DEPENDENCIAS ---


    // Los otros ViewModels que NO necesitan dependencias (como el repositorio)
    // se pueden crear de la forma normal y compartirse.
    // Los creamos aquí, en el nivel más alto, para que sean compartidos
    // por todas las pantallas que los necesiten.
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val registerViewModel: RegisterViewModel = viewModel()
    val invitadoViewModel: InvitadoViewModel = viewModel()


    /**
     * NavHost: Es el "contenedor" donde se dibujarán nuestras pantallas.
     * Se vincula al 'navController' y define la pantalla inicial ('startDestination').
     */
    NavHost(
        navController = navController,
        startDestination = "home"
    ){

        /**
         * define una ruta de navegación.
         * Cuando llamamos a navController.navigate("home"), se mostrará
         * el contenido de este bloque.
         */
        composable("home") {
            HomeScreen(
                // Pasamos funciones lambda para manejar la navegación
                // Esto mantiene la lógica de navegación FUERA de la pantalla (buena práctica).
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToLogin = { navController.navigate("iniciar session") },
                onNavigateToCarrito = { navController.navigate("carrito") },

                // Le pasamos el ViewModel compartido.
                productoViewModel = productoViewModel
            )
        }

        composable("iniciar session") {
            // Este ViewModel (LoginViewModel) se crea aquí.
            // Esto significa que su ciclo de vida está ATADO a esta pantalla.
            // No se comparte. Se destruirá cuando salgamos de esta pantalla.
            val LoginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                viewModel = LoginViewModel,
                onLoginSuccess = {
                    // Lógica de navegación post-login
                    navController.navigate("home") {
                        // Limpiamos la pila de navegación hasta "iniciar session"
                        // 'inclusive = true' también elimina "iniciar session".
                        // Esto evita que el usuario pueda "volver atrás" a la pantalla de login.
                        popUpTo("iniciar session") { inclusive = true }
                        // Evita crear múltiples instancias de "home" si ya está en la pila.
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = { navController.navigate("home") }
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToLogin = {navController.navigate("iniciar session")},
                viewModel = usuarioViewModel, // ViewModel compartido
                RegisterViewModel = registerViewModel // ViewModel compartido
            )
        }

        // --- INICIO DEL FLUJO DE COMPRA ---

        // 1. Pantalla del Carrito
        composable("carrito") {
            CarritoScreen(
                productoViewModel = productoViewModel, // Compartido (para ver los items)
                onNavigateBack = { navController.popBackStack() }, // Función para volver atrás
                // Define el siguiente paso del flujo:
                onNavigateToFormularioInvitado = { navController.navigate("formularioInvitado") }
            )
        }

        // 2. Pantalla del Formulario
        composable("formularioInvitado") {
            FormularioInvitadoScreen(
                invitadoViewModel = invitadoViewModel, // Compartido (para guardar datos del invitado)
                onNavigateBack = { navController.popBackStack() },
                // Siguiente paso del flujo:
                onNavigateToCheckout = { navController.navigate("checkout") }
            )
        }

        // 3. Pantalla de Resumen (Checkout)
        composable("checkout") {
            CheckoutScreen(
                productoViewModel = productoViewModel, // Compartido (para mostrar productos)
                invitadoViewModel = invitadoViewModel, // Compartido (para mostrar datos de envío)
                onNavigateBack = { navController.popBackStack() },
                // Paso final del flujo:
                onNavigateToPedidoExitoso = { navController.navigate("pedidoExitoso") }
            )
        }

        // 4. Pantalla de Pedido Exitoso (FIN DEL FLUJO)
        composable("pedidoExitoso") {
            PedidoExitosoScreen(
                productoViewModel = productoViewModel, // Para mostrar el resumen final
                invitadoViewModel = invitadoViewModel, // Para mostrar el resumen final

                // onFinalizar define qué pasa cuando el usuario termina.
                onFinalizar = {
                    // 1. Limpiamos los datos de los ViewModels compartidos
                    productoViewModel.vaciarCarrito()
                    invitadoViewModel.limpiarDatos()

                    // 2. Navegamos de vuelta al Home
                    navController.navigate("home") {
                        // Limpiamos TODA la pila de navegación (el flujo de compra entero)
                        // para que el usuario no pueda "volver atrás" al checkout.
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}