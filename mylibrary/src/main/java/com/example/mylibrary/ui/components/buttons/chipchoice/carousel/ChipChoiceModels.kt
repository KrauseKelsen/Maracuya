package com.example.mylibrary.ui.components.buttons.chipchoice.carousel

import androidx.compose.runtime.Immutable
import com.example.mylibrary.ui.components.buttons.chipchoice.ChipChoiceBaseTokens

@Immutable
data class ChipChoiceItem(
    val id: String,
    val label: String,
    val enabled: Boolean = true,

    /**
     * Override opcional por item.
     * Útil si un chip debe tener tokens únicos.
     * Prioridad más alta.
     */
    val tokensOverride: ChipChoiceBaseTokens? = null
)

enum class ChipChoiceSelectionMode {
    NONE,
    SINGLE,
    MULTI
}
