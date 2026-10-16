package com.hann.myapplicationu2.BitacoraLibros.datos.modelo

import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class Libro(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val autor: String,
    val paginas: Int,
    val estado: Estado = Estado.PENDIENTE,
    val calificacion: Int? = null,
    val opinion: String = "",
    val frase: String = "",
    val paginaActual: Int = 0
) {
    val progreso: Float
        get() = if (paginas > 0) paginaActual.toFloat() / paginas else 0f
}