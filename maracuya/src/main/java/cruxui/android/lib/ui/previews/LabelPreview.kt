package cruxui.android.lib.ui.previews

import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import cruxui.android.lib.R
import cruxui.android.lib.ui.components.labels.LabelMrcy
import cruxui.android.lib.ui.previews.core.Mode
import cruxui.android.lib.ui.previews.core.PreviewWrapper

@Preview(name = "Label – Default", showBackground = true)
@Composable
fun Label_Default() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", showIcon = false, error = false)
    }
}

@Preview(name = "Label – With Icon", showBackground = true)
@Composable
fun Label_With_Icon() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", showIcon = true, error = false)
    }
}

@Preview(name = "Label – With Optional Icon", showBackground = true)
@Composable
fun Label_With_Optional_Icon() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", optionalText = true, showIcon = true, error = false)
    }
}

@Preview(name = "Label – Error", showBackground = true)
@Composable
fun Label_Error() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", showIcon = true, error = true)
    }
}

@Preview(name = "Label – With Optional Icon Error", showBackground = true)
@Composable
fun Label_With_Optional_Icon_Error() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", optionalText = true, showIcon = true, error = true)
    }
}

@Preview(name = "Label – With Large Text", showBackground = true)
@Composable
fun Label_With_Large_Text() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "This a label that could extend to more than one line")
    }
}

@Preview(name = "TextView XML - Native variants and states", showBackground = true)
@Composable
fun TextView_XmlPreview() {
    AndroidView(factory = { context ->
        LayoutInflater.from(context).inflate(R.layout.text_view_preview, FrameLayout(context), false)
    })
}
