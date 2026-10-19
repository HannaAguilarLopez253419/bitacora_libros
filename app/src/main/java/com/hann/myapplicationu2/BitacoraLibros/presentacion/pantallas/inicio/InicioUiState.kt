package com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.inicio

import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.FraseDestacada
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.LibroRecomendado

data class InicioUiState(
    val recomendados: List<LibroRecomendado> = emptyList(),
    val fraseFija: FraseDestacada? = null,
    val pendientes: Int = 0,
    val leyendo: Int = 0,
    val finalizados: Int = 0,
    val cargando: Boolean = true
)