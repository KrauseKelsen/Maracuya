package cruxui.android.lib.ui.components.inputs.checkbox

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken

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
