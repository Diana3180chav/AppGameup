package com.example.levelup_gamer.ui.theme.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton


@Composable
fun LoginScreen(onLoginSuccess: () -> Unit = {},
                onNavigateToRegister: () -> Unit = {} ) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isValid = email.isNotBlank() && password.length >= 6

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login")

            OutlinedTextField(
                value = email,                       // lo que muestra el campo
                onValueChange = { email = it },      // actualiza el estado al teclear
                label = { Text("Correo") },          // etiqueta flotante
                singleLine = true,                   // una sola línea
                modifier = Modifier.fillMaxWidth()   // ocupa todo el ancho
            )

            OutlinedTextField(
                value = password,                       // muestra el texto actual
                onValueChange = { password = it },      // actualiza el estado
                label = { Text("Contraseña") },         // etiqueta flotante
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(), // oculta caracteres
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { if (isValid) onLoginSuccess() },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }

            TextButton(
                onClick = { onNavigateToRegister() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("¿No tienes cuenta? Regístrate")
            }



        }
    }
}