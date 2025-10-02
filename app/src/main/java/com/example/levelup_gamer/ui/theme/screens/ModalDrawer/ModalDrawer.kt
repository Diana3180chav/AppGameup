package com.example.levelup_gamer.ui.theme.screens.ModalDrawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MyModalDrawer(
    drawerState: DrawerState,
    drawerContent: @Composable () -> Unit,
    mainContent: @Composable () -> Unit){
    //val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) //esto me dice si la variable va a comenzar cerrada
                                        //esto me permite abrir y cerrar el menú
    ModalNavigationDrawer(
        drawerState = drawerState, //estado inicial de la variable drawerState, es decir, que va a iniciar en closed
        drawerContent = {
            ModalDrawerSheet { // es el contenedor de los elementos que estarán en el menú hamburguesa
                drawerContent() //acá le pasamos el contenido del menú hamburguesa
            }
        }, //este es el contenido que viene en el menú hamburguesa
        scrimColor = Color.LightGray //le pasamos un color al menú
    ) {
        //Acá le colocamos el contenido que va a existir detrás del ModalDrawer
        mainContent()
    }
}