package com.example.mylibrary.ui.components.buttons.icon

import com.example.mylibrary.ui.components.utils.IconPosition

internal data class ButtonIconBehavior(
    val iconPosition: IconPosition?,
    val onTrailingClick: (() -> Unit)?,
)