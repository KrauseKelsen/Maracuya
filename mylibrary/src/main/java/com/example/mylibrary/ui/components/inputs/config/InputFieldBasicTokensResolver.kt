package com.example.mylibrary.ui.components.inputs.config

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicTokens

/**
 * InputFieldBasicTokensResolver
 *
 * - Punto único de resolución de estilos DS
 * - Maneja DS vs Material
 */
object InputFieldBasicTokensResolver {

    @Composable
    fun resolve(
        group: InputFieldBasicTokenGroup = InputFieldBasicTokenGroup.BASIC,
        override: InputFieldBasicTokens? = null
    ): InputFieldBasicTokens {
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
    private fun fromLibrary(group: InputFieldBasicTokenGroup): InputFieldBasicTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        val baseTokens = InputFieldBasicTokens(
            borderDefault = colors.borderDefault,
            borderFocus = colors.borderFocus,
            borderError = colors.errorBorder,
            placeholderColor = colors.fgMuted,
            textColor = colors.fgDefault,
            backgroundDisabled = colors.surfaceSubtle,
            placeholderTypography = typography.placeholder,
            textTypography = typography.placeholder,
            fontFamilyToken = fontFamily,
            iconColor = colors.fgMuted,
            implicitClearTrailingIcon = icons.general.close,
        )

        return when (group) {
            InputFieldBasicTokenGroup.BASIC -> baseTokens
            InputFieldBasicTokenGroup.LEADING_USER ->
                baseTokens.copy(
                    leadingIcon = icons.people.person,
                )

            InputFieldBasicTokenGroup.TRAILING_FACE_ID ->
                baseTokens.copy(
                    trailingIcon = icons.general.fingerprint,
                )

            InputFieldBasicTokenGroup.LEADING_KEY_TRAILING_VISIBILITY ->
                baseTokens.copy(
                    //leadingIcon = icons.general.lock,
                    trailingIcon = icons.general.visibility,
                    iconColor = colors.fgDefault,
                )

        }
    }

    /**
     * TODO fallback Material
     */
    @Composable
    private fun fromMaterial(group: InputFieldBasicTokenGroup): InputFieldBasicTokens =
        fromLibrary(group)
}
