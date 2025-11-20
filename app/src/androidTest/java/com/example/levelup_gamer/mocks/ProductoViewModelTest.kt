package com.example.levelup_gamer.mocks

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.levelup_gamer.datastore.HistorialRepository
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

//Acá testeamos la lógica de agregar un producto al carrito
class ProductoViewModelTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun agregar_prducto() = runTest {

        val repo = mockk<HistorialRepository>(relaxed = true)

        val vm = ProductoViewModel(historialRepository = repo)

        val producto = Producto(
            idProducto = 1,
            nombre = "Mouse Gamer",
            precio = 10000.0,
        )

        vm.agregarAlCarrito(producto)

        val estado = vm.estadoCarrito.value

        TestCase.assertEquals(1, estado.size)
        TestCase.assertEquals(1, estado[0].cantidad)
        TestCase.assertEquals("Mouse Gamer", estado[0].producto.nombre)

    }

}