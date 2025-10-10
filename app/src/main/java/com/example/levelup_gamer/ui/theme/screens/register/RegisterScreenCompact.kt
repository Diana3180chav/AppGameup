package com.example.levelup_gamer.ui.theme.screens.register

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.levelup_gamer.ui.theme.*


import com.example.levelup_gamer.R
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenCompact(onNavigateToHome: () -> Unit,
                          onNavigateToLogin: () -> Unit,
                          viewModel: UsuarioViewModel) {
    val estado by viewModel.estado.collectAsState()
    val context = LocalContext.current
    val registrado by viewModel.registroExitoso.collectAsState() //collectAsState convierte el StateFlow en un State de Compose.

    Scaffold(containerColor = registerFondo, topBar = {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onNavigateToHome() },
                        modifier = Modifier.padding(start = 8.dp)
                    ){
                        Image(
                            painter = painterResource(id = R.mipmap.logo),
                            contentDescription = "Logo App Level-Up Gamer",
                            modifier = Modifier
                                .height(40.dp)
                                .padding(end = 8.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = fondoPrincipal
            )
        )
    }) { innerPadding ->

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
            //Un Spacer para empujar el formulario mas abajo.
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //campo rut
                OutlinedTextField(
                    value = estado.rut,
                    onValueChange = viewModel:: onRutChange,
                    label = { Text("Rut") },
                    isError = estado.errores.rut != null,
                    supportingText = {
                        estado.errores.rut?.let{
                           Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = registerlabel,
                        unfocusedTextColor = registerlabel,
                        cursorColor = registerlabel,
                        focusedLabelColor = registerlabel,
                        unfocusedLabelColor = registerlabel

                    )
                )
                //campo del nombre del usuario
                OutlinedTextField(
                    value = estado.userNam,
                    onValueChange = viewModel:: onNombreChange,
                    label = { Text("Nombre de usuario") },
                    isError = estado.errores.userNam != null,
                    supportingText = {
                        estado.errores.userNam?.let{
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = registerlabel,
                        unfocusedTextColor = registerlabel,
                        cursorColor = registerlabel,
                        focusedLabelColor = registerlabel,
                        unfocusedLabelColor = registerlabel

                    )
                )
                //campo del correo
                OutlinedTextField(
                    value = estado.email,
                    onValueChange = viewModel:: onEmailChange,
                    label = { Text("Correo electrónico") },
                    isError = estado.errores.email != null,
                    supportingText = {
                        estado.errores.email?.let{
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = registerlabel,
                        unfocusedTextColor = registerlabel,
                        cursorColor = registerlabel,
                        focusedLabelColor = registerlabel,
                        unfocusedLabelColor = registerlabel

                    )
                )
                //campo del correo
                OutlinedTextField(
                    value = estado.password,
                    onValueChange = viewModel:: onPasswordChange,
                    label = { Text("Contraseña") },
                    isError = estado.errores.password != null,
                    supportingText = {
                        estado.errores.password?.let{
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors( //acá le damos ciertas propiedades de color
                        focusedTextColor = registerlabel,
                        unfocusedTextColor = registerlabel,
                        cursorColor = registerlabel,
                        focusedLabelColor = registerlabel,
                        unfocusedLabelColor = registerlabel
                    )
                )
                //botón
                Button(
                    onClick = {
                        if(viewModel.validarFormulario()){ // validamos que cumpla todas las condiciones
                            Toast.makeText(context, "Registro exitoso. Rut: ${estado.rut} - Usuario: ${estado.userNam}. Serás redirigido para iniciar sesión", Toast.LENGTH_LONG).show() // acá creamos un mensaje cuando se registra
                            viewModel.limpiarCampos() //limpiamos los campos
                            viewModel.registrarUsuario() //llamamos la función que ayuda a validar el registro del usuario vía consola
                            onNavigateToLogin() //después enviamos el usuario al login para que inicie sesión
                        }
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse")
                }

                if(registrado){
                    viewModel.limpiarRegistroExitoso() //llama la función que limpia el formulario de registro
                }
            }
        }
    }
}