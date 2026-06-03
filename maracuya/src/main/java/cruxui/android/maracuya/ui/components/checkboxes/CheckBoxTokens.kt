package cruxui.android.maracuya.ui.components.checkboxes

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.ui.components.inputs.checkbox.InputCheckBoxTokensGroup

/**
 * Token layer para CheckBoxMrcy (componente compuesto).
 */
data class CheckBoxTokens(
    val fontFamilyToken: FontFamilyToken,
    val bottomTextErrorColor: ColorToken,
    val bottomTextColor: ColorToken,
    val bottomTextTypography: TypographyToken,
    val bottomTextIcon: IconToken,
    val inputCheckBoxTokensGroup: InputCheckBoxTokensGroup = InputCheckBoxTokensGroup.DEFAULT,
)
