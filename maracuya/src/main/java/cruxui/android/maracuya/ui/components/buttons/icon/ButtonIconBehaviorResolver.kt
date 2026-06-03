package cruxui.android.maracuya.ui.components.buttons.icon


import cruxui.android.maracuya.ui.components.utils.IconPosition

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