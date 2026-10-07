package org.example.challenge.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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

private val LightColorScheme = lightColorScheme(
    primary = PitchGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = PitchGreenContainer,
    onPrimaryContainer = OnPitchGreenContainer,
    secondary = PitchGreenSecondary,
    onSecondary = Color.White,
    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF0F172A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569),
    error = ErrorRed
)

@Composable
fun FutbolboxdTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val typography = FutbolboxdTypography()

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
