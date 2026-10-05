package org.example.challenge.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import challengetecnico.shared.generated.resources.Res
import challengetecnico.shared.generated.resources.nunito_regular
import challengetecnico.shared.generated.resources.nunito_medium
import challengetecnico.shared.generated.resources.nunito_semibold
import challengetecnico.shared.generated.resources.nunito_bold
import challengetecnico.shared.generated.resources.nunito_extrabold

@Composable
fun NunitoFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.nunito_regular, FontWeight.Normal),
        Font(Res.font.nunito_medium, FontWeight.Medium),
        Font(Res.font.nunito_semibold, FontWeight.SemiBold),
        Font(Res.font.nunito_bold, FontWeight.Bold),
        Font(Res.font.nunito_extrabold, FontWeight.ExtraBold)
    )
}

@Composable
fun FutbolboxdTypography(): Typography {
    val fontFamily = NunitoFontFamily()
    val defaultTypography = Typography()
    return Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = fontFamily, fontWeight = FontWeight.Bold),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = fontFamily, fontWeight = FontWeight.Bold),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = fontFamily, fontWeight = FontWeight.Bold),
        titleLarge = defaultTypography.titleLarge.copy(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = fontFamily, fontWeight = FontWeight.SemiBold),
        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = defaultTypography.labelLarge.copy(fontFamily = fontFamily, fontWeight = FontWeight.Medium),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = fontFamily, fontWeight = FontWeight.Medium),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = fontFamily, fontWeight = FontWeight.Medium)
    )
}
