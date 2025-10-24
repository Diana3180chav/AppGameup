package com.example.levelup_gamer.ui.theme.screens.register

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.levelup_gamer.R
import com.example.levelup_gamer.ui.theme.* // Importa los colores personalizados
import com.example.levelup_gamer.ui.theme.screens.register.Camera.CameraPreview
import com.example.levelup_gamer.viewmodel.RegisterViewModel
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenCompact(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: UsuarioViewModel,
    registerViewModel: RegisterViewModel
) {
    val estado by viewModel.estado.collectAsState()
    val context = LocalContext.current
    val registrado by viewModel.registroExitoso.collectAsState()
    var showPassword by remember { mutableStateOf(false) } // Estado para mostrar/ocultar contraseña
    var cargando by remember { mutableStateOf(false) } // Estado para mostrar/ocultar el cargando
    val cameraActiva by registerViewModel.siCamaraActiva.collectAsState()
    val fotoUri by registerViewModel.fotoUri.collectAsState()
    //var mostrarTexto by rememberSaveable { mutableStateOf(false) }
    //var toastMostrado by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = loginBg, // Usa el color de fondo del login (negro puro)
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = loginBg, // Usa el color de fondo del login
                    titleContentColor = neonBlue // Usa el color neón para el título
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
            Spacer(modifier = Modifier.height(24.dp))

            //Acá pedimos permio y se activa la camara una vez que se entregue.
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CAMERA),
                    100
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = { registerViewModel.activarCamara() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = neonBlue,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Registra tu foto de perfil")
                }

                //LLamamos a la camara si se preionó el botón
                if (cameraActiva) {
                    CameraPreview(
                        onTomarFoto = registerViewModel::onTomarFoto,
                        registerViewModel = registerViewModel
                    )
                }

                //Confirmamos con un texto que la foto se generó
                LaunchedEffect(fotoUri) {
                    if (fotoUri != null && !registerViewModel.fotoProcesada.value) {
                        Toast.makeText(context, "Foto capturada ", Toast.LENGTH_SHORT).show()
                        registerViewModel.marcarFotoProcesada()
                    }
                }

                //opción para poder eliminar la foto
                if (fotoUri != null) {
                    Button(
                        onClick = {
                            registerViewModel.onLimpiarFoto() // limpia foto y resetea fotoProcesada
                        },
                        colors = ButtonDefaults.buttonColors( // le pasamos colores al botón
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Eliminar foto")
                    }
                }




                // --- Campo Rut ---
                OutlinedTextField(
                    value = estado.rut,
                    onValueChange = viewModel::onRutChange,
                    label = { Text("Rut") },
                    singleLine = true,
                    isError = estado.errores.rut != null,
                    supportingText = {
                        estado.errores.rut?.let {
                            Text(
                                it,
                                color = errorRed
                            )
                        }
                    }, // Usa errorRed
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), // Usar Text o Number (solo si quieres numeros)
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors() // Usa la función de colores
                )


                // --- Campo Nombre de usuario ---
                OutlinedTextField(
                    value = estado.userNam,
                    onValueChange = viewModel::onNombreChange,
                    label = { Text("Nombre de usuario") },
                    singleLine = true,
                    isError = estado.errores.userNam != null,
                    supportingText = {
                        estado.errores.userNam?.let {
                            Text(
                                it,
                                color = errorRed
                            )
                        }
                    }, // Usa errorRed
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors() // Usa la función de colores
                )

                // --- Campo Correo electrónico ---
                OutlinedTextField(
                    value = estado.email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    isError = estado.errores.email != null,
                    supportingText = {
                        estado.errores.email?.let {
                            Text(
                                it,
                                color = errorRed
                            )
                        }
                    }, // Usa errorRed
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors() // Usa la función de colores
                )

                // --- Campo Contraseña ---
                OutlinedTextField(
                    value = estado.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = { // Icono para mostrar/ocultar contraseña
                        TextButton(onClick = { showPassword = !showPassword }) {
                            Text(if (showPassword) "Ocultar" else "Ver", color = neonBlue)
                        }
                    },
                    isError = estado.errores.password != null,
                    supportingText = {
                        estado.errores.password?.let {
                            Text(
                                it,
                                color = errorRed
                            )
                        }
                    }, // Usa errorRed
                    modifier = Modifier.fillMaxWidth(),
                    colors = outlinedTextFieldColors() // Usa la función de colores
                )

                // --- Botón de Registro ---
                Button(
                    onClick = {

                        CoroutineScope(Dispatchers.Main).launch{
                            cargando = true


                            if (viewModel.validarFormulario()) {
                                val toast = Toast.makeText(
                                    context,
                                    "Registro exitoso",
                                    Toast.LENGTH_LONG
                                )

                                toast.show()

                                delay(1000)
                                toast.cancel()
                                cargando = false
                                viewModel.limpiarCampos()
                                viewModel.registrarUsuario()
                                onNavigateToLogin()
                            } else {
                                cargando = false
                            }
                        }
                    },
                    enabled = !cargando, // Deshabilita el botón si está cargando
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors( // Estilo del botón del login
                        containerColor = neonBlue,
                        contentColor = Color.Black
                    )
                ) {
                    if(cargando){
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = Color.White // Usa el color de fondo del botón
                        )
                    } else {
                        Text("Registrarse")
                    }

                }

                if (registrado) {
                    viewModel.limpiarRegistroExitoso()
                }

                // --- Botón para ir al Login ---
                TextButton(
                    onClick = onNavigateToLogin,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.textButtonColors(contentColor = neonBlue) // Usa el color neón
                ) {
                    Text("¿Ya tienes cuenta? Inicia sesión")
                }
            }
        }
    }
}

// Función auxiliar para tener los colores de OutlinedTextField del Login en un solo lugar
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