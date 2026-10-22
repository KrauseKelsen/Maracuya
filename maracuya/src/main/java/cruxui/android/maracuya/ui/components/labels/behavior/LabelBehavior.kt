package cruxui.android.maracuya.ui.components.labels.behavior

internal data class LabelBehavior (
    val onClick: (() -> Unit)?,
    val onClickContentDescription: String?,
)