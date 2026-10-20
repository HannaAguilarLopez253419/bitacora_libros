package com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.formulario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura
import com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes.etiqueta
import com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels.FormularioViewModel
import com.hann.myapplicationu2.BitacoraLibros.factory.bitacoraFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioPage(
    libroId: String? = null,
    onVolver: () -> Unit = {},
    onGuardado: () -> Unit = onVolver,
    viewModel: FormularioViewModel = viewModel(factory = bitacoraFactory())
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(libroId) { viewModel.iniciar(libroId) }
    LaunchedEffect(estado.guardado) { if (estado.guardado) onGuardado() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(if (estado.esEdicion) "Editar libro" else "Agregar libro") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = estado.titulo,
                onValueChange = viewModel::cambiarTitulo,
                label = { Text("Título") },
                isError = estado.errorTitulo,
                supportingText = if (estado.errorTitulo) {
                    { Text("El título es obligatorio") }
                } else null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.autor,
                onValueChange = viewModel::cambiarAutor,
                label = { Text("Autor") },
                isError = estado.errorAutor,
                supportingText = if (estado.errorAutor) {
                    { Text("El autor es obligatorio") }
                } else null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.paginas,
                onValueChange = viewModel::cambiarPaginas,
                label = { Text("Páginas") },
                isError = estado.errorPaginas,
                supportingText = if (estado.errorPaginas) {
                    { Text("Escribe un número de páginas mayor que 0") }
                } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Estado")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                EstadoLectura.entries.forEach { opcion ->
                    FilterChip(
                        selected = estado.estado == opcion,
                        onClick = { viewModel.cambiarEstado(opcion) },
                        label = { Text(opcion.etiqueta()) }
                    )
                }
            }

            if (estado.estado == EstadoLectura.FINALIZADO) {
                Text("Calificación (1 a 5)")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    (1..5).forEach { nota ->
                        FilterChip(
                            selected = estado.calificacion == nota,
                            onClick = {
                                viewModel.cambiarCalificacion(
                                    if (estado.calificacion == nota) null else nota
                                )
                            },
                            label = { Text(nota.toString()) }
                        )
                    }
                }

                OutlinedTextField(
                    value = estado.comentario,
                    onValueChange = viewModel::cambiarComentario,
                    label = { Text("Comentario") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            OutlinedTextField(
                value = estado.frase,
                onValueChange = viewModel::cambiarFrase,
                label = { Text("Frase destacada (opcional)") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = viewModel::guardar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (estado.esEdicion) "Guardar cambios" else "Guardar")
            }
        }
    }
}