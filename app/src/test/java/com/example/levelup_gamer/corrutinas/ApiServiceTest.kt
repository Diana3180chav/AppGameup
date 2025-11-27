package com.example.levelup_gamer.corrutinas

import com.example.levelup_gamer.model.Comuna
import com.example.levelup_gamer.model.Region
import com.example.levelup_gamer.model.Usuario
import com.example.levelup_gamer.repository.api.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private val gson = Gson()

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @AfterEach
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getUsuarios debe retornar lista de usuarios correctamente`() = runTest {
        val usuariosFake = listOf(
            Usuario(rut = "1", userNam = "Juan", email = "juan@test.com"),
            Usuario(rut = "2", userNam = "Ana", email = "ana@test.com")
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(usuariosFake))
        )

        val resultado = apiService.getUsuarios()

        Assertions.assertEquals(2, resultado.size)
        Assertions.assertEquals("Juan", resultado[0].userNam)
        Assertions.assertEquals("Ana", resultado[1].userNam)
    }

    @Test
    fun `crearUsuario debe retornar el usuario creado`() = runTest {
        val usuario = Usuario(rut = "1", userNam = "Pedro", email = "pedro@test.com")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(201)
                .setBody(gson.toJson(usuario))
        )

        val resultado = apiService.crearUsuario(usuario)

        Assertions.assertEquals("Pedro", resultado.userNam)
        Assertions.assertEquals("1", resultado.rut)
    }

    @Test
    fun `getRegiones debe retornar lista de regiones`() = runTest {
        val regionesFake = listOf(
            Region(id = 1, nombre = "Metropolitana"),
            Region(id = 2, nombre = "Valparaíso")
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(regionesFake))
        )

        val resultado = apiService.getRegiones()

        Assertions.assertEquals(2, resultado.size)
        Assertions.assertEquals("Metropolitana", resultado[0].nombre)
    }

    @Test
    fun `getComunas debe retornar lista de comunas por región`() = runTest {
        val comunasFake = listOf(
            Comuna(id = 1, nombre = "Santiago"),
            Comuna(id = 2, nombre = "Providencia")
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(comunasFake))
        )

        val resultado = apiService.getComunas(1)

        Assertions.assertEquals(2, resultado.size)
        Assertions.assertEquals("Santiago", resultado[0].nombre)
    }
}