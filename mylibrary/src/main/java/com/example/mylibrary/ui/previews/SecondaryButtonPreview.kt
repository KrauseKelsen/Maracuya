package com.example.mylibrary.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mylibrary.ui.components.buttons.secondary.SecondaryButtonMrcy


@Preview(name = "SecondaryButton – Default", showBackground = true)
@Composable
fun SecondaryButton_Default() {
    PreviewWrapper(style = Mode.current){
        SecondaryButtonMrcy(
            text = "Button",
            onClick = {},
            enabled = false
        )
    }
}