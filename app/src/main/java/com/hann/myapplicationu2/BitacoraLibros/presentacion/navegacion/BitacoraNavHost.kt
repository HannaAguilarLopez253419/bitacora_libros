package com.hann.myapplicationu2.BitacoraLibros.presentacion.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.biblioteca.BibliotecaPage
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.detalle.DetallePage
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.estadisticas.EstadisticasPage
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.formulario.FormularioPage
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.frases.FrasesPage
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.inicio.InicioPage

@Composable
fun BitacoraNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destino.Inicio.ruta,
        modifier = modifier
    ) {
        composable(Destino.Inicio.ruta) {
            InicioPage()
        }

        composable(Destino.Biblioteca.ruta) {
            BibliotecaPage(
                onAgregarClick = { navController.navigate(Destino.Formulario.crearRuta()) },
                onLibroClick = { id -> navController.navigate(Destino.Detalle.crearRuta(id)) }
            )
        }

        composable(Destino.Frases.ruta) {
            FrasesPage()
        }

        composable(Destino.Estadisticas.ruta) {
            EstadisticasPage()
        }

        composable(
            route = Destino.Formulario.ruta,
            arguments = listOf(
                navArgument(Destino.Formulario.ARG_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { entrada ->
            val libroId = entrada.arguments?.getString(Destino.Formulario.ARG_ID)
            FormularioPage(
                libroId = libroId,
                onVolver = { navController.popBackStack() },
                onGuardado = {
                    navController.popBackStack(Destino.Biblioteca.ruta, inclusive = false)
                }
            )
        }

        composable(
            route = Destino.Detalle.ruta,
            arguments = listOf(
                navArgument(Destino.Detalle.ARG_ID) { type = NavType.StringType }
            )
        ) { entrada ->
            val libroId = entrada.arguments?.getString(Destino.Detalle.ARG_ID).orEmpty()
            DetallePage(
                libroId = libroId,
                onVolver = { navController.popBackStack() },
                onEditar = { id -> navController.navigate(Destino.Formulario.crearRuta(id)) },
                onEliminado = { navController.popBackStack() }
            )
        }
    }
}