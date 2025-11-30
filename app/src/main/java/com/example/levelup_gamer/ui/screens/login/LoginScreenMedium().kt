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
fun LoginScreenMedium(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToHome: () -> Unit = {}
) {
    val estado by viewModel.estadoLogin.collectAsState()
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
                                .clickable{onNavigateToHome()},
                            contentScale = ContentScale.Fit
                        )
                        Text("App Level-Up Store", color = neonBlue, style = MaterialTheme.typography.titleLarge)
                    }
                }
            )
        }
    ) { inner ->
        // Layout 2 columnas: logo / formulario
        Row(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Columna izquierda: logo grande
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.logo),
                    contentDescription = "Logo Empresa",
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit
                )
            }

            // Columna derecha: formulario
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.widthIn(max = 460.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Correo
                    OutlinedTextField(
                        value = estado.email,
                        onValueChange = viewModel::onEmailChange,
                        label = { Text("Correo") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = estado.errores.email != null,
                        supportingText = {
                            estado.errores.email?.let { Text(it, color = errorRed) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedTextFieldColors()
                    )

                    // Contraseña
                    OutlinedTextField(
                        value = estado.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = { Text("Contraseña") },
                        singleLine = true,
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            TextButton(onClick = { showPassword = !showPassword }) {
                                Text(if (showPassword) "Ocultar" else "Ver", color = neonBlue)
                            }
                        },
                        isError = estado.errores.password != null,
                        supportingText = {
                            estado.errores.password?.let { Text(it, color = errorRed) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = outlinedTextFieldColors()
                    )

                    // Botón Entrar
                    Button(
                        onClick = {
                            if (viewModel.validarLogin()) {
                                viewModel.iniciarSesion(
                                    onSuccess = { response ->
                                        onLoginSuccess() },
                                    onError = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = neonBlue,
                            contentColor = Color.Black
                        )
                    ) { Text("Entrar") }

                    // Ir a Registro
                    TextButton(
                        onClick = onNavigateToRegister,
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.textButtonColors(contentColor = neonBlue)
                    ) { Text("¿No tienes cuenta? Regístrate") }
                }
            }
        }
    }
}
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