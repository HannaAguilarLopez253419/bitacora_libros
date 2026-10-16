package com.hann.myapplicationu2.BitacoraLibros.datos.repositorio

import com.hann.myapplicationu2.BitacoraLibros.datos.fuentedatos.LibroLocalDataSource
import com.hann.myapplicationu2.BitacoraLibros.datos.fuentedatos.SemillaDataSource
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.Libro
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.SemillaData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class LibroRepositoryImpl(
    private val semilla: SemillaDataSource,
    private val local: LibroLocalDataSource
) : LibroRepository {

    override val libros: Flow<List<Libro>> = local.libros

    override suspend fun inicializarSemilla() {
        if (local.semillaCargada.first()) return
        local.guardarLibros(semilla.leerSemilla().libros)
        local.marcarSemillaCargada()
    }

    override suspend fun leerSemilla(): SemillaData = semilla.leerSemilla()

    override suspend fun agregar(libro: Libro) {
        val actuales = local.libros.first()
        local.guardarLibros(actuales + libro)
    }

    override suspend fun editar(libro: Libro) {
        val actuales = local.libros.first()
        local.guardarLibros(actuales.map { if (it.id == libro.id) libro else it })
    }

    override suspend fun eliminar(id: String) {
        val actuales = local.libros.first()
        local.guardarLibros(actuales.filter { it.id != id })
    }
}