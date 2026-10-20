package cruxui.android.maracuya.ui.components.buttons.navigation.group.child.core

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokens.ButtonNavigationChildTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokens.ButtonNavigationChildTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.variant.ButtonNavigationChildVariant
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupDefaults

/**
 * Declara un hijo de navegacion que puede ser unido por `ButtonNavigationGroupMrcy`.
 *
 * El hijo solo admite variantes compatibles con el layout del grupo. Loading,
 * variantes square y callbacks de carga pertenecen al boton nativo o al grupo fijo,
 * pero no forman parte de esta API personalizada.
 */
data class ButtonNavigationChild(
    val modifier: Modifier = Modifier,
    val label: String? = null,
    val onClick: () -> Unit = {},
    val enabled: Boolean = true,
    val width: Dp = Dp.Unspecified,
    val minWidth: Dp = Dp.Unspecified,
    val shape: Shape = RoundedCornerShape(ButtonNavigationGroupDefaults.CornerRadius),
    val variant: ButtonNavigationChildVariant = ButtonNavigationChildVariant.PRIMARY,
    val showIcon: Boolean = true,
    val buttonNavigationChildTokens: ButtonNavigationChildTokens? = null,
    val buttonNavigationChildTokensOverride: ButtonNavigationChildTokensOverride? = null,
)
