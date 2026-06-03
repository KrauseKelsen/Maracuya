package cruxui.android.maracuya.ui.components.buttons.icon

import cruxui.android.maracuya.ui.components.utils.IconPosition

internal data class ButtonIconBehavior(
    val iconPosition: IconPosition?,
    val onTrailingClick: (() -> Unit)?,
)