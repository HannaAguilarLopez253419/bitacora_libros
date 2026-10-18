package com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.formulario

import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura

data class FormularioUiState(
    val titulo: String = "",
    val autor: String = "",
    val paginas: String = "",
    val estado: EstadoLectura = EstadoLectura.PENDIENTE,
    val calificacion: Int? = null,
    val comentario: String = "",
    val frase: String = "",
    val esEdicion: Boolean = false,
    val errorTitulo: Boolean = false,
    val errorAutor: Boolean = false,
    val errorPaginas: Boolean = false,
    val guardado: Boolean = false
)