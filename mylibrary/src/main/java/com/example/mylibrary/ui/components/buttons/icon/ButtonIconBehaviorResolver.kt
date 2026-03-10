package com.example.mylibrary.ui.components.buttons.icon


import com.example.mylibrary.ui.components.utils.IconPosition

internal object ButtonIconBehaviorResolver {

    fun resolve(
        variant: ButtonIconVariant?,
        onTrailingClick: (() -> Unit)?,
    ): ButtonIconBehavior {
        val iconPosition = when (variant) {
            ButtonIconVariant.IconRight -> IconPosition.END
            ButtonIconVariant.IconLeft -> IconPosition.START
            null -> null
        }

        return ButtonIconBehavior(
            iconPosition = iconPosition,
            onTrailingClick = onTrailingClick,
        )
    }
}