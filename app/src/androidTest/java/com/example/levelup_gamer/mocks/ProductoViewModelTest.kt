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
// El objetivo es validar que agregar un producto al carrito funciona correctamente.
class ProductoViewModelTest {

    // Regla que permite crear un entorno mínimo de Activity para Compose.
    // Aunque aquí el test es de lógica, la regla es útil si el ViewModel
    // llega a usar states de Compose.
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun agregar_prducto() = runTest {

        // Creamos un mock del repositorio.
        //relaxed = true hace que MockK devuelva valores por defecto
        val repo = mockk<HistorialRepository>(relaxed = true)

        // Instanciamos el ViewModel usando el mock en lugar de un repositorio real.
        val vm = ProductoViewModel(historialRepository = repo)

        // Creamos un producto que posteriormente agregaremos al carrito.
        val producto = Producto(
            idProducto = 1,
            nombre = "Mouse Gamer",
            precio = 10000.0,
        )

        // Ejecutamos la lógica que queremos testear
        vm.agregarAlCarrito(producto)

        // Obtenemos el estado del carrito después de agregarlo.
        val estado = vm.estadoCarrito.value

        // Validamos (asserts) el comportamiento esperado:
        // El carrito debe tener exactamente 1 item.
        // La cantidad debe ser 1.
        // El nombre debe coincidir con el del producto original.
        TestCase.assertEquals(1, estado.size)
        TestCase.assertEquals(1, estado[0].cantidad)
        TestCase.assertEquals("Mouse Gamer", estado[0].producto.nombre)

    }

}