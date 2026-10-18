package com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.biblioteca

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.TarjetaLibro
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.etiqueta
import com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels.BibliotecaViewModel
import com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels.bitacoraFactory

@Composable
fun BibliotecaPage(
    viewModel: BibliotecaViewModel = viewModel(factory = bitacoraFactory()),
    onAgregarClick: () -> Unit = {},
    onLibroClick: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar libro")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Mi biblioteca",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            OutlinedTextField(
                value = state.busqueda,
                onValueChange = viewModel::cambiarBusqueda,
                placeholder = { Text("Buscar por título o autor") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = state.filtro == null,
                    onClick = { viewModel.cambiarFiltro(null) },
                    label = { Text("Todos") }
                )
                EstadoLectura.entries.forEach { estado ->
                    FilterChip(
                        selected = state.filtro == estado,
                        onClick = { viewModel.cambiarFiltro(estado) },
                        label = { Text(estado.etiqueta()) }
                    )
                }
            }

            when {
                state.cargando -> Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
                state.totalLibro == 0 -> Mensaje("Aún no tienes libros. Toca + para agregar uno.")
                state.libros.isEmpty() -> Mensaje("No se encontraron libros.")
                else -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    items(state.libros, key = { it.id }) { libro ->
                        TarjetaLibro(libro = libro, onClick = { onLibroClick(libro.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun Mensaje(texto: String) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text(texto, style = MaterialTheme.typography.bodyLarge)
    }
}