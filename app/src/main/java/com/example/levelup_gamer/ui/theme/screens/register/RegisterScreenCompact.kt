package com.example.levelup_gamer.ui.theme.screens.register

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
import com.example.levelup_gamer.ui.theme.*

import com.example.levelup_gamer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenCompact(onNavigateToHome: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { (mutableStateOf("")) }

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
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = registerlabel,
                        unfocusedTextColor = registerlabel,
                        cursorColor = registerlabel,
                        focusedLabelColor = registerlabel,
                        unfocusedLabelColor = registerlabel

                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = registerlabel,
                        unfocusedTextColor = registerlabel,
                        cursorColor = registerlabel,
                        focusedLabelColor = registerlabel,
                        unfocusedLabelColor = registerlabel

                    )
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = registerlabel,
                        unfocusedTextColor = registerlabel,
                        cursorColor = registerlabel,
                        focusedLabelColor = registerlabel,
                        unfocusedLabelColor = registerlabel
                    )
                )
                Button(
                    onClick = {/*Accion Futura*/ }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse")
                }
            }
        }
    }
}