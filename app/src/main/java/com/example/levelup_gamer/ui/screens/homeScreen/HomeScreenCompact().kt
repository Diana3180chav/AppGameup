package com.example.levelup_gamer.ui.theme.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import com.example.levelup_gamer.ui.theme.fondoPrincipal
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
// import androidx.lifecycle.viewmodel.compose.viewModel // <- YA NO SE USA
import com.example.levelup_gamer.ui.theme.homeBg
import com.example.levelup_gamer.ui.theme.loginBg
// import com.example.levelup_gamer.ui.theme.neonBlueDim // <- No se usaba en tu código
import com.example.levelup_gamer.ui.screens.ModalDrawer.MyModalDrawer
import com.example.levelup_gamer.viewmodel.ProductoViewModel
// import kotlinx.coroutines.CoroutineScope // <- No se usaba
// import kotlinx.coroutines.Dispatchers // <- No se usaba
// import kotlinx.coroutines.delay // <- No se usaba
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.levelup_gamer.ui.theme.neonBlueDim
import com.example.levelup_gamer.model.UserSession   // NUEVO


@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCompact(
    onNavigateToRegister: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToCarrito: () -> Unit,
    productoViewModel: ProductoViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) //acá creamos el estado del menú inicialmente
    val scope = rememberCoroutineScope () // acá creamos el scope para abrir y cerrar el menú

    val productos by productoViewModel.listaProductos.collectAsState()

    // Eliminamos la creación local del ViewModel:
    // val productoViewModel: ProductoViewModel = viewModel() // <- ELIMINADO

    val usuarioLogueado = UserSession.usuario

    val context = LocalContext.current

    val drawerContent: @Composable () -> Unit = {
        Column( // trabajamos el menú en columna
            modifier = Modifier
                .fillMaxSize()
                .background(loginBg)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            //  Si hay usuario logueado
            if (usuarioLogueado != null) {
                Text(
                    text = "Hola, ${usuarioLogueado.nombre} ${usuarioLogueado.apellido}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                HorizontalDivider()

                Button(
                    onClick = {
                        // Limpiamos sesión y vamos al login
                        UserSession.clear()
                        onNavigateToLogin()
                        scope.launch { drawerState.close() }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Cerrar sesión", style = MaterialTheme.typography.titleMedium)
                }
                HorizontalDivider()
            } else {
                //  Invitado (no hay sesión)
                Text(
                    "Menú",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                HorizontalDivider()

                Button(
                    onClick = {
                        onNavigateToLogin()
                        scope.launch { drawerState.close() }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Inicio sesión", style = MaterialTheme.typography.titleMedium)
                }
                HorizontalDivider()

                Button(
                    onClick = {
                        onNavigateToRegister()
                        scope.launch { drawerState.close() }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = "Registro", style = MaterialTheme.typography.titleMedium)
                }
                HorizontalDivider()
            }

            // Botón "Carro" (para ambos casos)
            Button(
                onClick = {
                    onNavigateToCarrito()
                    scope.launch { drawerState.close() }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(12.dp)
            ) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "Carrito",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(text = "Carro", style = MaterialTheme.typography.titleMedium)
            }
            HorizontalDivider()

            IconButton(
                onClick = {
                    scope.launch { drawerState.close() }
                }
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Cerrar menú",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }

    MyModalDrawer(drawerState = drawerState, drawerContent = drawerContent){ //acá estámos pasando el estado de drawerState
        Scaffold( //es el contenedor principal que organiza las zonas típicas de una pantalla
            containerColor = fondoPrincipal,
            topBar = { // es similar al header
                TopAppBar(
                    title = {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Primera fila: Menú + Logo
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                ) {
                                    Icon(
                                        Icons.Filled.Menu,
                                        contentDescription = "Menu principal",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }

                                Image(
                                    painter = painterResource(id = R.mipmap.logo),
                                    contentDescription = "Logo App Level UP Gamer",
                                    modifier = Modifier
                                        .height(40.dp)
                                        .padding(end = 8.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }

                            // Segunda fila: Carro alineado a la derecha
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = { onNavigateToCarrito() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.onPrimary,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    )
                                ) {
                                    Icon(
                                        Icons.Filled.ShoppingCart,
                                        contentDescription = "Carrito",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text("Carro")
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = fondoPrincipal
                    )
                )
            },
        ){ innerPadding ->
            Column ( //Es el contenido central...es como el main
                modifier = Modifier
                    .padding(innerPadding) //deja espacio debajo del AppBar
                    .fillMaxSize()
                    .padding(16.dp),// margen alrededor del contenido
                verticalArrangement = Arrangement.spacedBy(20.dp), // espacio entre cada elemento
                horizontalAlignment = Alignment.CenterHorizontally //centramos la imagen
            ){
                Image( //imagen del posible carousel
                    painter = painterResource(id = R.mipmap.carousel),
                    contentDescription = "Logo App Level UP Gamer",
                )
                //Listado de productos

                LaunchedEffect(Unit) {
                    productoViewModel.cargarProductos()
                }


                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(productos) { producto ->

                        //Log.d("ProductoImagen", "URL: ${producto.imagenUrl}")
                        //Log.d("ProductoImagen", "URL: ${producto.precio}")

                        val precioFormateado = "%,d".format(producto.precio.toInt()) // acá formateamos visualmente el precio

                        AsyncImage(
                            model = producto.imagenUrl,
                            contentDescription = producto.nombre,
                            modifier = Modifier.height(120.dp)
                        )

                        Text(

                            text = "$$precioFormateado",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Button(
                            onClick = {
                                val productoLocal = com.example.levelup_gamer.model.Producto(
                                    idProducto = producto.id.toInt(),
                                    nombre = producto.nombre,
                                    precio = producto.precio
                                )
                                productoViewModel.agregarAlCarrito(productoLocal)
                                Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .border(2.dp, Color.White, RoundedCornerShape(24.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onSurface,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Agregar al carrito")
                        }
                    }
                }
            }
        }
    }
}