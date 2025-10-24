package com.example.levelup_gamer.ui.theme.screens.pedidoExitoso

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@Composable
fun PedidoExitosoScreen(
    viewModel: ProductoViewModel,
    onFinalizar: () -> Unit = {}
) {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("PedidoExitosoScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact ->
            PedidoExitosoScreenCompact(
                productoViewModel = viewModel,
                onFinalizar = onFinalizar
            )

        WindowWidthSizeClass.Medium  ->
            PedidoExitosoScreenMedium(
                productoViewModel = viewModel,
                onFinalizar = onFinalizar
            )

        WindowWidthSizeClass.Expanded ->
            PedidoExitosoScreenExpanded(
                productoViewModel = viewModel,
                onFinalizar = onFinalizar
            )
    }
}
