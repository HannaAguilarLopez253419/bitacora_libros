package com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.FraseDestacada
import com.hann.myapplicationu2.BitacoraLibros.datos.repositorio.LibroRepository
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.frases.FrasesUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FrasesViewModel(
    repositorio: LibroRepository
) : ViewModel() {

    val uiState: StateFlow<FrasesUiState> =
        repositorio.libros
            .map { libros ->
                FrasesUiState(
                    frases = libros
                        .filter { it.frase.isNotBlank() }
                        .map { libro ->
                            FraseDestacada(
                                texto = libro.frase,
                                autor = libro.autor,
                                libro = libro.titulo
                            )
                        },
                    cargando = false
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FrasesUiState()
            )
}