package com.example.levelup_gamer.ui.theme.screens

import android.app.Activity // necesario ara castear el contexto de Compose a Activity
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
import androidx.compose.ui.graphics.Color
import com.example.levelup_gamer.ui.theme.fondoPrincipal
import com.example.levelup_gamer.ui.theme.textoPrincipal
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.BottomAppBar

@OptIn( ExperimentalMaterial3Api::class)
@Composable

fun HomeScreenMedium() { //con esta función establecemos las principales características del contenedor y del diseño
    Scaffold( //es el contenedor principal que organiza las zonas típicas de una pantalla
        topBar = { // es similar al header
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically // alinea los elementos
                    ) {
                        Image(
                            painter = painterResource(id = R.mipmap.logo),
                            contentDescription = "Logo App Level UP Gamer",
                            modifier = Modifier
                                .height(40.dp) // tamaño reducido para caber en el AppBar
                                .padding(end = 8.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = "App LevelUP Gamer",
                            color = textoPrincipal,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar ={ //Esto sería como el footer
            BottomAppBar (
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text(
                    text = "footer",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
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

            /*Button(onClick = { /* acá podemos poner una acción que se haga cuando se presione el botón*/ },
                    colors = ButtonDefaults.buttonColors( //busqué información y en esta sección podemos configurar el color del botón
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )) {
                Text(text = "Texto de prueba",
                    style = MaterialTheme.typography.titleMedium)
            }

            */


        }
    }
}

//La versión tablet no está al 100% lista