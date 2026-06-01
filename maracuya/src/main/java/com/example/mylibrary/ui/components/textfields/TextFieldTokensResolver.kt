package com.example.mylibrary.ui.components.textfields

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.ui.components.inputs.config.InputFieldBasicTokenGroup

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
        variant: TextFieldVariant = TextFieldVariant.DEFAULT,
        override: TextFieldTokens? = null
    ): TextFieldTokens {
        override?.let { return it }

        return when {
            hasLibraryTokens() -> fromLibrary(variant)
            else -> fromMaterial(variant)
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
    private fun fromLibrary(variant: TextFieldVariant): TextFieldTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        val inputGroup = when (variant) {
            TextFieldVariant.DEFAULT -> InputFieldBasicTokenGroup.BASIC
            TextFieldVariant.USER -> InputFieldBasicTokenGroup.LEADING_USER
            TextFieldVariant.FACE_ID -> InputFieldBasicTokenGroup.TRAILING_FACE_ID
            TextFieldVariant.PASSWORD -> InputFieldBasicTokenGroup.LEADING_KEY_TRAILING_VISIBILITY
            TextFieldVariant.PIN -> InputFieldBasicTokenGroup.BASIC
        }

        return TextFieldTokens(
            // ───────── Bottom text ─────────
            bottomTextErrorColor = colors.fgError,
            fontFamilyToken = fontFamily,
            bottomTextTypography = typography.bottomText,
            bottomTextColor = colors.fgMuted,
            bottomTextIcon = icons.alerts.error,
            inputFieldTokenGroup = inputGroup,
            passwordHiddenIcon = icons.general.visibility,
            passwordVisibleIcon = icons.general.visibilityOff,
        )
    }

    /**
     * TODO: Fallback Material Design
     *
     * Por ahora reutiliza DS para evitar divergencias visuales
     */
    @Composable
    private fun fromMaterial(variant: TextFieldVariant): TextFieldTokens = fromLibrary(variant)
}
