package cruxui.android.lib.ui.components.labels.tokens

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken

/**
 * Component Token Layer (DS puro)
 *
 * - NO conoce Compose
 * - NO conoce LocalComposition
 * - NO conoce MaterialTheme
 */
data class LabelTokens(
    val foregroundDefault: ColorToken,
    val foregroundError: ColorToken,
    val labelTypography: TypographyToken,
    val optionalTypography: TypographyToken,
    val infoIcon: IconToken,
    val fontFamily: FontFamilyToken
)

data class LabelTokensOverride(
    val foregroundDefault: ColorToken? = null,
    val foregroundError: ColorToken? = null,
    val labelTypography: TypographyToken? = null,
    val optionalTypography: TypographyToken? = null,
    val infoIcon: IconToken? = null,
    val fontFamily: FontFamilyToken? = null
)