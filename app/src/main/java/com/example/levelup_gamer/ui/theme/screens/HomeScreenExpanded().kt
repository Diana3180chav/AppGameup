package com.example.levelup_gamer.ui.theme.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import com.example.levelup_gamer.ui.theme.screens.ModalDrawer.MyModalDrawer
import com.example.levelup_gamer.ui.theme.screens.register.RegisterScreen
import kotlinx.coroutines.launch

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenExpanded(onNavigateToRegister: () -> Unit) {//con esta función establecemos las principales características del contenedor y del diseño
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) //acá creamos el estado del menú inicialmente

    val scope = rememberCoroutineScope () // acá creamos el scope para abrir y cerrar el menú

    val drawerContent: @Composable () -> Unit = {
        Column( // trabajamo el menú en columna
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Menú", style = MaterialTheme.typography.titleLarge) //le damos unos estilos
            HorizontalDivider() //nos da una sepación entre elementos con una línea

            Button(onClick = { //Esto nos indica qu cuando se presiona el botón algo pasará...
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
        }
    }

    MyModalDrawer(drawerState = drawerState, drawerContent = drawerContent){ //acá estámos pasando el estado de drawerState
        Scaffold( //es el contenedor principal que organiza las zonas típicas de una pantalla
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
                verticalArrangement = Arrangement.spacedBy(20.dp) // espacio entre cada elemento
            ){
                Text( // acá establecemos un texto estándar y llámamos características de color y style que ya están definidas
                    text = "Contenido principal",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )


            }
        }
    }
}
