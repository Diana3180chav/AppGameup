package com.example.levelup_gamer.ui.screens.register.Galeria

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.levelup_gamer.viewmodel.RegisterViewModel


@Composable
fun GaleriaPreview(
    registerViewModel: RegisterViewModel
){
    val  context = LocalContext.current //contexto actual (para acceder a recursos y actividades)
    val permisoGaleriaConcedido by registerViewModel.permisoGaleriaConcedido.collectAsState() //permiso de galeria


    //Tenemos un lanzador para abrir la galeria
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            registerViewModel.setImageUri(uri)
            registerViewModel.marcarPermisoGaleriaConcedido()
            Toast.makeText(context, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
        }
    }

    // acá determinamos el permiso según la versión de Android
    val permiso = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> Manifest.permission.READ_MEDIA_IMAGES
        else -> Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val permisoLauncher = rememberLauncherForActivityResult( //lanza el permiso
        contract = ActivityResultContracts.RequestPermission()
    ){ concedido ->
        if(concedido){ // si el permiso es concedido, se abre la galeria
            Toast.makeText(context, "Permiso concedido", Toast.LENGTH_SHORT).show()
            registerViewModel.marcarPermisoGaleriaConcedido()
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Se requiere permiso de galeria", Toast.LENGTH_SHORT).show()
        }

    }

    //solo muestra el botón su el permiso aún no ha sido concedido
    if(!permisoGaleriaConcedido){
        Button(onClick = { permisoLauncher.launch(permiso)}){
            Text("Permitir acceso a la galería")
        }
    }

}