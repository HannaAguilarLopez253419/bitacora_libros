package com.hann.myapplicationu2.BitacoraLibros.datos.modelo

import java.util.UUID
import kotlinx.serialization.Serializable
@Serializable
data class Libro(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val autor: String,
    val paginas: Int,
    val estado: EstadoLectura = EstadoLectura.PENDIENTE,
    val comentario: String = "",
    val calificacion: Int? = null,
    val frase: String = ""
)