package com.example.mylibrary.ui.components.inputs.dropdown

import androidx.compose.runtime.Composable
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.ui.components.inputs.config.InputDropDownTokenGroup

/**
 * Resolver único de tokens para InputDropDownMrcy.
 */
object InputDropDownTokensResolver {

    @Composable
    fun resolve(
        group: InputDropDownTokenGroup = InputDropDownTokenGroup.BASIC,
        variant: InputDropDownVariant = InputDropDownVariant.SIMPLE,
        leadingIconOverride: com.example.mylibrary.tokens.base.IconToken? = null,
        override: InputDropDownTokens? = null,
    ): InputDropDownTokens {
        override?.let { return it }

        return when {
            hasLibraryTokens() -> fromLibrary(group, variant, leadingIconOverride)
            else -> fromMaterial(group, variant, leadingIconOverride)
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
    private fun fromLibrary(
        group: InputDropDownTokenGroup,
        variant: InputDropDownVariant,
        leadingIconOverride: IconToken?,
    ): InputDropDownTokens {
        val colors = LocalLibraryColorTokens.current
        val typography = LocalLibraryTypography.current
        val icons = LocalLibraryIcons.current
        val fontFamily = LocalFontFamily.current

        val variantSelectedIcon = when (variant) {
            InputDropDownVariant.SIMPLE -> icons.general.check
            InputDropDownVariant.CHECKBOX -> icons.general.check
        }

        val base = InputDropDownTokens(
            borderDefault = colors.borderDefault,
            borderFocus = colors.borderFocus,
            borderError = colors.errorBorder,
            borderDisabled = colors.borderSubtle,
            backgroundDisabled = colors.surfaceSubtle,
            textColor = colors.fgDefault,
            placeholderColor = colors.fgMuted,
            iconColor = colors.fgDefault,
            menuBackground = colors.bgBase,
            menuBorder = colors.borderSubtle,
            menuItemTextColor = colors.fgDefault,
            menuItemSelectedTextColor = colors.fgDefault,
            menuItemCheckColor = colors.fgDefault,
            textTypography = typography.placeholder,
            placeholderTypography = typography.placeholder,
            menuItemTypography = typography.headline16,
            fontFamilyToken = fontFamily,
            trailingCollapsedIcon = icons.arrows.expandMore,
            trailingExpandedIcon = icons.arrows.expandLess,
            leadingIcon = leadingIconOverride,
            selectedItemLeadingIcon = variantSelectedIcon,
        )

        return when (group) {
            InputDropDownTokenGroup.BASIC -> base
        }
    }

    @Composable
    private fun fromMaterial(
        group: InputDropDownTokenGroup,
        variant: InputDropDownVariant,
        leadingIconOverride: com.example.mylibrary.tokens.base.IconToken?,
    ): InputDropDownTokens = fromLibrary(group, variant, leadingIconOverride)
}
