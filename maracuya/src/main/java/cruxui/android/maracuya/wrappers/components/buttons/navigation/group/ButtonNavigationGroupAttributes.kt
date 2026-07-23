package cruxui.android.maracuya.wrappers.components.buttons.navigation.group

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupDefaults
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant

/**
 * Agrupa la configuracion inicial de `ButtonNavigationGroupWrp`.
 *
 * Centraliza los valores leidos desde XML y conserva la relacion de defaults
 * de la API Compose: si un boton hijo no define enabled, hereda el enabled del grupo.
 */
internal data class ButtonNavigationGroupAttributes(
    val modifier: Modifier = Modifier,
    val variant: ButtonNavigationGroupVariant,
    val leadingLabel: String? = null,
    val trailingLabel: String? = null,
    val leadingButtonWidth: Dp = ButtonNavigationGroupDefaults.LeadingButtonWidth,
    val trailingButtonWidth: Dp = ButtonNavigationGroupDefaults.TrailingButtonWidth,
    val buttonSpacing: Dp = ButtonNavigationGroupDefaults.ButtonSpacing,
    val enabled: Boolean,
    val leadingEnabledOverride: Boolean? = null,
    val trailingEnabledOverride: Boolean? = null,
    val buttonNavigationGroupBehavior: ButtonNavigationGroupBehavior = ButtonNavigationGroupBehavior.Default,
)
