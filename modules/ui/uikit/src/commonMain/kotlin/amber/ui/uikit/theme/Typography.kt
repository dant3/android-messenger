package amber.ui.uikit.theme

import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_Black
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_BlackItalic
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_Bold
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_BoldItalic
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_ExtraBold
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_ExtraBoldItalic
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_ExtraLight
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_ExtraLightItalic
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_Italic
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_Light
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_LightItalic
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_Medium
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_MediumItalic
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_Regular
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_SemiBold
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_SemiBoldItalic
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_Thin
import amber.modules.ui.uikit.generated.resources.FiraSansCondensed_ThinItalic
import amber.modules.ui.uikit.generated.resources.FiraSans_Black
import amber.modules.ui.uikit.generated.resources.FiraSans_BlackItalic
import amber.modules.ui.uikit.generated.resources.FiraSans_Bold
import amber.modules.ui.uikit.generated.resources.FiraSans_BoldItalic
import amber.modules.ui.uikit.generated.resources.FiraSans_ExtraBold
import amber.modules.ui.uikit.generated.resources.FiraSans_ExtraBoldItalic
import amber.modules.ui.uikit.generated.resources.FiraSans_ExtraLight
import amber.modules.ui.uikit.generated.resources.FiraSans_ExtraLightItalic
import amber.modules.ui.uikit.generated.resources.FiraSans_Italic
import amber.modules.ui.uikit.generated.resources.FiraSans_Light
import amber.modules.ui.uikit.generated.resources.FiraSans_LightItalic
import amber.modules.ui.uikit.generated.resources.FiraSans_Medium
import amber.modules.ui.uikit.generated.resources.FiraSans_MediumItalic
import amber.modules.ui.uikit.generated.resources.FiraSans_Regular
import amber.modules.ui.uikit.generated.resources.FiraSans_SemiBold
import amber.modules.ui.uikit.generated.resources.FiraSans_SemiBoldItalic
import amber.modules.ui.uikit.generated.resources.FiraSans_Thin
import amber.modules.ui.uikit.generated.resources.FiraSans_ThinItalic
import amber.modules.ui.uikit.generated.resources.Res
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font

@Composable
private fun firaSansFontFamily(): FontFamily = FontFamily(
    Font(Res.font.FiraSans_Thin, FontWeight.Thin),
    Font(Res.font.FiraSans_ThinItalic, FontWeight.Thin, FontStyle.Italic),
    Font(Res.font.FiraSans_ExtraLight, FontWeight.ExtraLight),
    Font(Res.font.FiraSans_ExtraLightItalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.FiraSans_Light, FontWeight.Light),
    Font(Res.font.FiraSans_LightItalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.FiraSans_Regular, FontWeight.Normal),
    Font(Res.font.FiraSans_Italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.FiraSans_Medium, FontWeight.Medium),
    Font(Res.font.FiraSans_MediumItalic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.FiraSans_SemiBold, FontWeight.SemiBold),
    Font(Res.font.FiraSans_SemiBoldItalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.FiraSans_Bold, FontWeight.Bold),
    Font(Res.font.FiraSans_BoldItalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.FiraSans_ExtraBold, FontWeight.ExtraBold),
    Font(Res.font.FiraSans_ExtraBoldItalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(Res.font.FiraSans_Black, FontWeight.Black),
    Font(Res.font.FiraSans_BlackItalic, FontWeight.Black, FontStyle.Italic),
)

@Composable
private fun firaSansCondensedFontFamily(): FontFamily = FontFamily(
    Font(Res.font.FiraSansCondensed_Thin, FontWeight.Thin),
    Font(Res.font.FiraSansCondensed_ThinItalic, FontWeight.Thin, FontStyle.Italic),
    Font(Res.font.FiraSansCondensed_ExtraLight, FontWeight.ExtraLight),
    Font(Res.font.FiraSansCondensed_ExtraLightItalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.FiraSansCondensed_Light, FontWeight.Light),
    Font(Res.font.FiraSansCondensed_LightItalic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.FiraSansCondensed_Regular, FontWeight.Normal),
    Font(Res.font.FiraSansCondensed_Italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.FiraSansCondensed_Medium, FontWeight.Medium),
    Font(Res.font.FiraSansCondensed_MediumItalic, FontWeight.Medium, FontStyle.Italic),
    Font(Res.font.FiraSansCondensed_SemiBold, FontWeight.SemiBold),
    Font(Res.font.FiraSansCondensed_SemiBoldItalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.FiraSansCondensed_Bold, FontWeight.Bold),
    Font(Res.font.FiraSansCondensed_BoldItalic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.FiraSansCondensed_ExtraBold, FontWeight.ExtraBold),
    Font(Res.font.FiraSansCondensed_ExtraBoldItalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(Res.font.FiraSansCondensed_Black, FontWeight.Black),
    Font(Res.font.FiraSansCondensed_BlackItalic, FontWeight.Black, FontStyle.Italic),
)

@Composable
fun appTypography(): Typography {
    val fontFamily = firaSansFontFamily()
    val condensedFontFamily = firaSansCondensedFontFamily()
    val baseline = Typography()
    return remember(fontFamily) {
        Typography(
            displayLarge = baseline.displayLarge.copy(fontFamily = fontFamily),
            displayMedium = baseline.displayMedium.copy(fontFamily = fontFamily),
            displaySmall = baseline.displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = baseline.headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = baseline.headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = baseline.headlineSmall.copy(fontFamily = fontFamily),
            titleLarge = baseline.titleLarge.copy(fontFamily = fontFamily),
            titleMedium = baseline.titleMedium.copy(fontFamily = fontFamily),
            titleSmall = baseline.titleSmall.copy(fontFamily = fontFamily),
            bodyLarge = baseline.bodyLarge.copy(fontFamily = condensedFontFamily),
            bodyMedium = baseline.bodyMedium.copy(fontFamily = condensedFontFamily),
            bodySmall = baseline.bodySmall.copy(fontFamily = condensedFontFamily),
            labelLarge = baseline.labelLarge.copy(fontFamily = condensedFontFamily),
            labelMedium = baseline.labelMedium.copy(fontFamily = condensedFontFamily),
            labelSmall = baseline.labelSmall.copy(fontFamily = condensedFontFamily),
        )
    }
}
