package com.example.mylibrary.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicMrcy
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicTokenGroup
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicTokensResolver
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicType
import com.example.mylibrary.ui.previews.core.Mode
import com.example.mylibrary.ui.previews.core.PreviewWrapper


@Preview(name = "InputFieldBasic – Default", showBackground = true)
@Composable
fun InputFieldBasic_Default() {
    var name by remember { mutableStateOf("") }
    PreviewWrapper(style = Mode.current) {
        InputFieldBasicMrcy(
            value = name,
            placeholder = "Placeholder",
            onValueChange = { name = it }
        )
    }
}


@Preview(name = "InputFieldBasic – Error", showBackground = true)
@Composable
fun InputFieldBasic_Error() {
    var name by remember { mutableStateOf("") }
    PreviewWrapper(style = Mode.current) {
        InputFieldBasicMrcy(
            value = name,
            placeholder = "Placeholder",
            onValueChange = { name = it },
            hasError = true
        )
    }
}


@Preview(name = "InputFieldBasic – Disabled", showBackground = true)
@Composable
fun InputFieldBasic_Disabled() {
    var name by remember { mutableStateOf("") }
    PreviewWrapper(style = Mode.current) {
        InputFieldBasicMrcy(
            value = name,
            placeholder = "Placeholder",
            onValueChange = { name = it },
            enabled = false
        )
    }
}


@Preview(name = "InputFieldBasic – Readonly", showBackground = true)
@Composable
fun InputFieldBasic_Readonly() {
    var name by remember { mutableStateOf("") }
    PreviewWrapper(style = Mode.current) {
        InputFieldBasicMrcy(
            value = name,
            placeholder = "Placeholder",
            onValueChange = { name = it },
            readOnly = true
        )
    }
}


@Preview(name = "InputFieldBasic – Numeric", showBackground = true)
@Composable
fun InputFieldBasic_Numeric() {
    var age by remember { mutableStateOf("") }
    PreviewWrapper(style = Mode.current) {
        InputFieldBasicMrcy(
            value = age,
            placeholder = "Placeholder",
            onValueChange = { age = it },
            inputType = InputFieldBasicType.NUMBER
        )
    }
}

@Preview(name = "InputFieldBasic – Password", showBackground = true)
@Composable
fun InputFieldBasic_Password() {
    var value by remember { mutableStateOf("") }
    PreviewWrapper(style = Mode.current) {
        InputFieldBasicMrcy(
            value = value,
            placeholder = "Password",
            onValueChange = { value = it },
            inputFieldBasicTokens = InputFieldBasicTokensResolver.resolve(
                group = InputFieldBasicTokenGroup.LEADING_KEY_TRAILING_VISIBILITY
            )
        )
    }
}

@Preview(name = "InputFieldBasic – User With Clear", showBackground = true)
@Composable
fun InputFieldBasic_UserWithClear() {
    var value by remember { mutableStateOf("") }
    PreviewWrapper(style = Mode.current) {
        InputFieldBasicMrcy(
            value = value,
            placeholder = "Placeholder",
            onValueChange = { value = it },
            inputFieldBasicTokens = InputFieldBasicTokensResolver.resolve(
                group = InputFieldBasicTokenGroup.LEADING_USER_TRAILING_CLEAR
            )
        )
    }
}

@Preview(name = "InputFieldBasic – User (Example Usage)", showBackground = true)
@Composable
fun InputFieldBasic_User_ExampleUsage() {
    var value by remember { mutableStateOf("Daviuser1234") }
    PreviewWrapper(style = Mode.current) {
        InputFieldBasicMrcy(
            value = value,
            placeholder = "Usuario",
            onValueChange = { value = it },
            inputFieldBasicTokens = InputFieldBasicTokensResolver.resolve(
                group = InputFieldBasicTokenGroup.LEADING_USER
            )
        )
    }
}

@Preview(name = "InputFieldBasic – Face ID (Example Usage)", showBackground = true)
@Composable
fun InputFieldBasic_FaceId_ExampleUsage() {
    var value by remember { mutableStateOf("Daviuser1234") }
    PreviewWrapper(style = Mode.current) {
        InputFieldBasicMrcy(
            value = value,
            placeholder = "Usuario",
            onValueChange = { value = it },
            inputFieldBasicTokens = InputFieldBasicTokensResolver.resolve(
                group = InputFieldBasicTokenGroup.TRAILING_FACE_ID
            )
        )
    }
}
