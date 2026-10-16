package com.hann.myapplicationu2.BitacoraLibros.datos.modelo

import kotlinx.serialization.Serializable

@Serializable
data class LibroRecomendado (
    val titulo: String,
    val autor: String,
    val paginas : Int,
    val descripcion:String

)