package com.example.levelup_gamer.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.viewmodel.UsuarioViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenMedium(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: UsuarioViewModel
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro (Tablet)") }) },
        bottomBar = { BottomAppBar { Text("Pantalla Medium", modifier = Modifier.padding(8.dp)) } }
    ) { innerPanding ->
        Row(
            modifier = Modifier
                .padding(innerPanding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Columna con formulario
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {/*Futura Accion*/ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse")
                }
            }
            // Espacio para imagen, banner o logo
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("En este espacio se puede agregar un logo o image")
            }
        }
    }
}