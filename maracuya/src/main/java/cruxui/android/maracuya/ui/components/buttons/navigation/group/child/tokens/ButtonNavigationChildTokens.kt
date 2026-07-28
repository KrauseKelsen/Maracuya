package cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokens

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken

/**
 * Overrides puntuales permitidos para un `ButtonNavigationChild`.
 *
 * No expone tokens de loading porque el child personalizado del grupo no maneja
 * estado de carga.
 */
data class ButtonNavigationChildTokensOverride(
    val contentColor: ColorToken? = null,
    val contentPressColor: ColorToken? = null,
    val containerColor: ColorToken? = null,
    val disabledContentColor: ColorToken? = null,
    val disabledContainerColor: ColorToken? = null,
    val hoverContainerColor: ColorToken? = null,
    val borderContainerColor: ColorToken? = null,
    val borderDisabledColor: ColorToken? = null,
    val iconToken: IconToken? = null,
    val iconColor: ColorToken? = null,
    val typographyToken: TypographyToken? = null,
    val fontFamilyToken: FontFamilyToken? = null,
)
