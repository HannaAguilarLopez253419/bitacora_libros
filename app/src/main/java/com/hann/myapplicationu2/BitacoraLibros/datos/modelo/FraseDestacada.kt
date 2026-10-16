package com.hann.myapplicationu2.BitacoraLibros.datos.modelo

import kotlinx.serialization.Serializable

@Serializable
data class FraseDestacada(
    val texto: String,
    val autor: String,
    val libro: String
)