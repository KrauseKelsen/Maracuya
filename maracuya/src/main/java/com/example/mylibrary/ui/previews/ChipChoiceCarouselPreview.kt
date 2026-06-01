package com.example.mylibrary.ui.previews

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.mylibrary.ui.components.buttons.chipchoice.carousel.*
import com.example.mylibrary.ui.previews.core.Mode
import com.example.mylibrary.ui.previews.core.PreviewWrapper

private val baseItems = listOf(
    ChipChoiceItem(id = "1", label = "Item 1"),
    ChipChoiceItem(id = "2", label = "Item 2"),
    ChipChoiceItem(id = "3", label = "Item 3"),
)

private val overflowItems = (1..12).map {
    ChipChoiceItem(
        id = it.toString(),
        label = "Item $it"
    )
}

@Preview(name = "ChipCarousel – Default (Multi)", showBackground = true)
@Composable
fun ChipChoiceCarousel_Default() {
    PreviewWrapper(style = Mode.current) {
        ChipChoiceCarouselMrcy(
            labelText = "Items multiples",
            items = baseItems,

        )
    }
}

@Preview(name = "ChipCarousel – Single Selection", showBackground = true)
@Composable
fun ChipChoiceCarousel_Single() {
    PreviewWrapper(style = Mode.current) {
        ChipChoiceCarouselMrcy(
            labelText = "Items individuales",
            items = baseItems,
            optionalText = true,
            selectionMode = ChipChoiceSelectionMode.SINGLE
        )
    }
}

@Preview(name = "ChipCarousel – No Selection (Read Only)", showBackground = true)
@Composable
fun ChipChoiceCarousel_None() {
    PreviewWrapper(style = Mode.current) {
        ChipChoiceCarouselMrcy(
            labelText = "Items no seleccionables",
            items = baseItems,
            optionalText = true,
            showLabelIcon = true,
            selectionMode = ChipChoiceSelectionMode.NONE
        )
    }
}

@Preview(name = "ChipCarousel – With Disabled Items", showBackground = true)
@Composable
fun ChipChoiceCarousel_With_Disabled_Items() {
    PreviewWrapper(style = Mode.current) {
        ChipChoiceCarouselMrcy(
            labelText = "Items con deshabilitados",
            hasError = true,
            items = listOf(
                ChipChoiceItem(id = "1", label = "Item 1"),
                ChipChoiceItem(id = "2", label = "Item 2", enabled = false),
                ChipChoiceItem(id = "3", label = "Item 3")
            ),
            bottomText = "Field-specific error message",
        )
    }
}

@Preview(name = "ChipCarousel – Horizontal Scroll", showBackground = true)
@Composable
fun ChipChoiceCarousel_Overflow() {
    PreviewWrapper(style = Mode.current) {
        ChipChoiceCarouselMrcy(
            labelText = "Items con scroll horizontal",
            items = overflowItems
        )
    }
}


@Preview(name = "ChipCarousel – Controlled State", showBackground = true)
@Composable
fun ChipChoiceCarousel_Controlled() {
    PreviewWrapper(style = Mode.current) {
        var selected by remember { mutableStateOf(setOf("2")) }

        ChipChoiceCarouselMrcy(
            labelText = "Items controlados",
            items = baseItems,
            selectedIds = selected,
            onSelectedIdsChange = { selected = it }
        )
    }
}