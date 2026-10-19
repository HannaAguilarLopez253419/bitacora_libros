package com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.LibroRecomendado

@Composable
fun TarjetaRecomendado(libro: LibroRecomendado, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = libro.titulo, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${libro.autor} · ${libro.paginas} páginas",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(text = libro.descripcion, style = MaterialTheme.typography.bodyMedium)
        }
    }
}