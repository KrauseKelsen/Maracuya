package cruxui.android.maracuya.ui.utils.compose

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.*

/** Estado pressed derivado de InteractionSource (reutilizable en cualquier componente). */
@Composable
fun InteractionSource.collectPressedAsState(): State<Boolean> {
    val pressed = remember { mutableStateOf(false) }

    LaunchedEffect(this) {
        interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> pressed.value = true
                is PressInteraction.Release,
                is PressInteraction.Cancel -> pressed.value = false
            }
        }
    }
    return pressed
}