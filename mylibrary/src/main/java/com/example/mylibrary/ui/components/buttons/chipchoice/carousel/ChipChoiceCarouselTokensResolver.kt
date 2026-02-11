package com.example.mylibrary.ui.components.buttons.chipchoice.carousel

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.ui.components.buttons.chipchoice.ChipChoiceBaseTokens

object ChipChoiceCarouselTokensResolver {

    @Composable
    fun resolve(
        override: ChipChoiceCarouselTokens? = null
    ): ChipChoiceCarouselTokens {
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
            LocalFontFamily.current
            true
        }.getOrDefault(false)
    }

    /**
     * Semántica corporativa (Library tokens).
     */
    @Composable
    fun fromLibrary(): ChipChoiceCarouselTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val fontFamily = LocalFontFamily.current

        val unselected = ChipChoiceBaseTokens(
            containerColor = colors.bgBase,
            pressedContainerColor = colors.fgDefault,

            borderColor = colors.borderDefault,

            contentColor = colors.fgDefault,
            pressedContentColor = colors.bgBase,

            disabledContainerColor = colors.bgMuted,
            disabledContentColor = colors.fgSubtle,

            textTypography = typography.subtitle3,
            fontFamilyToken = fontFamily
        )

        val selected = ChipChoiceBaseTokens(
            containerColor = colors.fgDefault,
            pressedContainerColor = colors.fgDefault,

            borderColor = colors.fgDefault,

            contentColor = colors.bgBase,
            pressedContentColor = colors.bgBase,

            disabledContainerColor = colors.bgMuted,
            disabledContentColor = colors.fgSubtle,

            textTypography = typography.subtitle3,
            fontFamilyToken = fontFamily
        )

        return ChipChoiceCarouselTokens(
            unselected = unselected,
            selected = selected
        )
    }

    /**
     * Fallback Material.
     * En este DS, Material delega en Library para mantener consistencia.
     */
    @Composable
    fun fromMaterial(): ChipChoiceCarouselTokens = fromLibrary()
}