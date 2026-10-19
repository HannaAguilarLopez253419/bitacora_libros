package com.hann.myapplicationu2.BitacoraLibros.presentacion

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.BarraInferior
import com.hann.myapplicationu2.BitacoraLibros.presentacion.navegacion.BitacoraNavHost
import com.hann.myapplicationu2.BitacoraLibros.presentacion.navegacion.Destino
import com.hann.myapplicationu2.ui.theme.MyApplicationU2Theme

@Composable
fun BitacoraRaiz() {
    MyApplicationU2Theme {
        val navController = rememberNavController()
        val entradaActual by navController.currentBackStackEntryAsState()
        val rutaActual = entradaActual?.destination?.route
        val mostrarBarra = Destino.conBarra.any { it.ruta == rutaActual }

        Scaffold(
            bottomBar = {
                if (mostrarBarra) {
                    BarraInferior(
                        rutaActual = rutaActual,
                        onSeleccionar = { destino ->
                            navController.navigate(destino.ruta) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { innerPadding ->
            BitacoraNavHost(
                navController = navController,
                modifier = Modifier
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
            )
        }
    }
}