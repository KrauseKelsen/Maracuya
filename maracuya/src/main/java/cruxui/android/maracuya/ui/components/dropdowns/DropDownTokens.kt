package cruxui.android.maracuya.ui.components.dropdowns

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.ui.components.inputs.config.InputDropDownTokenGroup

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
