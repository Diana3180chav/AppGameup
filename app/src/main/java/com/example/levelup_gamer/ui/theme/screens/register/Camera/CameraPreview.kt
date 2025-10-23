package com.example.levelup_gamer.ui.theme.screens.register.Camera

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import com.example.levelup_gamer.R
import androidx.camera.view.PreviewView // Vista que renderiza la imagen de la cámara
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.camera.core.Preview



@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onTomarFoto: () -> Unit,
    onIniciarPararVideo: () -> Unit
){
    val  context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply { id = R.id.viewFinder }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    )

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ){
        Button(onClick = onTomarFoto) {Text("Tomar foto")}
        Button(onClick = onIniciarPararVideo) {Text("Iniciar/Parar video")}
    }

    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        preview.setSurfaceProvider(
            (context as AppCompatActivity).findViewById<PreviewView>(R.id.viewFinder).surfaceProvider
        )

        try{
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
        } catch (exc: Exception){
            Log.e("Camara", "El uso de case binding falló", exc)
        }

    }

}
