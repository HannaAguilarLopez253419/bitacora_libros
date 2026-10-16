package com.hann.myapplicationu2.BitacoraLibros.datos.fuentedatos

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hann.myapplicationu2.BitacoraLibros.datos.modelo.Libro
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "bitacora_prefs")

class LibroLocalDataSource(private val context: Context) {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private companion object {
        val CLAVE_LIBROS = stringPreferencesKey("libros")
        val CLAVE_SEMILLA_CARGADA = booleanPreferencesKey("semilla_cargada")
    }

    val libros: Flow<List<Libro>> = context.dataStore.data.map { preferencias ->
        preferencias[CLAVE_LIBROS]
            ?.let { texto -> json.decodeFromString<List<Libro>>(texto) }
            ?: emptyList()
    }

    val semillaCargada: Flow<Boolean> = context.dataStore.data.map { preferencias ->
        preferencias[CLAVE_SEMILLA_CARGADA] ?: false
    }

    suspend fun guardarLibros(lista: List<Libro>) {
        context.dataStore.edit { preferencias ->
            preferencias[CLAVE_LIBROS] = json.encodeToString(lista)
        }
    }

    suspend fun marcarSemillaCargada() {
        context.dataStore.edit { preferencias ->
            preferencias[CLAVE_SEMILLA_CARGADA] = true
        }
    }
}