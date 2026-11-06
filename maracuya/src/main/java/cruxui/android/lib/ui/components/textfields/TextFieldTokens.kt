package cruxui.android.lib.ui.components.textfields

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.ui.components.inputs.config.InputFieldBasicTokenGroup

//Component token layer del TextField por defecto

data class TextFieldTokens(
    // Texto inferior
    val fontFamilyToken: FontFamilyToken,
    val bottomTextErrorColor: ColorToken,
    val bottomTextTypography: TypographyToken,
    val bottomTextColor: ColorToken,
    val bottomTextIcon: IconToken,
    val inputFieldTokenGroup: InputFieldBasicTokenGroup = InputFieldBasicTokenGroup.BASIC,
    val passwordHiddenIcon: IconToken? = null,
    val passwordVisibleIcon: IconToken? = null,
)
