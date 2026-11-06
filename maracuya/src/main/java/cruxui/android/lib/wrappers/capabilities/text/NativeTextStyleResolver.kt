package cruxui.android.lib.wrappers.capabilities.text

import android.content.Context
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.wrappers.core.resources.AndroidTokenAdapter
import cruxui.android.lib.wrappers.core.resources.resolve
import cruxui.android.lib.wrappers.core.resources.pixels
import cruxui.android.lib.wrappers.core.tokens.TextDimension
import kotlin.math.abs

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * NativeTextStyleResolver adapta tipografía a métricas Android sin conocer componentes del DS.
 *
 * Justificación:
 * Centraliza escalado, tracking y fuentes para todos los widgets que admiten texto.
 * Recibe la base elegida por tokens del componente; no elige variantes ni accede al registro.
 *
 * Uso:
 * Interno del DS: resolver después de combinar capas y antes del render. No necesita una View.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object NativeTextStyleResolver {
    /** Convierte la base semántica y los campos nativos de mayor prioridad. */
    fun resolve(context: Context, library: LibraryThemeSnapshot, base: TypographyToken,
        family: FontFamilyToken, patch: NativeTextStyleOverrides,
    ): NativeTextStyle {
        val typography = patch.typography?.resolve(library) ?: base
        val size = patch.textSize ?: TextDimension(typography.fontSize)
        val sizePx = size.pixels(context)
        val spacingPx = TextDimension(abs(typography.letterSpacing)).pixels(context) *
            if (typography.letterSpacing < 0) -1 else 1
        val weight = patch.weight ?: typography.weight
        val italic = patch.italic ?: typography.italic
        return NativeTextStyle(
            typeface = patch.font?.resolve(context, weight, italic)
                ?: AndroidTokenAdapter.font(context, family, weight, italic),
            textSize = size,
            lineHeight = patch.lineHeight ?: TextDimension(typography.lineHeight),
            lineSpacing = patch.lineSpacing,
            letterSpacingEm = patch.letterSpacingEm ?: if (sizePx == 0f) 0f else spacingPx / sizePx,
            alignment = TextAlignmentRender(typography.textAlign, patch.textAlignment, patch.gravity, patch.justificationMode),
        )
    }
}
