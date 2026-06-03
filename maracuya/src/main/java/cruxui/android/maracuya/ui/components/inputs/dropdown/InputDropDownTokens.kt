package cruxui.android.maracuya.ui.components.inputs.dropdown

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken

/**
 * Token layer del InputDropDownMrcy.
 */
data class InputDropDownTokens(
    val borderDefault: ColorToken,
    val borderFocus: ColorToken,
    val borderError: ColorToken,
    val borderDisabled: ColorToken,
    val backgroundDisabled: ColorToken,
    val textColor: ColorToken,
    val placeholderColor: ColorToken,
    val iconColor: ColorToken,
    val menuBackground: ColorToken,
    val menuBorder: ColorToken,
    val menuItemTextColor: ColorToken,
    val menuItemSelectedTextColor: ColorToken,
    val menuItemCheckColor: ColorToken,
    val textTypography: TypographyToken,
    val placeholderTypography: TypographyToken,
    val menuItemTypography: TypographyToken,
    val fontFamilyToken: FontFamilyToken,
    val trailingCollapsedIcon: IconToken,
    val trailingExpandedIcon: IconToken,
    val leadingIcon: IconToken? = null,
    val selectedItemLeadingIcon: IconToken,
)
