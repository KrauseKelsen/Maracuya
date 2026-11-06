package cruxui.android.lib.ui.components.labels.behavior

internal object  LabelBehaviorResolver {
    fun resolve(
        onClick: (() -> Unit)?,
        onClickContentDescription: String?
    ): LabelBehavior{
        return LabelBehavior(
            onClick = onClick,
            onClickContentDescription = onClickContentDescription
        )
    }
}

