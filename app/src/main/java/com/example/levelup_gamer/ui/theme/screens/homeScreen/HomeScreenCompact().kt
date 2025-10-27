package com.example.levelup_gamer.ui.theme.screens.home

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
import com.example.levelup_gamer.ui.theme.screens.ModalDrawer.MyModalDrawer
import com.example.levelup_gamer.viewmodel.ProductoViewModel
// import kotlinx.coroutines.CoroutineScope // <- No se usaba
// import kotlinx.coroutines.Dispatchers // <- No se usaba
// import kotlinx.coroutines.delay // <- No se usaba
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.levelup_gamer.ui.theme.neonBlueDim

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

    // Eliminamos la creación local del ViewModel:
    // val productoViewModel: ProductoViewModel = viewModel() // <- ELIMINADO

    val context = LocalContext.current

    val drawerContent: @Composable () -> Unit = {
        Column( // trabajamos el menú en columna
            modifier = Modifier
                .fillMaxSize()
                .background(loginBg)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),


            ) {
            Text("Menú", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary) //le damos unos estilos
            HorizontalDivider() //nos da una sepación entre elementos con una línea

            Button(onClick = {
                onNavigateToLogin()//Esto nos indica qu cuando se presiona el botón algo pasará...
                scope.launch { drawerState.close()}  //con esto se cierra el menú
            },
                colors = ButtonDefaults.buttonColors( //le damos algunos estilos al botón
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.onSurface

                ),
                modifier = Modifier.padding(8.dp) //indicamos cuanto espacio tendrá el botón
            ) {
                Text("Inicio sesón", style = MaterialTheme.typography.titleMedium)
            }
            HorizontalDivider()
            Button(onClick = {
                onNavigateToRegister() // cuando se haga click en el botón, se redireccionará el usuario a Register
                scope.launch{drawerState.close()}
            },
                colors = ButtonDefaults.buttonColors( //le damos algunos estilos al botón
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.onSurface

                ),
                modifier = Modifier.padding(12.dp) // indicamos cuanto espacio tendrá el botón
            ) {
                Text(text = "Registro", style = MaterialTheme.typography.titleMedium)
            }
            HorizontalDivider()
            Button(onClick = {
                // onClick actualizado:
                onNavigateToCarrito() // Redirige al carrito
                scope.launch{drawerState.close()}
            },
                colors = ButtonDefaults.buttonColors( //le damos algunos estilos al botón
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.onSurface

                ),
                modifier = Modifier.padding(12.dp) // indicamos cuanto espacio tendrá el botón
            ) {
                Icon(
                    Icons.Filled.ShoppingCart, // llamamos un icono de carrito y le damos estilos
                    contentDescription = "Carrito",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(text = "Carro", style = MaterialTheme.typography.titleMedium)
            }
            HorizontalDivider()
            IconButton( // se agrega un icono de una X para cerrar el menú
                onClick = {
                    scope.launch {
                        drawerState.close()
                    }
                }
            ) {
                Icon(
                    Icons.Filled.Close, // llamamos un icono de menú y le damos estilos
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically // alinea los elementos
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Filled.Menu, // llamamos un icono de menú y le damos estilos
                                    contentDescription = "Menu principal",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }

                            Image(
                                painter = painterResource(id = R.mipmap.logo),
                                contentDescription = "Logo App Level UP Gamer",
                                modifier = Modifier
                                    .height(40.dp) // tamaño reducido para caber en el AppBar
                                    .padding(end = 8.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Row( //Acá creamos una nueva fila para agregar el carro y alinearlo a la derecha

                            modifier = Modifier
                                .fillMaxWidth() // hace que ocupe todo el ancho disponible
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.End // coloca el contenido a la derecha
                        ) {
                            Button(
                                onClick = {
                                    // onClick actualizado:
                                    onNavigateToCarrito() // Redirige al carrito
                                },
                                colors = ButtonDefaults.buttonColors( //estilos
                                    containerColor = MaterialTheme.colorScheme.onPrimary,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Icon(
                                    Icons.Filled.ShoppingCart, // llamamos un icono de carrito y le damos estilos
                                    contentDescription = "Carrito",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Text("Carro")
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

                Column( // a cada producto en coliumna
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally //centramos el contenido

                ){
                    //producto 1

                    Image(
                        painter = painterResource(id = R.mipmap.producto1),
                        contentDescription = "Logo App Level UP Gamer",
                        modifier = Modifier
                            .height(80.dp) // tamaño reducido para caber en el AppBar
                    )
                    Text(
                        text = "Play Station 5" +
                                "\n $ 599.990",
                        style = MaterialTheme.typography.titleSmall, //estilos al texto
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Button(
                        onClick = {
                            val producto = com.example.levelup_gamer.model.Producto(1, "Play Station 5", 599990.0)
                            // Usamos el ViewModel que recibimos por parámetro
                            productoViewModel.agregarAlCarrito(producto)

                            Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors( //le damos algunos estilos al botón
                            containerColor = MaterialTheme.colorScheme.onSurface,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                            //colores en el borde
                        ),
                        modifier = Modifier.padding(8.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(24.dp))

                    ){
                        Text("Agregar al carrito")
                    }

                    //producto 2

                    Image(
                        painter = painterResource(id = R.mipmap.producto2),
                        contentDescription = "Logo App Level UP Gamer",
                        modifier = Modifier
                            .height(80.dp) // tamaño reducido para caber en el AppBar
                    )
                    Text(
                        text = "Silla gamer" +
                                "\n $ 79.990",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Button(
                        onClick = {
                            val producto = com.example.levelup_gamer.model.Producto(2, "Silla gamer", 79990.0)
                            // Usamos el ViewModel que recibimos por parámetro
                            productoViewModel.agregarAlCarrito(producto)

                            Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors( //le damos algunos estilos al botón
                            containerColor = MaterialTheme.colorScheme.onSurface,
                            contentColor = MaterialTheme.colorScheme.onPrimary

                        ),
                        modifier = Modifier.padding(8.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(24.dp))
                    ){
                        Text("Agregar al carrito")
                    }

                    //producto 3

                    Image(
                        painter = painterResource(id = R.mipmap.producto3),
                        contentDescription = "Logo App Level UP Gamer",
                        modifier = Modifier
                            .height(80.dp) // tamaño reducido para caber en el AppBar
                    )
                    Text(
                        text = "PC Gamer" +
                                "\n $ 899.990",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Button(
                        onClick = {
                            val producto = com.example.levelup_gamer.model.Producto(3, "PC Gamer", 899990.0)
                            // Usamos el ViewModel que recibimos por parámetro
                            productoViewModel.agregarAlCarrito(producto)

                            Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors( //le damos algunos estilos al botón
                            containerColor = MaterialTheme.colorScheme.onSurface,
                            contentColor = MaterialTheme.colorScheme.onPrimary

                        ),
                        modifier = Modifier.padding(8.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(24.dp))

                    ){
                        Text("Agregar al carrito")
                    }
                }
            }
        }
    }
}