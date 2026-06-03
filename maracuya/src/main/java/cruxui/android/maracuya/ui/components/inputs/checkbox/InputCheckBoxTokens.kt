package cruxui.android.maracuya.ui.components.inputs.checkbox

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken

/**
 * Component Token Layer — InputCheckBox.
 *
 * Tokeniza al átomo base para desacoplar UI y semántica.
 */
data class InputCheckBoxTokens(
    val borderDefault: ColorToken,
    val borderDisabled: ColorToken,
    val checkBackground: ColorToken,
    val checkIconColor: ColorToken,
    val labelColor: ColorToken,
    val labelDisabledColor: ColorToken,
    val labelTypography: TypographyToken,
    val fontFamilyToken: FontFamilyToken,
    val checkIcon: IconToken,
)
