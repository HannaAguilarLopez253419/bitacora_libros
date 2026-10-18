package com.hann.myapplicationu2

import android.app.Application
import com.hann.myapplicationu2.BitacoraLibros.di.BitacoraModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class BitacoraApp : Application() {

    lateinit var modulo: BitacoraModule
        private set

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        modulo = BitacoraModule(this)
        scope.launch { modulo.libroRepository.inicializarSemilla() }
    }
}