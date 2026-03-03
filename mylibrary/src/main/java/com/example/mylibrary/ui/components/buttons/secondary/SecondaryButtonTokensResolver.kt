package com.example.mylibrary.ui.components.buttons.secondary

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryTypography

object SecondaryButtonTokensResolver {

    @Composable
    fun resolve(
        tokens: SecondaryButtonTokens? = null,
        override: SecondaryButtonTokensOverride? = null
    ): SecondaryButtonTokens {
        val base = tokens?: defaultTokens()

        return base.merge(override)
    }

    @Composable
    private fun defaultTokens(): SecondaryButtonTokens =
        if(hasLibraryTokens() ) fromLibrary() else fromMaterial()

    @Composable
    private fun hasLibraryTokens(): Boolean {
        return runCatching {
            LocalLibraryColorTokens.current
            LocalLibraryTypography.current
            LocalFontFamily.current
            true
        }.getOrDefault(false)
    }

    /**
     * Deriva ButtonTokens desde LibraryColorTokens (semántica corporativa).
     * Mantener SRP: esta función solo mapea semántica -> tokens del componente.
     */
    @Composable
    fun fromLibrary(): SecondaryButtonTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val fontFamily = LocalFontFamily.current
        return SecondaryButtonTokens(
            containerColor = colors.bgBase,
            contentColor = colors.fgOnInverseAux, // TODO este color debe ser brandSecondary
            contentPressColor = colors.fgOnInverse,
            hoverContainerColor = colors.brandPrimaryHover,
            disabledContainerColor = colors.bgMuted,
            disabledContentColor = colors.fgMuted,
            borderContainerColor = colors.brandPrimary,
            borderDisabledColor = colors.borderDefault,
            textTypography = typography.buttons, // semántica corporativa para botones
            fontFamilyToken = fontFamily
        )
    }

    /**
     * Deriva ButtonTokens desde el ColorScheme activo (Material).
     * Útil cuando la librería host ha solicitado `useMaterial = true`.
     */
    @Composable
    fun fromMaterial(): SecondaryButtonTokens = fromLibrary()

    @Composable
    private fun SecondaryButtonTokens.merge(
        override: SecondaryButtonTokensOverride?
    ): SecondaryButtonTokens {
        if (override == null) return this
        return copy(
            containerColor = override.containerColor ?: containerColor,
            contentColor = override.contentColor ?: contentColor,
            contentPressColor = override.contentPressColor ?: contentPressColor,
            hoverContainerColor = override.hoverContainerColor ?: hoverContainerColor,
            disabledContainerColor = override.disabledContainerColor ?: disabledContainerColor,
            disabledContentColor = override.disabledContentColor ?: disabledContentColor,
            borderContainerColor = override.borderContainerColor ?: borderContainerColor,
            borderDisabledColor = override.borderDisabledColor ?: borderDisabledColor,
            textTypography = override.textTypography ?: textTypography,
            fontFamilyToken = override.fontFamilyToken ?: fontFamilyToken
        )
    }
}