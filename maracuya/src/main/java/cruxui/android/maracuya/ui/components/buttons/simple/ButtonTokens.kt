package cruxui.android.maracuya.ui.components.buttons.simple

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.TypographyToken

// Component token layer del Button por defecto
data class ButtonTokens(
    val containerColor: ColorToken,
    val contentColor: ColorToken,
    val contentPressColor: ColorToken,
    val hoverContainerColor: ColorToken,
    val disabledContainerColor: ColorToken,
    val disabledContentColor: ColorToken,
    val borderContainerColor: ColorToken,
    val borderDisabledColor: ColorToken,
    val textTypography: TypographyToken,
    val fontFamilyToken: FontFamilyToken,
)

data class ButtonTokensOverride(
    val containerColor: ColorToken? = null,
    val contentColor: ColorToken? = null,
    val contentPressColor: ColorToken? = null,
    val hoverContainerColor: ColorToken? = null,
    val disabledContainerColor: ColorToken? = null,
    val disabledContentColor: ColorToken? = null,
    val borderContainerColor: ColorToken? = null,
    val borderDisabledColor: ColorToken? = null,
    val textTypography: TypographyToken? = null,
    val fontFamilyToken: FontFamilyToken? = null,
)

@Composable
fun ButtonTokens.merge(
    override: ButtonTokensOverride?,
): ButtonTokens {
    if (override == null) return this
    return copy(
        containerColor = override.containerColor ?: containerColor,
        contentColor = override.contentColor ?: contentColor,
        contentPressColor = override.contentPressColor ?: contentPressColor,
        hoverContainerColor = override.hoverContainerColor ?: hoverContainerColor,
        disabledContainerColor = override.disabledContainerColor ?: disabledContainerColor,
        disabledContentColor = override.disabledContentColor ?: disabledContentColor,
        borderContainerColor = override.borderContainerColor ?: borderContainerColor,
        borderDisabledColor = override.borderDisabledColor ?: borderDisabledColor,
        textTypography = override.textTypography ?: textTypography,
        fontFamilyToken = override.fontFamilyToken ?: fontFamilyToken,
    )
}
