package com.hann.myapplicationu2.BitacoraLibros.di

import android.content.Context
import com.hann.myapplicationu2.BitacoraLibros.datos.fuentedatos.LibroLocalDataSource
import com.hann.myapplicationu2.BitacoraLibros.datos.fuentedatos.SemillaDataSource
import com.hann.myapplicationu2.BitacoraLibros.datos.repositorio.LibroRepository
import com.hann.myapplicationu2.BitacoraLibros.datos.repositorio.LibroRepositoryImpl

class BitacoraModule(context: Context) {

    private val appContext = context.applicationContext

    private val semillaDataSource = SemillaDataSource(appContext)
    private val localDataSource = LibroLocalDataSource(appContext)

    val libroRepository: LibroRepository = LibroRepositoryImpl(semillaDataSource, localDataSource)
}