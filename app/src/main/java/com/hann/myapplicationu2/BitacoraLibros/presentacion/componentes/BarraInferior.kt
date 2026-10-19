package com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.hann.myapplicationu2.BitacoraLibros.presentacion.navegacion.Destino

private fun Destino.etiquetaBarra(): String = when (this) {
    Destino.Inicio -> "Inicio"
    Destino.Biblioteca -> "Biblioteca"
    Destino.Frases -> "Frases"
    Destino.Estadisticas -> "Estadísticas"
    else -> ""
}

private fun Destino.iconoBarra(): ImageVector = when (this) {
    Destino.Inicio -> Icons.Filled.Home
    Destino.Biblioteca -> Icons.AutoMirrored.Filled.MenuBook
    Destino.Frases -> Icons.Filled.FormatQuote
    Destino.Estadisticas -> Icons.Filled.BarChart
    else -> Icons.Filled.Home
}

@Composable
fun BarraInferior(
    rutaActual: String?,
    onSeleccionar: (Destino) -> Unit
) {
    NavigationBar {
        Destino.conBarra.forEach { destino ->
            NavigationBarItem(
                selected = rutaActual == destino.ruta,
                onClick = { onSeleccionar(destino) },
                icon = { Icon(destino.iconoBarra(), contentDescription = destino.etiquetaBarra()) },
                label = { Text(destino.etiquetaBarra()) }
            )
        }
    }
}