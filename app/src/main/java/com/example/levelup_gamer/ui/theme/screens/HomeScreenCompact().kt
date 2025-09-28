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

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCompact() { //con esta función establecemos las principales características del contenedor y del diseño
    Scaffold (
        topBar = {
            TopAppBar({ Text("App LevelUP Gamer") })
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Text( // acá establecemos un texto estándar y llámamos características de color y style que ya están definidas
                text = "Bienvenido",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge
            )

            Button(onClick = { /* acá podemos poner una acción que se haga cuando se presione el botón*/ },
                    colors = ButtonDefaults.buttonColors( //busqué información y en esta sección podemos configurar el color del botón
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )) {
                Text(text = "Texto de prueba",
                    style = MaterialTheme.typography.titleMedium)
            }

            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Logo App Level UP Gamer",
                modifier = Modifier
                    .fillMaxSize()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}