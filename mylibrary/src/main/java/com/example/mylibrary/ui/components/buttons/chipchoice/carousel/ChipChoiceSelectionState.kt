package com.example.mylibrary.ui.components.buttons.chipchoice.carousel

import androidx.compose.runtime.Immutable

@Immutable
data class ChipChoiceSelection(
    val selectedIds: Set<String> = emptySet()
)

internal object ChipChoiceSelectionReducer {

    fun reduce(
        current: ChipChoiceSelection,
        clickedId: String,
        mode: ChipChoiceSelectionMode,
        allowEmptySelection: Boolean
    ): ChipChoiceSelection {
        if (mode == ChipChoiceSelectionMode.NONE) return current

        return when (mode) {
            ChipChoiceSelectionMode.SINGLE -> {
                val alreadySelected = current.selectedIds.contains(clickedId)
                val next = when {
                    alreadySelected && allowEmptySelection -> emptySet()
                    else -> setOf(clickedId)
                }
                current.copy(selectedIds = next)
            }

            ChipChoiceSelectionMode.MULTI -> {
                val next = current.selectedIds.toMutableSet()
                if (next.contains(clickedId)) {
                    if (allowEmptySelection || next.size > 1) next.remove(clickedId)
                } else {
                    next.add(clickedId)
                }
                current.copy(selectedIds = next)
            }

            ChipChoiceSelectionMode.NONE -> current
        }
    }
}