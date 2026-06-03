package cruxui.android.maracuya.ui.components.inputs.checkbox

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography

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
                labelTypography = typography.headline16,
                fontFamilyToken = fontFamily,
                checkIcon = icons.general.check,
            )
        }
    }

    @Composable
    private fun fromMaterial(group: InputCheckBoxTokensGroup): InputCheckBoxTokens = fromLibrary(group)
}
