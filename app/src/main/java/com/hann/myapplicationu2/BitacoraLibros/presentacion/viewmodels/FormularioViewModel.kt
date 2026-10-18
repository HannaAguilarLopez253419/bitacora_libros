package com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.Libro
import com.hann.myapplicationu2.BitacoraLibros.datos.repositorio.LibroRepository
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.formulario.FormularioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class FormularioViewModel(
    private val repositorio: LibroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormularioUiState())
    val uiState: StateFlow<FormularioUiState> = _uiState.asStateFlow()

    private var libroId: String? = null
    private var iniciado = false
    private var guardando = false

    fun iniciar(id: String?) {
        if (iniciado) return
        iniciado = true
        if (id == null) return
        libroId = id
        viewModelScope.launch {
            val libro = repositorio.libros.first().firstOrNull { it.id == id } ?: return@launch
            _uiState.value = FormularioUiState(
                titulo = libro.titulo,
                autor = libro.autor,
                paginas = libro.paginas.toString(),
                estado = libro.estado,
                calificacion = libro.calificacion,
                comentario = libro.comentario,
                frase = libro.frase,
                esEdicion = true
            )
        }
    }

    fun cambiarTitulo(valor: String) =
        _uiState.update { it.copy(titulo = valor, errorTitulo = false) }

    fun cambiarAutor(valor: String) =
        _uiState.update { it.copy(autor = valor, errorAutor = false) }

    fun cambiarPaginas(valor: String) =
        _uiState.update {
            it.copy(paginas = valor.filter { c -> c.isDigit() }.take(5), errorPaginas = false)
        }

    fun cambiarEstado(valor: EstadoLectura) =
        _uiState.update { it.copy(estado = valor) }

    fun cambiarCalificacion(valor: Int?) =
        _uiState.update { it.copy(calificacion = valor) }

    fun cambiarComentario(valor: String) =
        _uiState.update { it.copy(comentario = valor) }

    fun cambiarFrase(valor: String) =
        _uiState.update { it.copy(frase = valor) }

    fun guardar() {
        if (guardando) return
        val actual = _uiState.value
        val paginas = actual.paginas.toIntOrNull()

        val errorTitulo = actual.titulo.isBlank()
        val errorAutor = actual.autor.isBlank()
        val errorPaginas = paginas == null || paginas <= 0

        if (errorTitulo || errorAutor || errorPaginas) {
            _uiState.update {
                it.copy(
                    errorTitulo = errorTitulo,
                    errorAutor = errorAutor,
                    errorPaginas = errorPaginas
                )
            }
            return
        }

        guardando = true
        val finalizado = actual.estado == EstadoLectura.FINALIZADO
        val libro = Libro(
            id = libroId ?: UUID.randomUUID().toString(),
            titulo = actual.titulo.trim(),
            autor = actual.autor.trim(),
            paginas = paginas!!,
            estado = actual.estado,
            comentario = if (finalizado) actual.comentario.trim() else "",
            calificacion = if (finalizado) actual.calificacion else null,
            frase = actual.frase.trim()
        )

        viewModelScope.launch {
            if (libroId == null) repositorio.agregar(libro) else repositorio.editar(libro)
            _uiState.update { it.copy(guardado = true) }
        }
    }
}