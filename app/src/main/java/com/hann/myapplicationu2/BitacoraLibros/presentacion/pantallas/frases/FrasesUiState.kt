package com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.frases

import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.FraseDestacada

data class FrasesUiState(
    val frases: List<FraseDestacada> = emptyList(),
    val cargando: Boolean = true
)