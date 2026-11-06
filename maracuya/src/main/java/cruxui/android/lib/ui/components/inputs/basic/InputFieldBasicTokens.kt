package cruxui.android.lib.ui.components.inputs.basic

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken

/**
 * Component Token Layer — InputFieldBasic
 *
 * - DS puro
 * - NO conoce Compose
 * - NO contiene lógica
 */
data class InputFieldBasicTokens(
    val borderDefault: ColorToken, // Borde gris por default
    val borderFocus: ColorToken, // Borde azulado selected
    val borderError: ColorToken, // Borde rojo de error

    val placeholderColor: ColorToken, // Placeholder gris
    val textColor: ColorToken, // Texto del usuario negro

    val backgroundDisabled: ColorToken, // Fondo del input gris oscuro

    val placeholderTypography: TypographyToken, // Tipografia del texto
    val textTypography: TypographyToken, // Tipografia del texto

    val fontFamilyToken: FontFamilyToken, // Fuente de letra

    val leadingIcon: IconToken? = null,
    val trailingIcon: IconToken? = null,
    val iconColor: ColorToken? = null,
    val implicitClearTrailingIcon: IconToken? = null,
)
