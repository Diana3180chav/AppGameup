<<<<<<<< HEAD:app/src/main/java/com/example/levelup_gamer/ui/theme/screens/home/HomeScreen.kt
package com.example.levelup_gamer.ui.theme.screens.home
========
package com.example.levelup_gamer.ui.theme.screens.homeScreen
>>>>>>>> develop_jsaravia:app/src/main/java/com/example/levelup_gamer/ui/theme/screens/homeScreen/HomeScreen.kt

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.levelup_gamer.ui.theme.utils.obtenerWindowSizeClass

@Composable
fun  HomeScreen( onNavigateToRegister: () -> Unit,
                 onNavigateToLogin: () -> Unit ) {
    val windowSizeClass = obtenerWindowSizeClass()
    Log.d("HomeScreen", "WidthSizeClass: ${windowSizeClass.widthSizeClass}")
    when (windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenCompact(onNavigateToRegister = onNavigateToRegister,
            onNavigateToLogin = onNavigateToLogin) //versión para pantallas pequeñas (celulares).
        WindowWidthSizeClass.Medium -> HomeScreenMedium(onNavigateToRegister = onNavigateToRegister,
            onNavigateToLogin = onNavigateToLogin) //versión para pantallas medianas (tablets verticales).
        WindowWidthSizeClass.Expanded -> HomeScreenExpanded(onNavigateToRegister = onNavigateToRegister,
            onNavigateToLogin = onNavigateToLogin) //versión para pantallas grandes (tablets horizontales o desktop).
    }
}