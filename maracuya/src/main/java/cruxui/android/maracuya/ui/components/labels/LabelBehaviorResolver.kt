package cruxui.android.maracuya.ui.components.labels

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

