package com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hann.myapplicationu2.BitacoraApp
import com.hann.myapplicationu2.BitacoraLibros.datos.repositorio.LibroRepository

class BitacoraViewModelFactory(
    private val repositorio: LibroRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(BibliotecaViewModel::class.java) ->
            BibliotecaViewModel(repositorio) as T

        modelClass.isAssignableFrom(FormularioViewModel::class.java) ->
            FormularioViewModel(repositorio) as T

        else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}

@Composable
fun bitacoraFactory(): BitacoraViewModelFactory {
    val app = LocalContext.current.applicationContext as BitacoraApp
    return remember(app) { BitacoraViewModelFactory(app.modulo.libroRepository) }
}