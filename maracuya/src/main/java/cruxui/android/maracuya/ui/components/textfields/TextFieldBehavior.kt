package cruxui.android.maracuya.ui.components.textfields

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import cruxui.android.maracuya.ui.components.inputs.basic.InputFieldBasicTokens

internal data class TextFieldBehavior(
    val inputTokens: InputFieldBasicTokens,
    val onTrailingIconClick: (() -> Unit)?,
    val trailingIconContentDescription: String?,
    val keyboardTypeOverride: KeyboardType?,
    val visualTransformation: VisualTransformation,
)