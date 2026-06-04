package cruxui.android.maracuya.wrappers.components.buttons.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokens
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokensOverride
import cruxui.android.maracuya.ui.components.buttons.button.ButtonVariant

/**
 * Agrupa los valores de configuración del wrapper `ButtonWrp` obtenidos desde XML
 * o defaults internos, manteniendo paridad con la API Compose de `ButtonMrcy`.
 */
internal data class ButtonAttributes(
    val text: String,
    val modifier: Modifier = Modifier,
    val enabled: Boolean,
    val shape: Shape = RoundedCornerShape(100.dp),
    val interactionSource: MutableInteractionSource = MutableInteractionSource(),
    val buttonVariant: ButtonVariant,
    val buttonTokens: ButtonTokens? = null,
    val buttonTokensOverride: ButtonTokensOverride? = null,
    val buttonTokensOverrideName: String? = null,
    val showProgressOnPress: Boolean,
)
