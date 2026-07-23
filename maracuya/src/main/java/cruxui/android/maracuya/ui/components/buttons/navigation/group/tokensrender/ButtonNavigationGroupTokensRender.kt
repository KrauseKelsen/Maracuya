package cruxui.android.maracuya.ui.components.buttons.navigation.group.tokensrender

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.core.ButtonNavigationLoadingScope
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant

/**
 * Tokens finales que necesita el render del grupo para pintar sus botones hijos.
 */
internal data class ButtonNavigationGroupTokensRender(
    val buttons: List<ButtonNavigationGroupButtonRender>,
    val buttonSpacing: Dp,
    val useMaxEdgeSeparation: Boolean,
)

/**
 * Especificacion final de un `ButtonNavigationMrcy` hijo.
 */
internal data class ButtonNavigationGroupButtonRender(
    val modifier: Modifier,
    val label: String?,
    val onClick: () -> Unit,
    val enabled: Boolean,
    val width: Dp,
    val minWidth: Dp,
    val shape: Shape,
    val variant: ButtonNavigationVariant,
    val buttonNavigationBehavior: ButtonNavigationBehavior,
    val buttonNavigationTokens: ButtonNavigationTokens?,
    val buttonNavigationTokensOverride: ButtonNavigationTokensOverride?,
    val onTrailingClick: (ButtonNavigationLoadingScope.() -> Unit)?,
    val onLoadingClick: (ButtonNavigationLoadingScope.() -> Unit)?,
)
