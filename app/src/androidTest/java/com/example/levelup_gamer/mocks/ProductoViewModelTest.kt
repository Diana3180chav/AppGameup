package com.example.levelup_gamer.mocks
import com.example.levelup_gamer.datastore.HistorialRepository
import com.example.levelup_gamer.repository.data.ProductoRepository
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductoViewModelTest {

    // Dispatcher de test para corrutinas
    private val testDispatcher = StandardTestDispatcher()

    // Mocks (FAKE)
    private lateinit var historialRepository: HistorialRepository
    private lateinit var productoRepository: ProductoRepository

    // ViewModel bajo prueba
    private lateinit var viewModel: ProductoViewModel

    @Before
    fun setup() {
        // Reemplazamos Main dispatcher
        Dispatchers.setMain(testDispatcher)

        historialRepository = mockk(relaxed = true)
        productoRepository = mockk(relaxed = true)

        viewModel = ProductoViewModel(
            historialRepository = historialRepository,
            productoRepository = productoRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun agregar_producto() = runTest {
        // GIVEN: un producto
        val producto = Producto(
            idProducto = 1,
            nombre = "Mouse Gamer",
            precio = 10000.0
        )

        // WHEN: agregamos el producto al carrito
        viewModel.agregarAlCarrito(producto)

        // THEN: verificamos el estado
        val estado = viewModel.estadoCarrito.value

        assertEquals(1, estado.size)
        assertEquals(1, estado[0].cantidad)
        assertEquals("Mouse Gamer", estado[0].producto.nombre)
        assertEquals(10000.0, estado[0].producto.precio)
    }

    @Test
    fun agregar_mismo_producto() = runTest {
        val producto = Producto(
            idProducto = 1,
            nombre = "Mouse Gamer",
            precio = 10000.0
        )

        viewModel.agregarAlCarrito(producto)
        viewModel.agregarAlCarrito(producto)

        val estado = viewModel.estadoCarrito.value

        assertEquals(1, estado.size)
        assertEquals(2, estado[0].cantidad)
    }
}