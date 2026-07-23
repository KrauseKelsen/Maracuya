package cruxui.android.maracuya.ui.previews

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.viewinterop.AndroidView
import cruxui.android.maracuya.R
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.ui.components.inputs.config.InputFieldBasicTokenGroup
import cruxui.android.maracuya.ui.components.textfields.TextFieldTokens
import cruxui.android.maracuya.ui.components.textfields.TextFieldMrcy
import cruxui.android.maracuya.ui.components.textfields.TextFieldVariant
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper
import cruxui.android.maracuya.wrappers.components.textfields.TextFieldTokensOverrideRegistry

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


@Preview(name = "TextField – User With Face ID", showBackground = true)
@Composable
fun TextField_User_With_Face_Id() {
    var value by remember { mutableStateOf("Daviuser1234") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = { value = it },
            label = "Usuario",
            placeholder = "Placeholder",
            textFieldVariant = TextFieldVariant.FACE_ID,
            onTrailingIconClick = {
                // Ejemplo de integración: disparar flujo biométrico/Face ID
            }

        )
    }
}

@Preview(name = "TextField – Password", showBackground = true)
@Composable
fun TextField_Password() {
    var value by remember { mutableStateOf("••••••") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = { value = it },
            label = "Password",
            placeholder = "Password",
            textFieldVariant = TextFieldVariant.PASSWORD
        )
    }
}

@Preview(name = "TextField – User (Example Usage)", showBackground = true)
@Composable
fun TextField_User_ExampleUsage() {

    var value by remember { mutableStateOf("") }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = value,
            onValueChange = { value = it },
            label = "Usuario",
            placeholder = "Daviuser1234",
            textFieldVariant = TextFieldVariant.USER,
            bottomText = "Este ejemplo usa la variante USER"
        )
    }
}


@Preview(name = "TextField – PIN 4", showBackground = true)
@Composable
fun TextField_Pin_4() {
    var pinValues by remember { mutableStateOf(List(4) { "" }) }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = pinValues,
            onValueChange = { pinValues = it },
            label = "Código de seguridad",
            length = 4,
            textFieldVariant = TextFieldVariant.PIN,
        )
    }
}

@Preview(name = "TextField – PIN 4 Error", showBackground = true)
@Composable
fun TextField_Pin_4_Error() {
    var pinValues by remember { mutableStateOf(List(4) { "" }) }

    PreviewWrapper(style = Mode.current) {
        TextFieldMrcy(
            value = pinValues,
            onValueChange = { pinValues = it },
            label = "Código de seguridad",
            length = 4,
            textFieldVariant = TextFieldVariant.PIN,
            hasError = true,
            bottomText = "Field-specific error message",
            showBottomIcon = true,
        )
    }
}


private const val XML_PREVIEW_TOKENS_OVERRIDE = "mytokensoverride"

@Preview(name = "TextFieldWrp XML - Tokens override registry", showBackground = true)
@Composable
fun TextFieldWrp_XmlTokensOverridePreview() {
    PreviewWrapper(style = Mode.current) {
        TextFieldTokensOverrideRegistry.register(XML_PREVIEW_TOKENS_OVERRIDE) {
            colors, typography, icons, fontFamily ->
            TextFieldTokens(
                fontFamilyToken = fontFamily,
                bottomTextErrorColor = colors.fgError,
                bottomTextTypography = typography.bottomText,
                bottomTextColor = colors.fgDefault,
                bottomTextIcon = icons.alerts.error,
                inputFieldTokenGroup = InputFieldBasicTokenGroup.TRAILING_FACE_ID,
                passwordHiddenIcon = icons.general.visibility,
                passwordVisibleIcon = icons.general.visibilityOff,
            )
        }
        DisposableEffect(Unit) {
            onDispose {
                TextFieldTokensOverrideRegistry.unregister(XML_PREVIEW_TOKENS_OVERRIDE)
            }
        }

        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.textfield_wrp_preview, null)
            },
        )
    }
}
