package cruxui.android.maracuya.ui.components.buttons

import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.ui.components.utils.IconPosition

/**
 * ButtonIconTokens: tokens específicos para botones con icono.
 * Define:
 * - icono a renderizar
 * - color del icono
 * - posición del icono (inicio o fin)
 * - tipografía y color del texto
 */
data class ButtonIconTokens(
    val icon: IconToken,
    val iconTint: ColorToken,
    val textTypography: TypographyToken,
    val textColor: ColorToken,
    val iconPosition: IconPosition = IconPosition.START
)
