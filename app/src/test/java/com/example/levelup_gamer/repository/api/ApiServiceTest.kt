package com.example.levelup_gamer.repository.api

import com.example.levelup_gamer.model.Usuario
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private val gson = Gson()

    @BeforeAll
    fun setupServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Apunta al MockWebServer
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @AfterAll
    fun shutdownServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getUsuarios devuelve lista de usuarios correctamente`() = runTest {
        // Datos simulados
        val usuariosSimulados = listOf(
            Usuario(
                rut = "12345678-9",
                userNam = "Jonathan",
                apellido = "Saravia",
                email = "jonathan@test.com",
                password = "123456",
                telefono = "987654321",
                direccion = "Mi casa"
            )
        )

        // Convertimos a JSON
        val jsonResponse = gson.toJson(usuariosSimulados)

        // Encolamos respuesta en MockWebServer
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
        )

        // Ejecutamos llamada real al API (simulada)
        val response = apiService.getUsuarios()

        // Verificamos
        assertEquals(1, response.size)
        assertEquals("Jonathan", response[0].userNam)
        assertEquals("12345678-9", response[0].rut)
    }
}
