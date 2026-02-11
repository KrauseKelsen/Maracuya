package com.example.mylibrary.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mylibrary.ui.components.texts.LegalText
import com.example.mylibrary.ui.previews.core.Mode
import com.example.mylibrary.ui.previews.core.PreviewWrapper

@Preview(name = "LegalText – Default", showBackground = true)
@Composable
fun LegalText_Default() {
    PreviewWrapper(style = Mode.current) {
        LegalText(text = "Label")

    }
}