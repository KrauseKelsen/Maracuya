package cruxui.android.maracuya.utils.composeadapters

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.tokens.fonts.TextAlignTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Adaptador que convierte `TypographyToken` (valores puros) a `TextStyle` de Compose.
 * Esta clase es la *única* que conoce Compose en el pipeline tipográfico.
 *
 * Uso:
 * - Invocar desde componentes o desde `MyLibraryTheme` para crear `MaterialTheme.typography`.
 *
 * Notas:
 * - Convierte el `weight` entero a `FontWeight` de Compose.
 * - Respeta `italic` si aplica.
 * - Convierte `textAlign` desde tokens puros hacia Compose.
 *
 * @since 2025-11-18
 * @version 1.5.4
 */
object TypographyComposeAdapter {

    /**
     * Convierte un TypographyToken en un Compose TextStyle usando la familia provista.
     */
    fun toTextStyle(token: TypographyToken, fontFamily: FontFamily): TextStyle {
        val fontWeight = when (token.weight) {
            in 700..899 -> FontWeight.Companion.Bold
            in 600..699 -> FontWeight.Companion.SemiBold
            in 500..599 -> FontWeight.Companion.Medium
            in 400..499 -> FontWeight.Companion.Normal
            else -> FontWeight.Companion.Normal
        }

        val fontStyle = if (token.italic) FontStyle.Companion.Italic else FontStyle.Companion.Normal

        return TextStyle(
            textAlign = token.textAlign.toComposeTextAlign(),
            fontFamily = fontFamily,
            fontSize = token.fontSize.sp,
            lineHeight = token.lineHeight.sp,
            letterSpacing = token.letterSpacing.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyle
        )
    }

    private fun Int.toComposeTextAlign(): TextAlign {
        return when (this) {
            TextAlignTokens.Left -> TextAlign.Left
            TextAlignTokens.Right -> TextAlign.Right
            TextAlignTokens.Center -> TextAlign.Center
            TextAlignTokens.Justify -> TextAlign.Justify
            TextAlignTokens.Start -> TextAlign.Start
            TextAlignTokens.End -> TextAlign.End
            TextAlignTokens.Unspecified -> TextAlign.Unspecified
            else -> TextAlign.Unspecified
        }
    }
}
