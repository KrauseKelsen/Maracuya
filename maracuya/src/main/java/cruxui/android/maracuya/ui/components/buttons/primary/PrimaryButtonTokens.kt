package cruxui.android.maracuya.ui.components.buttons.primary

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.ui.components.buttons.secondary.SecondaryButtonTokens
import cruxui.android.maracuya.ui.components.buttons.secondary.SecondaryButtonTokensOverride

data class PrimaryButtonTokens(
    val containerColor: ColorToken, // DefaultColors.Brand.Red.c500: Rojo
    val contentColor: ColorToken, // DefaultColors.Core.white: Blanco en ambos modos
    val hoverContainerColor: ColorToken, // DefaultColors.Brand.Red.c600: Rojo oscuro
    val disabledContainerColor: ColorToken,
    val disabledContentColor: ColorToken,
    val borderContainerColor: ColorToken, // DefaultColors.Brand.Red.c500: Rojo
    val borderDisabledColor: ColorToken, // DefaultColors.Neutrals.Inks.c300: Rojo
    val textTypography: TypographyToken,
    val fontFamilyToken: FontFamilyToken
)

data class PrimaryButtonTokensOverride(
    val containerColor: ColorToken? = null, // DefaultColors.Brand.Red.c500: Rojo
    val contentColor: ColorToken? = null, // DefaultColors.Core.white: Blanco en ambos modos
    val hoverContainerColor: ColorToken? = null, // DefaultColors.Brand.Red.c600: Rojo oscuro
    val disabledContainerColor: ColorToken? = null,
    val disabledContentColor: ColorToken? = null,
    val borderContainerColor: ColorToken? = null, // DefaultColors.Brand.Red.c500: Rojo
    val borderDisabledColor: ColorToken? = null, // DefaultColors.Neutrals.Inks.c300: Rojo
    val textTypography: TypographyToken? = null,
    val fontFamilyToken: FontFamilyToken? = null,
)

@Composable
fun PrimaryButtonTokens.merge(
    override: PrimaryButtonTokensOverride?
): PrimaryButtonTokens {
    if (override == null) return this
    return copy(
        containerColor = override.containerColor ?: containerColor,
        contentColor = override.contentColor ?: contentColor,
        hoverContainerColor = override.hoverContainerColor ?: hoverContainerColor,
        disabledContainerColor = override.disabledContainerColor ?: disabledContainerColor,
        disabledContentColor = override.disabledContentColor ?: disabledContentColor,
        borderContainerColor = override.borderContainerColor ?: borderContainerColor,
        borderDisabledColor = override.borderDisabledColor ?: borderDisabledColor,
        textTypography = override.textTypography ?: textTypography,
        fontFamilyToken = override.fontFamilyToken ?: fontFamilyToken
    )
}