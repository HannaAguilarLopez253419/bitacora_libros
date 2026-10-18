package com.hann.myapplicationu2

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.hann.myapplicationu2.BitacoraLibros.datos.fuentedatos.SemillaDataSource
import com.hann.myapplicationu2.BitacoraLibros.di.BitacoraModule
import com.hann.myapplicationu2.BitacoraLibros.presentacion.BitacoraRaiz
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.biblioteca.BibliotecaPage
import com.hann.myapplicationu2.ui.theme.MyApplicationU2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
            setContent {
                BitacoraRaiz()
            }
    }
}