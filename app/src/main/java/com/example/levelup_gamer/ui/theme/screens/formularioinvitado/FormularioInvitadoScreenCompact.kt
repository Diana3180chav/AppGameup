package com.example.levelup_gamer.ui.theme.screens.formularioinvitado

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Import para el estado del scroll
import androidx.compose.foundation.verticalScroll // Modificador para hacer la columna "scrolleable"
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // Para observar el ViewModel
import androidx.compose.runtime.getValue // Para usar la sintaxis 'by'
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.viewmodel.InvitadoViewModel // Nuestro ViewModel
import com.example.levelup_gamer.ui.theme.* // Nuestros colores

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioInvitadoScreenCompact(
    // 1. RECIBIMOS EL VIEWMODEL:
    //    Lo recibimos desde AppNavigation. Este ViewModel es el "dueño" de los datos.
    invitadoViewModel: InvitadoViewModel,

    // 2. FUNCIONES DE NAVEGACIÓN:
    //    Funciones lambda que nos pasa AppNavigation.
    onNavigateBack: () -> Unit,
    onNavigateToCheckout: () -> Unit // Para ir al resumen
) {
    // 3. OBSERVAMOS EL ESTADO:
    //    Aquí está la conexión. 'collectAsState' "escucha" los datos del ViewModel.
    //    La variable 'invitado' ahora contiene el objeto Invitado (con nombre, email, etc.).
    //    Si los datos en el ViewModel cambian, Compose redibuja esta pantalla automáticamente.
    val invitado by invitadoViewModel.datosInvitado.collectAsState()

    // 4. ESTADO DE SCROLL:
    //    Creamos un estado de scroll. Lo necesitamos para que, si el teclado
    //    aparece, el usuario pueda deslizar la columna y ver los campos de abajo.
    val scrollState = rememberScrollState()

    // 5. SCAFFOLD:
    //    La plantilla de la pantalla (fondo oscuro, barra superior).
    Scaffold(
        containerColor = loginBg, // Nuestro fondo
        topBar = {
            TopAppBar(
                title = { Text("Datos de Invitado", color = neonBlue) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { // Botón de volver
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = textOnDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = loginBg // Fondo integrado
                )
            )
        }
    ) { innerPadding -> // Padding que deja la TopAppBar
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                // 6. ASIGNAMOS EL SCROLL:
                //    Vinculamos el estado de scroll a la columna.
                .verticalScroll(scrollState),
            // Esto pone un espacio automático de 12.dp entre cada campo de texto.
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Completa tus datos para continuar con el envío",
                style = MaterialTheme.typography.titleMedium,
                color = textOnDark
            )

            // 7. CAMPO DE TEXTO (Nombre):
            //    Este es el patrón de "Estado Controlado" (o Flujo de Datos Unidireccional).
            OutlinedTextField(
                // a) EL VALOR VIENE DEL VIEWMODEL:
                //    El campo de texto *muestra* lo que hay en 'invitado.nombre'.
                value = invitado.nombre,

                // b) EL CAMBIO SE ENVÍA AL VIEWMODEL:
                //    Cuando el usuario escribe, NO cambiamos el valor aquí.
                //    En vez de eso, le "avisamos" al ViewModel que el valor cambió
                //    (ej. 'onNombreChange') y le pasamos el nuevo texto ('it').
                onValueChange = { invitadoViewModel.onNombreChange(it) },

                //    El ViewModel actualizará su estado, y eso (por el paso 3)
                //    hará que esta pantalla se redibuje con el nuevo valor.

                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho
                singleLine = true, // Evita el "Enter"

                // 8. COLORES PERSONALIZADOS:
                //    Usamos nuestra función 'helper' de abajo para el estilo "gamer".
                colors = textFieldColors()
            )

            // 9. REPETIMOS EL PATRÓN:
            //    Hacemos exactamente lo mismo para los otros campos.

            // Campo de Email
            OutlinedTextField(
                value = invitado.email,
                onValueChange = { invitadoViewModel.onEmailChange(it) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = textFieldColors()
            )

            // Campo de Teléfono
            OutlinedTextField(
                value = invitado.telefono,
                onValueChange = { invitadoViewModel.onTelefonoChange(it) },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = textFieldColors()
            )

            // Campo de Dirección
            OutlinedTextField(
                value = invitado.direccion,
                onValueChange = { invitadoViewModel.onDireccionChange(it) },
                label = { Text("Dirección de Envío") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3, // Hacemos este campo más alto
                colors = textFieldColors()
            )

            // 10. ESPACIADOR FLEXIBLE:
            //     'weight(1f)' es un truco. Es un espacio que ocupa todo
            //     el alto sobrante, empujando el botón "Continuar"
            //     hasta el fondo de la pantalla.
            Spacer(modifier = Modifier.weight(1f))

            // 11. BOTÓN DE CONTINUAR:
            Button(
                onClick = {
                    // Simplemente llamamos a la función de navegación
                    // que nos pasaron para ir al resumen (checkout).
                    onNavigateToCheckout()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = neonBlue,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    "Continuar al Resumen",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    // Color del texto y cursor
    focusedTextColor = textOnDark,
    unfocusedTextColor = textOnDark,
    cursorColor = neonBlue,

    // Colores de la etiqueta (Label)
    focusedLabelColor = neonBlue, // Label color cuando se hace clic
    unfocusedLabelColor = textOnDark.copy(alpha = 0.7f), // Label color normal

    // Colores del borde
    focusedBorderColor = neonBlue, // Borde color cuando se hace clic
    unfocusedBorderColor = neonBlueDim.copy(alpha = 0.5f), // Borde color normal

    // Colores de fondo (transparentes para que se vea nuestro 'loginBg')
    focusedContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent
)