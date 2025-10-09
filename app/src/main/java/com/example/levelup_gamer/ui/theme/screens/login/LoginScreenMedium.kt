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
fun LoginScreenMedium(
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
        Row(
            modifier = Modifier.padding(innerPadding).fillMaxSize().padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Branding
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.logo),
                    contentDescription = "Logo Empresa",
                    modifier = Modifier.height(220.dp).fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.height(8.dp))
                Text("Bienvenida a Level-Up", color = neonBlue, style = MaterialTheme.typography.headlineSmall)
            }

            // Form
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight().padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = loginBg), // card negro
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Iniciar sesión", color = neonBlue, style = MaterialTheme.typography.titleLarge)

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

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TextButton(
                                onClick = onNavigateToRegister,
                                colors = ButtonDefaults.textButtonColors(contentColor = neonBlue)
                            ) { Text("¿No tienes cuenta? Regístrate") }
                        }
                    }
                }
            }
        }
    }
}
