package cruxui.android.maracuya.ui.components.buttons.icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.tokens.base.ColorToken

object ButtonIconTokensResolver {

    @get:Composable
    val primary: ButtonIconTokens
        get() = resolve(group = ButtonIconTokenGroup.PRIMARY)

    @get:Composable
    val secondary: ButtonIconTokens
        get() = resolve(group = ButtonIconTokenGroup.SECONDARY)

    @get:Composable
    val loading: ButtonIconTokens
        get() = resolve(group = ButtonIconTokenGroup.LOADING)

    @Composable
    fun resolve(
        group: ButtonIconTokenGroup = ButtonIconTokenGroup.PRIMARY,
        tokens: ButtonIconTokens? = null,
        override: ButtonIconTokensOverride? = null,
    ): ButtonIconTokens {
        val base = tokens ?: defaultTokens(group)
        return base.merge(override)
    }

    @Composable
    private fun defaultTokens(group: ButtonIconTokenGroup): ButtonIconTokens =
        if (hasLibraryTokens()) fromLibrary(group) else fromMaterial(group)

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
    fun fromLibrary(group: ButtonIconTokenGroup): ButtonIconTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        val transparent = ColorToken(0x00000000)
        val secondaryHover = ColorToken(0xFFF3F4F6)

        return when (group) {
            ButtonIconTokenGroup.PRIMARY -> ButtonIconTokens(
                contentColor = colors.fgOnInverse,
                containerColor = colors.brandPrimary,
                disabledContentColor = colors.fgMuted,
                disabledContainerColor = colors.bgMuted,
                hoverContainerColor = colors.brandPrimaryHover,
                iconToken = icons.general.check,
                iconColor = colors.fgOnInverse,
                typographyToken = typography.buttons,
                fontFamilyToken = fontFamily,
            )

            ButtonIconTokenGroup.SECONDARY -> ButtonIconTokens(
                contentColor = colors.fgDefault,
                containerColor = transparent,
                disabledContentColor = colors.fgMuted,
                disabledContainerColor = colors.bgMuted,
                hoverContainerColor = secondaryHover,
                iconToken = icons.arrows.keyboardBackspaceLeft,
                iconColor = colors.fgDefault,
                typographyToken = typography.buttons,
                fontFamilyToken = fontFamily,
            )

            ButtonIconTokenGroup.LOADING -> ButtonIconTokens(
                contentColor = colors.fgDefault,
                containerColor = transparent,
                disabledContentColor = colors.fgMuted,
                disabledContainerColor = colors.bgMuted,
                hoverContainerColor = secondaryHover,
                iconToken = icons.arrows.keyboardBackspaceRight,
                iconColor = colors.fgDefault,
                typographyToken = typography.buttons,
                fontFamilyToken = fontFamily,
            )
        }
    }

    @Composable
    fun fromMaterial(group: ButtonIconTokenGroup): ButtonIconTokens = fromLibrary(group)
}