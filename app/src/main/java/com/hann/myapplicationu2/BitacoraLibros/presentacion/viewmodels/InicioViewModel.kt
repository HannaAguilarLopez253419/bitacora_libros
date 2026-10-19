package com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.SemillaData
import com.hann.myapplicationu2.BitacoraLibros.datos.repositorio.LibroRepository
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.inicio.InicioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InicioViewModel(
    private val repositorio: LibroRepository
) : ViewModel() {

    private val semilla = MutableStateFlow<SemillaData?>(null)

    init {
        viewModelScope.launch {
            semilla.value = repositorio.leerSemilla()
        }
    }

    val uiState: StateFlow<InicioUiState> =
        combine(repositorio.libros, semilla) { libros, datos ->
            InicioUiState(
                recomendados = datos?.recomendados.orEmpty(),
                fraseFija = datos?.fraseFija,
                pendientes = libros.count { it.estado == EstadoLectura.PENDIENTE },
                leyendo = libros.count { it.estado == EstadoLectura.LEYENDO },
                finalizados = libros.count { it.estado == EstadoLectura.FINALIZADO },
                cargando = datos == null
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InicioUiState()
        )
}