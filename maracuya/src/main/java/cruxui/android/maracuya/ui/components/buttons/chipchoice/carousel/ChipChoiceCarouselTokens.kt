package cruxui.android.maracuya.ui.components.buttons.chipchoice.carousel

import androidx.compose.runtime.Immutable
import cruxui.android.maracuya.ui.components.buttons.chipchoice.ChipChoiceBaseTokens

@Immutable
data class ChipChoiceCarouselTokens(
    /**
     * Tokens del chip cuando NO está seleccionado.
     */
    val unselected: ChipChoiceBaseTokens,

    /**
     * Tokens del chip cuando está seleccionado.
     */
    val selected: ChipChoiceBaseTokens
)