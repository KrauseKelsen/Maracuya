package com.example.mylibrary.ui.components.inputs.basic

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryTypography

/**
 * InputFieldBasicTokensResolver
 *
 * - Punto único de resolución de estilos DS
 * - Maneja DS vs Material
 */
object InputFieldBasicTokensResolver {

    @Composable
    fun resolve(
        override: InputFieldBasicTokens? = null
    ): InputFieldBasicTokens {
        override?.let { return it }

        return when {
            hasLibraryTokens() -> fromLibrary()
            else -> fromMaterial()
        }
    }

    @Composable
    private fun hasLibraryTokens(): Boolean {
        return runCatching {
            LocalLibraryColorTokens.current
            LocalLibraryTypography.current
            true
        }.getOrDefault(false)
    }

    @Composable
    private fun fromLibrary(): InputFieldBasicTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val fontFamily = LocalFontFamily.current
        return InputFieldBasicTokens(
            borderDefault = colors.borderDefault,
            borderFocus = colors.borderFocus,
            borderError = colors.errorBorder,

            placeholderColor = colors.fgMuted,
            textColor = colors.fgDefault,

            backgroundDisabled = colors.surfaceSubtle,

            placeholderTypography = typography.placeholder,
            textTypography = typography.placeholder,

            fontFamilyToken = fontFamily,
        )
    }

    /**
     * TODO fallback Material
     */
    @Composable
    private fun fromMaterial(): InputFieldBasicTokens = fromLibrary()

}