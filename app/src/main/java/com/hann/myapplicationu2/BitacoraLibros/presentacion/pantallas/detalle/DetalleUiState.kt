package com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.detalle

import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.Libro

data class DetalleUiState(
    val libro: Libro? = null,
    val cargando: Boolean = true,
    val mostrarConfirmacion: Boolean = false,
    val eliminado: Boolean = false
)