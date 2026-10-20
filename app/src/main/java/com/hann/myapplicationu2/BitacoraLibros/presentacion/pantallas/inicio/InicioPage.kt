package com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.CardInicio
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.ContadorEstado
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.TarjetaFrase
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.TarjetaRecomendado
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.etiqueta
import com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels.InicioViewModel
import com.hann.myapplicationu2.BitacoraLibros.factory.bitacoraFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioPage(
    viewModel: InicioViewModel = viewModel(factory = bitacoraFactory())
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Inicio") }) }
    ) { innerPadding ->
        if (estado.cargando) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardInicio()

                Text(
                    text = "Tu progreso",
                    style = MaterialTheme.typography.titleLarge
                )
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ContadorEstado(
                        etiqueta = EstadoLectura.PENDIENTE.etiqueta(),
                        cantidad = estado.pendientes,
                        modifier = Modifier.weight(1f)
                    )
                    ContadorEstado(
                        etiqueta = EstadoLectura.LEYENDO.etiqueta(),
                        cantidad = estado.leyendo,
                        modifier = Modifier.weight(1f)
                    )
                    ContadorEstado(
                        etiqueta = EstadoLectura.FINALIZADO.etiqueta(),
                        cantidad = estado.finalizados,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = "Libros recomendados",
                    style = MaterialTheme.typography.titleLarge
                )
                estado.recomendados.forEach { TarjetaRecomendado(libro = it) }

                estado.fraseFija?.let { TarjetaFrase(frase = it) }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}