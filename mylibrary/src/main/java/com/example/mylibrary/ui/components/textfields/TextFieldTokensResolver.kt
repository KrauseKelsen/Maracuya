package com.example.mylibrary.ui.components.textfields

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography

/**
 * TextFieldTokensResolver
 *
 * - Punto único de resolución de tokens para TextFieldMrcy
 * - Orquesta tokens de Label + InputFieldBasic + BottomText
 * - DS first, Material fallback (pendiente)
 */
object TextFieldTokensResolver {

    /**
     * Resolución pública de tokens
     */
    @Composable
    fun resolve(
        override: TextFieldTokens? = null
    ): TextFieldTokens {
        override?.let { return it }

        return when {
            hasLibraryTokens() -> fromLibrary()
            else -> fromMaterial()
        }
    }

    /**
     * Detecta si el Design System está correctamente inyectado
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
     * Resolución desde tokens corporativos (DS)
     */
    @Composable
    private fun fromLibrary(): TextFieldTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        return TextFieldTokens(

            // ───────── Bottom text ─────────
            bottomTextErrorColor = colors.fgError,
            fontFamilyToken = fontFamily,
            bottomTextTypography = typography.bottomText,
            bottomTextColor = colors.fgMuted,
            bottomTextIcon = icons.alerts.error
        )
    }

    /**
     * TODO: Fallback Material Design
     *
     * Por ahora reutiliza DS para evitar divergencias visuales
     */
    @Composable
    private fun fromMaterial(): TextFieldTokens = fromLibrary()
}

