package cruxui.android.lib.ui.components.labels.behavior

internal data class LabelBehavior (
    val onClick: (() -> Unit)?,
    val onClickContentDescription: String?,
)