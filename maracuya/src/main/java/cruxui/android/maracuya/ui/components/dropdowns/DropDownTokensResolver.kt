package cruxui.android.maracuya.ui.components.dropdowns

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.ui.components.inputs.config.InputDropDownTokenGroup

/**
 * Resolver único de tokens de DropDownMrcy.
 */
object DropDownTokensResolver {

    @Composable
    fun resolve(override: DropDownTokens? = null): DropDownTokens {
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
    private fun fromLibrary(): DropDownTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        return DropDownTokens(
            fontFamilyToken = fontFamily,
            bottomTextErrorColor = colors.fgError,
            bottomTextColor = colors.fgMuted,
            bottomTextTypography = typography.bottomText,
            bottomTextIcon = icons.alerts.error,
            inputDropDownTokenGroup = InputDropDownTokenGroup.BASIC,
        )
    }

    @Composable
    private fun fromMaterial(): DropDownTokens = fromLibrary()
}
