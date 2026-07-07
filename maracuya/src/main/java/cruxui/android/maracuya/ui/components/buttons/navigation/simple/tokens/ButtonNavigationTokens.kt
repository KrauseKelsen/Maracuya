package cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken

/**
 * Tokens visuales base para ButtonNavigation.
 *
 * Solo contiene valores declarativos de color, tipografia e iconos.
 */
data class ButtonNavigationTokens(
    val contentColor: ColorToken,
    val contentPressColor: ColorToken,
    val containerColor: ColorToken,
    val disabledContentColor: ColorToken,
    val disabledContainerColor: ColorToken,
    val hoverContainerColor: ColorToken,
    val borderContainerColor: ColorToken,
    val borderDisabledColor: ColorToken,
    val iconToken: IconToken?,
    val loadingIconToken: IconToken?,
    val iconColor: ColorToken,
    val typographyToken: TypographyToken,
    val fontFamilyToken: FontFamilyToken,
)

/**
 * Overrides puntuales para adaptar colores, tipografia e iconos del ButtonNavigation.
 */
data class ButtonNavigationTokensOverride(
    val contentColor: ColorToken? = null,
    val contentPressColor: ColorToken? = null,
    val containerColor: ColorToken? = null,
    val disabledContentColor: ColorToken? = null,
    val disabledContainerColor: ColorToken? = null,
    val hoverContainerColor: ColorToken? = null,
    val borderContainerColor: ColorToken? = null,
    val borderDisabledColor: ColorToken? = null,
    val iconToken: IconToken? = null,
    val loadingIconToken: IconToken? = null,
    val iconColor: ColorToken? = null,
    val typographyToken: TypographyToken? = null,
    val fontFamilyToken: FontFamilyToken? = null,
)

/**
 * Fusiona los tokens resueltos con los overrides enviados por el developer.
 */
fun ButtonNavigationTokens.merge(override: ButtonNavigationTokensOverride?): ButtonNavigationTokens {
    if (override == null) return this
    return copy(
        contentColor = override.contentColor ?: contentColor,
        contentPressColor = override.contentPressColor ?: contentPressColor,
        containerColor = override.containerColor ?: containerColor,
        disabledContentColor = override.disabledContentColor ?: disabledContentColor,
        disabledContainerColor = override.disabledContainerColor ?: disabledContainerColor,
        hoverContainerColor = override.hoverContainerColor ?: hoverContainerColor,
        borderContainerColor = override.borderContainerColor ?: borderContainerColor,
        borderDisabledColor = override.borderDisabledColor ?: borderDisabledColor,
        iconToken = override.iconToken ?: iconToken,
        loadingIconToken = override.loadingIconToken ?: loadingIconToken,
        iconColor = override.iconColor ?: iconColor,
        typographyToken = override.typographyToken ?: typographyToken,
        fontFamilyToken = override.fontFamilyToken ?: fontFamilyToken,
    )
}
