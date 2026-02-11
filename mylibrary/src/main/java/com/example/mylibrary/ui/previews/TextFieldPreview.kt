package com.example.mylibrary.ui.previews

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.mylibrary.ui.components.textfields.TextFieldMrcy
import com.example.mylibrary.ui.previews.core.Mode
import com.example.mylibrary.ui.previews.core.PreviewWrapper

/* ────────────────────────────────────────────── */
/* Default                                        */
/* ────────────────────────────────────────────── */

@Preview(name = "TextField – Default", showBackground = true)
@Composable
fun TextField_Default() {
    var value by remember { mutableStateOf("") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = { value = it },
            label = "Label",
            placeholder = "Placeholder"
        )
    }
}

/* ────────────────────────────────────────────── */
/* With Value (focused / writing)                 */
/* ────────────────────────────────────────────── */

@Preview(name = "TextField – With Value", showBackground = true)
@Composable
fun TextField_With_Value() {
    var value by remember { mutableStateOf("Angela Dariela") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = { value = it },
            label = "Label",
            placeholder = "Placeholder"
        )
    }
}

/* ────────────────────────────────────────────── */
/* Error                                          */
/* ────────────────────────────────────────────── */

@Preview(name = "TextField – Error", showBackground = true)
@Composable
fun TextField_Error() {
    var value by remember { mutableStateOf("") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = { value = it },
            label = "Label",
            placeholder = "Placeholder",
            hasError = true,
            bottomText = "Field-specific error message",
            showBottomIcon = true
        )
    }
}

/* ────────────────────────────────────────────── */
/* Disabled                                       */
/* ────────────────────────────────────────────── */

@Preview(name = "TextField – Disabled", showBackground = true)
@Composable
fun TextField_Disabled() {
    var value by remember { mutableStateOf("") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = {},
            label = "Label",
            placeholder = "Placeholder",
            enabled = false,
            bottomText = "Disabled field"
        )
    }
}

/* ────────────────────────────────────────────── */
/* Readonly                                       */
/* ────────────────────────────────────────────── */

@Preview(name = "TextField – Readonly", showBackground = true)
@Composable
fun TextField_Readonly() {
    var value by remember { mutableStateOf("Readonly value") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = {},
            label = "Label",
            placeholder = "Placeholder",
            readOnly = true,
            bottomText = "Readonly information"
        )
    }
}

/* ────────────────────────────────────────────── */
/* Optional + Label Icon                          */
/* ────────────────────────────────────────────── */

@Preview(name = "TextField – Optional With Label Icon", showBackground = true)
@Composable
fun TextField_Optional_With_Label_Icon() {
    var value by remember { mutableStateOf("") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = { value = it },
            label = "Label",
            placeholder = "Placeholder",
            optionalText = true,
            showLabelIcon = true,
            bottomText = "Supplementary text"
        )
    }
}

/* ────────────────────────────────────────────── */
/* Bottom helper with icon                        */
/* ────────────────────────────────────────────── */

@Preview(name = "TextField – Helper With Icon", showBackground = true)
@Composable
fun TextField_Helper_With_Icon() {
    var value by remember { mutableStateOf("") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = { value = it },
            label = "Label",
            placeholder = "Placeholder",
            bottomText = "Supplementary text",
            showBottomIcon = true
        )
    }
}

/* ────────────────────────────────────────────── */
/* Long label & bottom text                       */
/* ────────────────────────────────────────────── */

@Preview(name = "TextField – Long Texts", showBackground = true)
@Composable
fun TextField_Long_Texts() {
    var value by remember { mutableStateOf("") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = { value = it },
            label = "This is a label that can extend to more than one line",
            placeholder = "Placeholder",
            bottomText = "This is a helper text that explains the field in more detail"
        )
    }
}
