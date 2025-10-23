package com.example.levelup_gamer.ui.theme.screens.register.Camera

import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
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
import androidx.core.content.ContextCompat
import java.io.File


@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onTomarFoto: (Uri) -> Unit,
){
    //contexto actual (para acceder a recursos y actividades)
    val  context = LocalContext.current
    //LifecycleOwner actual (para vincular la cámara al ciclo de vida)
    val lifecycleOwner = LocalLifecycleOwner.current
    //Obtiene el proveedor de la cámara (asíncrono)
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    //Instancia recordada de ImageCapture (permite tomar fotos)
    val imageCapture = remember { ImageCapture.Builder().build() }

    //Crea una vista de cámara nativa de Android dentro de Compose
    AndroidView(
        // Crea un PreviewView (vista de cámara)
        factory = { ctx ->
            PreviewView(ctx).apply { id = R.id.viewFinder } // Asigna un ID para poder encontrarlo luego
        },
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp) // Altura del visor
    )


    // Se ejecuta una vez cuando cameraProviderFuture cambia
    LaunchedEffect(cameraProviderFuture) {
        //Espera a que el proveedor esté disponible
        val cameraProvider = cameraProviderFuture.get()
        // Crea la vista previa de la cámara
        val preview = Preview.Builder().build()
        // Selecciona la cámara trasera por defecto
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        //Asocia la vista previa con la superficie del PreviewView
        preview.setSurfaceProvider(
            (context as AppCompatActivity).findViewById<PreviewView>(R.id.viewFinder).surfaceProvider
        )

        try{
            //Desvincula cualquier uso anterior de la cámara
            cameraProvider.unbindAll()
            // Vincula la cámara al ciclo de vida de la pantalla actual
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
        } catch (exc: Exception){
            //Lanza una excepción encaso de que ocurriese algún fallo
            Log.e("Camara", "El uso de case binding falló", exc)
        }

    }

    // Fila con los botones de control
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ){
        Button(onClick = {
            val fotoArchivo = File(context.cacheDir,"${System.currentTimeMillis()}.jpg")
            val output = ImageCapture.OutputFileOptions.Builder(fotoArchivo).build()
            imageCapture.takePicture(
                output,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback{
                    override fun onError(exc: ImageCaptureException){
                        Log.e("CamaraX", "Error al capturar la foto : ${exc.message}", exc)
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults){
                        val uri = Uri.fromFile(fotoArchivo)
                        onTomarFoto(uri)
                    }
                }
            )
        })
        {Text("Tomar Foto")}
    }

}
