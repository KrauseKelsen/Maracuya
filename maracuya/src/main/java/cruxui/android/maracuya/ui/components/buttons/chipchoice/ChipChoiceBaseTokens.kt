package cruxui.android.maracuya.ui.components.buttons.chipchoice

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.TypographyToken

/**
 * Tokens visuales para ChipChoiceBase.
 * Contiene únicamente definición de tokens (sin lógica).
 */
data class ChipChoiceBaseTokens(

    // Background
    val containerColor: ColorToken,
    val pressedContainerColor: ColorToken,

    // Border
    val borderColor: ColorToken,

    // Content
    val contentColor: ColorToken,
    val pressedContentColor: ColorToken,

    //Disabled
    val disabledContainerColor: ColorToken,
    val disabledContentColor: ColorToken,

    // Typography
    val textTypography: TypographyToken,
    val fontFamilyToken: FontFamilyToken,
)
