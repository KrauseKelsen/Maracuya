package cruxui.android.maracuya.ui.components.buttons.navigation.group.core

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.core.ButtonNavigationLoadingScope
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant

/**
 * Declara un boton nativo que puede ser unido por `ButtonNavigationGroupMrcy`.
 *
 * El grupo conserva la funcionalidad de `ButtonNavigationMrcy`: variante, behavior,
 * tokens base, overrides y callbacks de loading. La forma final puede ser ajustada
 * por el grupo cuando los botones se muestran unidos sin separacion.
 */
data class ButtonNavigationGroupButton(
    val modifier: Modifier = Modifier,
    val label: String? = null,
    val onClick: () -> Unit = {},
    val enabled: Boolean = true,
    val width: Dp = Dp.Unspecified,
    val minWidth: Dp = Dp.Unspecified,
    val shape: Shape = RoundedCornerShape(ButtonNavigationGroupDefaults.CornerRadius),
    val variant: ButtonNavigationVariant = ButtonNavigationVariant.PRIMARY,
    val buttonNavigationBehavior: ButtonNavigationBehavior = ButtonNavigationBehavior.Default,
    val buttonNavigationTokens: ButtonNavigationTokens? = null,
    val buttonNavigationTokensOverride: ButtonNavigationTokensOverride? = null,
    val onTrailingClick: (ButtonNavigationLoadingScope.() -> Unit)? = null,
    val onLoadingClick: (ButtonNavigationLoadingScope.() -> Unit)? = null,
)
