package com.hann.myapplicationu2.BitacoraLibros.datos.repositorio

import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.Libro
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.SemillaData
import kotlinx.coroutines.flow.Flow

interface LibroRepository {
    val libros: Flow<List<Libro>>

    suspend fun inicializarSemilla()
    suspend fun leerSemilla(): SemillaData
    suspend fun agregar(libro: Libro)
    suspend fun editar(libro: Libro)
    suspend fun eliminar(id: String)
}