package com.example.mylibrary.ui.previews


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mylibrary.ui.components.labels.LabelMrcy

@Preview(name = "Label – Default", showBackground = true)
@Composable
fun Label_Default() {
    PreviewWrapper(style = Mode.current){
        LabelMrcy(text = "Label", icon = false, error = false)

    }
}

@Preview(name = "Label – With Icon", showBackground = true)
@Composable
fun Label_With_Icon() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", icon = true, error = false)
    }
}


@Preview(name = "Label – With Optional Icon", showBackground = true)
@Composable
fun Label_With_Optional_Icon() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", optional = true, icon = true, error = false)
    }
}

@Preview(name = "Label – Error", showBackground = true)
@Composable
fun Label_Error() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", icon = true, error = true)
    }
}


@Preview(name = "Label – With Optional Icon Error", showBackground = true)
@Composable
fun Label_With_Optional_Icon_Error() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "Label", optional = true, icon = true, error = true)
    }
}


@Preview(name = "Label – With Large Text", showBackground = true)
@Composable
fun Label_With_Large_Text() {
    PreviewWrapper(style = Mode.current) {
        LabelMrcy(text = "This a label that could extend to more than one line")
    }
}