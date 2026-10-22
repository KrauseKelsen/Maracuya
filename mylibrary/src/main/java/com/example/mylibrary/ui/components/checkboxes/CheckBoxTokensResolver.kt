package com.example.mylibrary.ui.components.checkboxes

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography

/**
 * Resolver de tokens para CheckBoxMrcy.
 */
object CheckBoxTokensResolver {

    @Composable
    fun resolve(
        override: CheckBoxTokens? = null,
    ): CheckBoxTokens {
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
            LocalLibraryIcons.current
            LocalFontFamily.current
            true
        }.getOrDefault(false)
    }

    @Composable
    private fun fromLibrary(): CheckBoxTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        return CheckBoxTokens(
            fontFamilyToken = fontFamily,
            bottomTextErrorColor = colors.fgError,
            bottomTextColor = colors.fgMuted,
            bottomTextTypography = typography.bottomText,
            bottomTextIcon = icons.alerts.error,
            inputCheckBoxTokensGroup = InputCheckBoxTokensGroup.DEFAULT,
        )
    }

    @Composable
    private fun fromMaterial(): CheckBoxTokens = fromLibrary()
}
