package com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.FilaDato
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.PortadaLibroGrande
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.etiqueta
import com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels.DetalleViewModel
import com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels.bitacoraFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePage(
    libroId: String,
    onVolver: () -> Unit = {},
    onEditar: (String) -> Unit = {},
    onEliminado: () -> Unit = {},
    viewModel: DetalleViewModel = viewModel(factory = bitacoraFactory())
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(libroId) { viewModel.cargar(libroId) }
    LaunchedEffect(estado.eliminado) { if (estado.eliminado) onEliminado() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        val libro = estado.libro

        when {
            estado.cargando || estado.eliminado -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    if (estado.cargando) CircularProgressIndicator()
                }
            }

            libro == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontró el libro")
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PortadaLibroGrande(titulo = libro.titulo)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = libro.titulo,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = libro.autor,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    FilaDato(etiqueta = "Páginas", valor = libro.paginas.toString())
                    FilaDato(etiqueta = "Estado", valor = libro.estado.etiqueta())

                    if (libro.estado == EstadoLectura.FINALIZADO) {
                        FilaDato(
                            etiqueta = "Calificación",
                            valor = libro.calificacion?.let { "$it / 5" } ?: "Sin calificación"
                        )
                        FilaDato(
                            etiqueta = "Comentario",
                            valor = libro.comentario.ifBlank { "Sin comentario" }
                        )
                    }

                    FilaDato(
                        etiqueta = "Frase destacada",
                        valor = if (libro.frase.isBlank()) "Sin frase" else "“${libro.frase}”"
                    )

                    Button(
                        onClick = { onEditar(libro.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Editar")
                    }

                    OutlinedButton(
                        onClick = viewModel::pedirConfirmacion,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Eliminar")
                    }
                }

                if (estado.mostrarConfirmacion) {
                    AlertDialog(
                        onDismissRequest = viewModel::cancelarConfirmacion,
                        title = { Text("Eliminar libro") },
                        text = {
                            Text("¿Seguro que quieres eliminar «${libro.titulo}»? Esta acción no se puede deshacer.")
                        },
                        confirmButton = {
                            TextButton(onClick = viewModel::eliminar) { Text("Eliminar") }
                        },
                        dismissButton = {
                            TextButton(onClick = viewModel::cancelarConfirmacion) { Text("Cancelar") }
                        }
                    )
                }
            }
        }
    }
}