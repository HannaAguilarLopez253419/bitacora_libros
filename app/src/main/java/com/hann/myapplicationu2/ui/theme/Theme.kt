package com.hann.myapplicationu2.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EsquemaBitacora = lightColorScheme(
    primary = VioletaPrincipal,
    onPrimary = SobreVioletaPrincipal,
    primaryContainer = PastelRosa,
    onPrimaryContainer = VioletaTexto,
    secondary = AzulAcento,
    onSecondary = SobreVioletaPrincipal,
    secondaryContainer = PastelAzul,
    onSecondaryContainer = VioletaTexto,
    tertiary = MentaAcento,
    onTertiary = SobreVioletaPrincipal,
    tertiaryContainer = PastelMenta,
    onTertiaryContainer = VioletaTexto,
    background = VioletaFondo,
    onBackground = VioletaTexto,
    surface = VioletaFondo,
    onSurface = VioletaTexto,
    onSurfaceVariant = VioletaTextoSecundario,
    surfaceContainer = VioletaBarra,
    surfaceContainerHighest = VioletaTarjeta,
    outline = VioletaBorde,
    outlineVariant = VioletaBordeSuave
)

@Composable
fun MyApplicationU2Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = EsquemaBitacora,
        typography = Typography,
        content = content
    )
}