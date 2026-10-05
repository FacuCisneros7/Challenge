package org.example.challenge.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PitchGreenPrimary,
    onPrimary = Color.Black,
    primaryContainer = PitchGreenContainer,
    onPrimaryContainer = OnPitchGreenContainer,
    secondary = PitchGreenSecondary,
    onSecondary = Color.Black,
    background = DarkBackground,
    onBackground = OnDarkBackground,
    surface = DarkSurface,
    onSurface = OnDarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = OnDarkSurfaceVariant,
    error = ErrorRed
)

@Composable
fun FutbolboxdTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val typography = FutbolboxdTypography()

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
