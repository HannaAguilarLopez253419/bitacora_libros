package com.hann.myapplicationu2.BitacoraLibros.datos.fuentedatos

import android.content.Context
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.SemillaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class SemillaDataSource (private val context : Context){
    private val json = Json {
        ignoreUnknownKeys=true
        encodeDefaults=true
    }

    suspend fun leerSemilla(): SemillaData= withContext(Dispatchers.IO){
        val texto = context.assets.open("semilla.json")
            .bufferedReader()
            .use { it.readText() }
        json.decodeFromString<SemillaData>(texto)
    }
}