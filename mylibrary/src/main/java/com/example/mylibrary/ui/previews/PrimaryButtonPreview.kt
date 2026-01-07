package com.example.mylibrary.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mylibrary.ui.components.buttons.primary.PrimaryButtonMrcy


@Preview(name = "PrimaryButton – Default", showBackground = true)
@Composable
fun PrimaryButton_Default() {
    PreviewWrapper(style = Mode.current){
        PrimaryButtonMrcy(
            text = "Button",
            onClick = {},
        )
    }
}