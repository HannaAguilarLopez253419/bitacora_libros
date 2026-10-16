package com.hann.myapplicationu2.BitacoraLibros.datos.modelo

import kotlinx.serialization.Serializable

@Serializable
data class SemillaData(
    val libros: List<Libro>,
    val recomendados: List<LibroRecomendado>,
    val fraseCard: FraseDestacada,
    val fraseFija: FraseDestacada
        )
