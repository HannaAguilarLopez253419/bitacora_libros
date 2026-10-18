package com.hann.myapplicationu2.BitacoraLibros.presentacion

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.biblioteca.BibliotecaPage
import com.hann.myapplicationu2.BitacoraLibros.presentacion.pantallas.formulario.FormularioPage
import com.hann.myapplicationu2.BitacoraLibros.presentacion.viewmodels.bitacoraFactory
import com.hann.myapplicationu2.ui.theme.MyApplicationU2Theme

@Composable
fun BitacoraRaiz() {
    MyApplicationU2Theme {
        var enFormulario by rememberSaveable { mutableStateOf(false) }
        var aperturas by rememberSaveable { mutableIntStateOf(0) }

        if (enFormulario) {
            BackHandler { enFormulario = false }
            FormularioPage(
                libroId = null,
                onVolver = { enFormulario = false },
                viewModel = viewModel(
                    key = "formulario-$aperturas",
                    factory = bitacoraFactory()
                )
            )
        } else {
            BibliotecaPage(
                onAgregarClick = {
                    aperturas++
                    enFormulario = true
                }
            )
        }
    }
}