package com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hann.myapplicationu2.BitacoraApp
import com.hann.myapplicationu2.BitacoraLibros.datos.repositorio.LibroRepository

class BitacoraViewModelFactory(
    private val repositorio: LibroRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BibliotecaViewModel::class.java) ->
                BibliotecaViewModel(repositorio) as T
            else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
        }
    }
}

@Composable
fun bitacoraFactory(): BitacoraViewModelFactory {
    val app = LocalContext.current.applicationContext as BitacoraApp
    return BitacoraViewModelFactory(app.modulo.libroRepository)
}