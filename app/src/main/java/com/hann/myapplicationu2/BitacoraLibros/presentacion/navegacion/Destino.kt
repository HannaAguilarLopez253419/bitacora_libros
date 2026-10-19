package com.hann.myapplicationu2.BitacoraLibros.presentacion.navegacion

sealed class Destino(val ruta: String) {

    data object Inicio : Destino("inicio")
    data object Biblioteca : Destino("biblioteca")
    data object Frases : Destino("frases")
    data object Estadisticas : Destino("estadisticas")

    data object Formulario : Destino("formulario?libroId={libroId}") {
        const val ARG_ID = "libroId"

        fun crearRuta(id: String? = null): String =
            if (id == null) "formulario" else "formulario?libroId=$id"
    }

    data object Detalle : Destino("detalle/{libroId}") {
        const val ARG_ID = "libroId"

        fun crearRuta(id: String): String = "detalle/$id"
    }

    companion object {
        val conBarra: List<Destino> = listOf(Inicio, Biblioteca, Frases, Estadisticas)
    }
}