package cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokensrender

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.tokens.base.TypographyToken

/**
 * Tokens finales usados por el render despues de resolver estado visual.
 */
internal data class ButtonNavigationTokensRender(
    val containerColor: ColorToken,
    val contentColor: ColorToken,
    val disabledContainerColor: ColorToken,
    val disabledContentColor: ColorToken,
    val borderColor: ColorToken,
    val iconColor: ColorToken,
    val iconToken: IconToken?,
    val typographyToken: TypographyToken,
    val fontFamilyToken: FontFamilyToken,
)
