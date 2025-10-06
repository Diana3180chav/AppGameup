package com.example.levelup_gamer.ui.theme.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenExpanded(onNavigateToHome: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Registro (Desktop)") }) }, bottomBar = {
        BottomAppBar {
            Text(
                "Pantalla Expanded", modifier = Modifier.padding(8.dp)
            )
        }
    }) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .padding(32.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") })
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") })
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") })
                Button(onClick = {/* Accion Futura */ }) {
                    Text("Registrarse")
                }
            }
            // Espacio para imagen, banner o logo
            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("En este espacio se puede agregar un logo o image")
            }
        }
    }
}