package com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.biblioteca

import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.Libro

data class BibliotecaUiState (
    val libros:List<Libro> = emptyList(),
    val filtro : EstadoLectura? = null,
    val busqueda : String= "",
    val totalLibro: Int = 0,
    val cargando : Boolean = true
)