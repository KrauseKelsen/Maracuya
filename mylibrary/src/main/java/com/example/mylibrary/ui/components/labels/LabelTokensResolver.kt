package com.example.mylibrary.ui.components.labels

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography

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
        override: LabelTokens? = null
    ): LabelTokens {
        override?.let { return it }

        return when {
            hasLibraryTokens() -> fromLibrary()
            else -> fromMaterial()
        }
    }

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
}

