package cruxui.android.lib.ui.components.checkboxes

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.ui.components.inputs.checkbox.InputCheckBoxTokensGroup

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
