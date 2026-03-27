package com.example.mylibrary.ui.components.inputs.checkbox

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography

/**
 * Punto único para resolver los tokens del InputCheckBox.
 */
object InputCheckBoxTokensResolver {

    @Composable
    fun resolve(
        group: InputCheckBoxTokensGroup = InputCheckBoxTokensGroup.DEFAULT,
        override: InputCheckBoxTokens? = null,
    ): InputCheckBoxTokens {
        override?.let { return it }

        return when {
            hasLibraryTokens() -> fromLibrary(group)
            else -> fromMaterial(group)
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
    private fun fromLibrary(group: InputCheckBoxTokensGroup): InputCheckBoxTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        return when (group) {
            InputCheckBoxTokensGroup.DEFAULT -> InputCheckBoxTokens(
                borderDefault = colors.borderDefault,
                borderDisabled = colors.borderSubtle,
                checkBackground = colors.supportDefault,
                checkIconColor = colors.fgOnInverse,
                labelColor = colors.fgDefault,
                labelDisabledColor = colors.fgMuted,
                labelTypography = typography.placeholder,
                fontFamilyToken = fontFamily,
                checkIcon = icons.general.check,
            )
        }
    }

    @Composable
    private fun fromMaterial(group: InputCheckBoxTokensGroup): InputCheckBoxTokens = fromLibrary(group)
}
