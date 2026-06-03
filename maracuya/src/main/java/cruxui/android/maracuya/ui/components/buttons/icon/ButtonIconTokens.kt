package cruxui.android.maracuya.ui.components.buttons.icon

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken

data class ButtonIconTokens(
    val contentColor: ColorToken,
    val containerColor: ColorToken,
    val disabledContentColor: ColorToken,
    val disabledContainerColor: ColorToken,
    val hoverContainerColor: ColorToken,
    val iconToken: IconToken,
    val iconColor: ColorToken,
    val typographyToken: TypographyToken,
    val fontFamilyToken: FontFamilyToken,
)

data class ButtonIconTokensOverride(
    val contentColor: ColorToken? = null,
    val containerColor: ColorToken? = null,
    val disabledContentColor: ColorToken? = null,
    val disabledContainerColor: ColorToken? = null,
    val hoverContainerColor: ColorToken? = null,
    val iconToken: IconToken? = null,
    val iconColor: ColorToken? = null,
    val typographyToken: TypographyToken? = null,
    val fontFamilyToken: FontFamilyToken? = null,
)

fun ButtonIconTokens.merge(override: ButtonIconTokensOverride?): ButtonIconTokens {
    if (override == null) return this
    return copy(
        contentColor = override.contentColor ?: contentColor,
        containerColor = override.containerColor ?: containerColor,
        disabledContentColor = override.disabledContentColor ?: disabledContentColor,
        disabledContainerColor = override.disabledContainerColor ?: disabledContainerColor,
        hoverContainerColor = override.hoverContainerColor ?: hoverContainerColor,
        iconToken = override.iconToken ?: iconToken,
        iconColor = override.iconColor ?: iconColor,
        typographyToken = override.typographyToken ?: typographyToken,
        fontFamilyToken = override.fontFamilyToken ?: fontFamilyToken,
    )
}
