package com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura
import com.hann.myapplicationu2.BitacoraLibros.datos.repositorio.LibroRepository
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.biblioteca.BibliotecaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BibliotecaViewModel (private val repositorio: LibroRepository): ViewModel(){
    private val filtro = MutableStateFlow<EstadoLectura?>(null)
    private val busqueda = MutableStateFlow("")

    val uiState: StateFlow<BibliotecaUiState> =
        combine(repositorio.libros, filtro, busqueda) { libros, estadoFiltro, texto ->
            val visibles = libros
                .filter { estadoFiltro == null || it.estado == estadoFiltro }
                .filter {
                    texto.isBlank() ||
                            it.titulo.contains(texto, ignoreCase = true) ||
                            it.autor.contains(texto, ignoreCase = true)
                }
            BibliotecaUiState(
                libros = visibles,
                filtro = estadoFiltro,
                busqueda = texto,
                totalLibro = libros.size,
                cargando = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BibliotecaUiState()
        )
    fun cambiarFiltro(estado: EstadoLectura?) {
        filtro.value = estado
    }

    fun cambiarBusqueda(texto: String) {
        busqueda.value = texto
    }
}