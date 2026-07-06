package cruxui.android.maracuya.wrappers.components.buttons.navigation.simple

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationLoadingScope
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationVariant

/**
 * Agrupa la configuracion inicial de `ButtonNavigationWrp`.
 *
 * Centraliza los valores leidos desde XML y los defaults equivalentes a la API
 * Compose de `ButtonNavigationMrcy`.
 */
internal data class ButtonNavigationAttributes(
    val label: String?,
    val modifier: Modifier = Modifier,
    val enabled: Boolean,
    val shape: Shape = RoundedCornerShape(100.dp),
    val interactionSource: MutableInteractionSource = MutableInteractionSource(),
    val variant: ButtonNavigationVariant,
    val buttonNavigationBehavior: ButtonNavigationBehavior,
    val buttonNavigationTokens: ButtonNavigationTokens? = null,
    val buttonNavigationTokensOverride: ButtonNavigationTokensOverride? = null,
    val buttonNavigationTokensOverrideName: String? = null,
    val onTrailingClick: (ButtonNavigationLoadingScope.() -> Unit)? = null,
)
