package cruxui.android.maracuya.ui.components.labels

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography

/**
 * LabelTokensResolver
 *
 * - Punto único de resolución de tokens
 * - Conoce DS y Material
 * - Maneja fallbacks
 */
object LabelTokensResolver {

    @Composable
    fun resolve(
        tokens: LabelTokens? = null,
        override: LabelTokensOverride? = null
    ): LabelTokens {
        val base = tokens?: defaultTokens()

        return base.merge(override)
    }

    @Composable
    private fun defaultTokens(): LabelTokens =
        if(hasLibraryTokens()) fromLibrary() else fromMaterial()

    /**
     * Detecta si el DS está correctamente provisto
     */
    @Composable
    private fun hasLibraryTokens(): Boolean {
        return runCatching {
            LocalLibraryColorTokens.current
            LocalLibraryTypography.current
            LocalLibraryIcons.current
            LocalFontFamily.current
            true
        }.getOrDefault(false)
    }

    /**
     * Resolución desde semántica corporativa (DS)
     */
    @Composable
    private fun fromLibrary(): LabelTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        return LabelTokens(
            foregroundDefault = colors.fgDefault,
            foregroundError = colors.fgError,
            labelTypography = typography.label,
            optionalTypography = typography.caption,
            infoIcon = icons.alerts.info,
            fontFamily = fontFamily
        )
    }

    /**
     * TODO No esta implementado Material Design
     * Fallback MaterialTheme (cuando no hay DS)
     */
    @Composable
    private fun fromMaterial(): LabelTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        return LabelTokens(
            foregroundDefault = colors.fgDefault,
            foregroundError = colors.fgError,
            labelTypography = typography.label,
            optionalTypography = typography.caption,
            infoIcon = icons.alerts.info,
            fontFamily = fontFamily
        )
    }

    @Composable
    private fun LabelTokens.merge(
        override: LabelTokensOverride?
    ): LabelTokens{
        if (override==null) return this
        return copy(
            foregroundDefault = override.foregroundDefault?: foregroundDefault,
            foregroundError = override.foregroundError?: foregroundError,
            labelTypography = override.labelTypography?: labelTypography,
            optionalTypography = override.optionalTypography?: optionalTypography,
            infoIcon = override.infoIcon?: infoIcon,
            fontFamily = override.fontFamily?: fontFamily
        )
    }
}

