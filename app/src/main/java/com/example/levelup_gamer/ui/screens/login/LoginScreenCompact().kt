package com.example.levelup_gamer.ui.screens.login

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.levelup_gamer.R
import com.example.levelup_gamer.ui.theme.*
import com.example.levelup_gamer.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenCompact(
    viewModel: LoginViewModel,                 // <- VM requerido
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val estado by viewModel.estadoLogin.collectAsState()   // <- lee estado (email, password, errores)
    val context = LocalContext.current

    var showPassword by remember { mutableStateOf(false) }

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
                            modifier = Modifier
                                .height(40.dp)
                                .padding(end = 8.dp)
                                .clickable{onNavigateToHome()},  // estamos ocupando la propiedad clickable para derivar al usuario al home
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            "App Level-Up Store",
                            color = neonBlue,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(19.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Logo Empresa",
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- Correo ---
                OutlinedTextField(
                    value = estado.email,
                    onValueChange = viewModel::onEmailChange,         // <- escribe vía VM
                    label = { Text("Correo") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = estado.errores.email != null,           // <- muestra error
                    supportingText = {
                        estado.errores.email?.let { Text(it, color = errorRed) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors()
                )

                // --- Contraseña ---
                OutlinedTextField(
                    value = estado.password,
                    onValueChange = viewModel::onPasswordChange,      // <- escribe vía VM
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        TextButton(onClick = { showPassword = !showPassword }) {
                            Text(if (showPassword) "Ocultar" else "Ver", color = neonBlue)
                        }
                    },
                    isError = estado.errores.password != null,        // <- muestra error
                    supportingText = {
                        estado.errores.password?.let { Text(it, color = errorRed) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors()
                )

                // --- Botón Entrar ---
                Button(
                    onClick = {
                        if (viewModel.validarLogin()) {
                            viewModel.iniciarSesion(
                                onSuccess = onLoginSuccess,
                                onError = { msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = neonBlue,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Entrar")
                }

                // --- Ir a Registro ---
                TextButton(
                    onClick = onNavigateToRegister,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.textButtonColors(contentColor = neonBlue)
                ) {
                    Text("¿No tienes cuenta? Regístrate")
                }
            }
        }
    }
}

/** Reutiliza los mismos colores que usas en Register */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun outlinedTextFieldColors() = OutlinedTextFieldDefaults.colors(
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
