package cruxui.android.maracuya.ui.components.buttons.chipchoice

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryTypography

/**
 * Resuelve los tokens del ChipChoiceBase siguiendo la jerarquía del DS.
 */
object ChipChoiceBaseTokensResolver {

    @Composable
    fun resolve(
        override: ChipChoiceBaseTokens? = null
    ): ChipChoiceBaseTokens {
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
    fun fromLibrary(): ChipChoiceBaseTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val fontFamily = LocalFontFamily.current

        return ChipChoiceBaseTokens(
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
    }

    /**
     * Fallback Material.
     * En este DS, Material delega en Library para mantener consistencia.
     */
    @Composable
    fun fromMaterial(): ChipChoiceBaseTokens = fromLibrary()
}
