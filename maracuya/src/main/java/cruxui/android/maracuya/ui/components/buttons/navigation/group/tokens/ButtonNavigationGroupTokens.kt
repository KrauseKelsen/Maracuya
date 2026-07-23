package cruxui.android.maracuya.ui.components.buttons.navigation.group.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupDefaults

/**
 * Declara los tokens estructurales del grupo de navegacion.
 *
 * Estos tokens no reemplazan los tokens visuales de `ButtonNavigationMrcy`; solo
 * describen medidas, separacion y radio que el grupo necesita para organizar hijos.
 */
data class ButtonNavigationGroupTokens(
    val buttonSpacing: Dp = ButtonNavigationGroupDefaults.ButtonSpacing,
    val leadingButtonWidth: Dp = ButtonNavigationGroupDefaults.LeadingButtonWidth,
    val trailingButtonWidth: Dp = ButtonNavigationGroupDefaults.TrailingButtonWidth,
    val minLeadingButtonWidth: Dp = ButtonNavigationGroupDefaults.MinLeadingButtonWidth,
    val minTrailingButtonWidth: Dp = ButtonNavigationGroupDefaults.MinTrailingButtonWidth,
    val cornerRadius: Dp = ButtonNavigationGroupDefaults.CornerRadius,
)

/**
 * Tokens estructurales ya resueltos y listos para combinarlos con variant/behavior.
 */
internal data class ButtonNavigationGroupResolvedTokens(
    val buttonSpacing: Dp,
    val hasExplicitButtonSpacing: Boolean,
    val leadingButtonWidth: Dp,
    val trailingButtonWidth: Dp,
    val minLeadingButtonWidth: Dp,
    val minTrailingButtonWidth: Dp,
    val cornerRadius: Dp,
    val submitIconToken: IconToken,
) {
    val hasSeparatedButtons: Boolean
        get() = hasExplicitButtonSpacing && buttonSpacing > 0.dp
}
