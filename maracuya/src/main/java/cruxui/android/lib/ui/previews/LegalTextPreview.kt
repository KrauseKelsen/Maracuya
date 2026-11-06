package cruxui.android.lib.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cruxui.android.lib.ui.components.texts.LegalText
import cruxui.android.lib.ui.previews.core.Mode
import cruxui.android.lib.ui.previews.core.PreviewWrapper

@Preview(name = "LegalText – Default", showBackground = true)
@Composable
fun LegalText_Default() {
    PreviewWrapper(style = Mode.current) {
        LegalText(text = "Label")

    }
}