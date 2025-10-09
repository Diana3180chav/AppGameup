package com.example.levelup_gamer.ui.theme.screens.login

import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.R
import com.example.levelup_gamer.ui.theme.*
import androidx.compose.ui.graphics.Color



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenCompact(
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val isValid = email.isNotBlank() && password.length >= 6

    Scaffold(
        containerColor = loginBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = loginBg,
                    titleContentColor = neonBlue
                ),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.mipmap.logo),
                            contentDescription = "Logo App Level-Up Gamer",
                            modifier = Modifier.height(40.dp).padding(end = 8.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text("App Level-Up Store", color = neonBlue, style = MaterialTheme.typography.titleLarge)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = loginBg, contentColor = textOnDark) {
                Text("App LevelUP Gamer", Modifier.padding(8.dp))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).padding(19.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Logo Empresa",
                modifier = Modifier.height(180.dp).fillMaxWidth().padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = textOnDark,
                        unfocusedTextColor = textOnDark,
                        cursorColor = neonBlue,
                        focusedLabelColor = neonBlue,
                        unfocusedLabelColor = textOnDark.copy(alpha = 0.7f),
                        focusedBorderColor = neonBlue,
                        unfocusedBorderColor = neonBlueDim,
                        errorBorderColor = errorRed,
                        focusedContainerColor = loginBg,
                        unfocusedContainerColor = loginBg
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        TextButton(onClick = { showPassword = !showPassword }) {
                            Text(if (showPassword) "Ocultar" else "Ver", color = neonBlue)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = textOnDark,
                        unfocusedTextColor = textOnDark,
                        cursorColor = neonBlue,
                        focusedLabelColor = neonBlue,
                        unfocusedLabelColor = textOnDark.copy(alpha = 0.7f),
                        focusedBorderColor = neonBlue,
                        unfocusedBorderColor = neonBlueDim,
                        errorBorderColor = errorRed,
                        focusedContainerColor = loginBg,
                        unfocusedContainerColor = loginBg
                    )
                )

                Button(
                    onClick = { if (isValid) onLoginSuccess() },
                    enabled = isValid,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = neonBlue,
                        contentColor = Color.Black
                    )
                ) { Text("Entrar") }

                TextButton(
                    onClick = onNavigateToRegister,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.textButtonColors(contentColor = neonBlue)
                ) { Text("¿No tienes cuenta? Regístrate") }
            }
        }
    }
}
