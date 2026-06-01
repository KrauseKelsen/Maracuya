package com.example.mylibrary.ui.components.buttons.chipchoice.carousel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylibrary.tokens.spacings.ChipChoiceSpacings
import com.example.mylibrary.ui.components.buttons.chipchoice.ChipChoiceBaseMrcy
import com.example.mylibrary.ui.components.buttons.chipchoice.ChipChoiceBaseTokens
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.ui.components.textfields.BottomTextMrcy
import com.example.mylibrary.ui.components.textfields.TextFieldTokens
import com.example.mylibrary.ui.components.textfields.TextFieldTokensResolver
import com.example.mylibrary.ui.components.textfields.TextFieldVariant

@Composable
fun ChipChoiceCarouselMrcy(
    labelText: String,
    items: List<ChipChoiceItem>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,

    hasError: Boolean = false,
    optionalText: Boolean = false,
    showLabelIcon: Boolean = false,

    bottomText: String? = null,

    selectionMode: ChipChoiceSelectionMode = ChipChoiceSelectionMode.MULTI,
    allowEmptySelection: Boolean = true,

    /**
     * State hoisting (controlado).
     * Si ambos vienen != null, se usa modo controlado.
     */
    selectedIds: Set<String>? = null,
    onSelectedIdsChange: ((Set<String>) -> Unit)? = null,

    /**
     * Layout
     */
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp),

    /**
     * Overrides de tokens:
     * - carouselTokens: override completo (selected+unselected)
     * - selectedTokensOverride/unselectedTokensOverride: overrides granulares por estado
     */
    carouselTokens: ChipChoiceCarouselTokens? = null,
    textFieldTokens: TextFieldTokens? = null, // estos tokens estan amarrados, deben solucionarse en BottomTextTokensResolver
    selectedTokensOverride: ChipChoiceBaseTokens? = null,
    unselectedTokensOverride: ChipChoiceBaseTokens? = null,
) {
    val itemSpacingDp = ChipChoiceSpacings.itemBetweenSpacing
    val labelHeightDp = ChipChoiceSpacings.labelHeightSpacing

    val isControlled = selectedIds != null && onSelectedIdsChange != null

    var internalSelected by rememberSaveable { mutableStateOf<Set<String>>(emptySet()) }
    val currentSelected = if (isControlled) selectedIds else internalSelected

    fun updateSelected(next: Set<String>) {
        if (isControlled) onSelectedIdsChange(next) else internalSelected = next
    }

    val resolvedCarouselTokens = ChipChoiceCarouselTokensResolver.resolve(carouselTokens)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(labelHeightDp.dp),
    ){
        // ───────── Label ─────────
        LabelMrcy(
            text = labelText,
            optionalText = optionalText,
            showIcon = showLabelIcon,
            error = hasError
        )

        LazyRow(
            modifier = modifier.fillMaxWidth(),
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(itemSpacingDp.dp)
        ) {
            items(
                items = items,
                key = { it.id }
            ) { item ->
                val itemEnabled = enabled && item.enabled
                val isSelected = currentSelected.contains(item.id)

                val tokensForThisChip = resolveChipTokens(
                    itemOverride = item.tokensOverride,
                    isSelected = isSelected,
                    resolvedCarouselTokens = resolvedCarouselTokens,
                    selectedTokensOverride = selectedTokensOverride,
                    unselectedTokensOverride = unselectedTokensOverride
                )

                ChipChoiceBaseMrcy(
                    text = item.label,
                    enabled = itemEnabled,
                    chipTokens = tokensForThisChip,
                    onClick = {
                        if (!itemEnabled) return@ChipChoiceBaseMrcy

                        val next = ChipChoiceSelectionReducer.reduce(
                            current = ChipChoiceSelection(currentSelected),
                            clickedId = item.id,
                            mode = selectionMode,
                            allowEmptySelection = allowEmptySelection
                        ).selectedIds

                        updateSelected(next)
                    }
                )
            }
        }

        val resolver = TextFieldTokensResolver.resolve(TextFieldVariant.DEFAULT)

        // ───────── Bottom text ─────────
        if (bottomText != null) {

            BottomTextMrcy(
                text = bottomText,
                hasError = hasError,
                textFieldTokens = resolver
            )
        }

    }
}

private fun resolveChipTokens(
    itemOverride: ChipChoiceBaseTokens?,
    isSelected: Boolean,
    resolvedCarouselTokens: ChipChoiceCarouselTokens,
    selectedTokensOverride: ChipChoiceBaseTokens?,
    unselectedTokensOverride: ChipChoiceBaseTokens?
): ChipChoiceBaseTokens {
    // 1) override por item
    itemOverride?.let { return it }

    // 2) override granular por estado
    if (isSelected) {
        selectedTokensOverride?.let { return it }
    } else {
        unselectedTokensOverride?.let { return it }
    }

    // 3) tokens del carrusel (resueltos por resolver)
    return if (isSelected) resolvedCarouselTokens.selected else resolvedCarouselTokens.unselected
}