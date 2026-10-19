package com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hann.myapplicationu2.BitacoraLibros.datos.repositorio.LibroRepository
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.detalle.DetalleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetalleViewModel(
    private val repositorio: LibroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleUiState())
    val uiState: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    private var libroId: String? = null

    fun cargar(id: String) {
        if (libroId == id) return
        libroId = id
        repositorio.libros
            .map { lista -> lista.firstOrNull { it.id == id } }
            .onEach { libro -> _uiState.update { it.copy(libro = libro, cargando = false) } }
            .launchIn(viewModelScope)
    }

    fun pedirConfirmacion() {
        _uiState.update { it.copy(mostrarConfirmacion = true) }
    }

    fun cancelarConfirmacion() {
        _uiState.update { it.copy(mostrarConfirmacion = false) }
    }

    fun eliminar() {
        val id = libroId ?: return
        viewModelScope.launch {
            repositorio.eliminar(id)
            _uiState.update { it.copy(mostrarConfirmacion = false, eliminado = true) }
        }
    }
}