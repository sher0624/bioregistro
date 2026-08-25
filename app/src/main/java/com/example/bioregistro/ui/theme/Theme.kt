package com.example.bioregistro.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(

    primary = BioGreen,
    onPrimary = Color.White,

    primaryContainer = BioGreenLight,
    onPrimaryContainer = BioGreenDark,

    secondary = BioBlue,
    onSecondary = Color.White,

    secondaryContainer = BioBlueLight,

    background = BioCream,
    onBackground = BioText,

    surface = BioSurface,
    onSurface = BioText,

    error = BioError
)

private val DarkColorScheme = darkColorScheme(

    primary = BioGreenLight,
    onPrimary = BioGreenDark,

    secondary = BioBlueLight,

    background = Color(0xFF101512),
    surface = Color(0xFF18201C),

    onBackground = Color(0xFFE4EDE7),
    onSurface = Color(0xFFE4EDE7)
)

@Composable
fun BioRegistroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}