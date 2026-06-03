package cruxui.android.maracuya.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cruxui.android.maracuya.ui.components.buttons.secondary.SecondaryButtonMrcy
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper


@Preview(name = "SecondaryButton – Default", showBackground = true)
@Composable
fun SecondaryButton_Default() {
    PreviewWrapper(style = Mode.current) {
        SecondaryButtonMrcy(
            text = "Button",
            onClick = {},
            enabled = false
        )
    }
}