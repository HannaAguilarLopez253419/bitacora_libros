package com.hann.myapplicationu2.BitacoraLibros.presentacion.componentes

import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.EstadoLectura

fun EstadoLectura.etiqueta(): String = when (this) {
    EstadoLectura.PENDIENTE -> "Sin leer"
    EstadoLectura.LEYENDO -> "Leyendo"
    EstadoLectura.FINALIZADO -> "Finalizado"
}
