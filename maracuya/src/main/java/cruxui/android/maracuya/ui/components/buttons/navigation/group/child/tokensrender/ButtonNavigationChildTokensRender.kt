package cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokensrender

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant

/**
 * Especificacion final de un `ButtonNavigationMrcy` hijo.
 */
internal data class ButtonNavigationChildRender(
    val modifier: Modifier,
    val label: String?,
    val onClick: () -> Unit,
    val enabled: Boolean,
    val width: Dp,
    val minWidth: Dp,
    val shape: Shape,
    val variant: ButtonNavigationVariant,
    val buttonNavigationBehavior: ButtonNavigationBehavior,
    val nativeTokensOverride: ButtonNavigationTokensOverride?,
)
