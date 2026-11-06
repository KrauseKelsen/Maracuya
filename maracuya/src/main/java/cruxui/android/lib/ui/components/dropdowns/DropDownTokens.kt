package cruxui.android.lib.ui.components.dropdowns

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.ui.components.inputs.config.InputDropDownTokenGroup

/**
 * Token layer del componente compuesto DropDownMrcy.
 */
data class DropDownTokens(
    val fontFamilyToken: FontFamilyToken,
    val bottomTextErrorColor: ColorToken,
    val bottomTextColor: ColorToken,
    val bottomTextTypography: TypographyToken,
    val bottomTextIcon: IconToken,
    val inputDropDownTokenGroup: InputDropDownTokenGroup = InputDropDownTokenGroup.BASIC,
)
