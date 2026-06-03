package cruxui.android.maracuya.ui.components.textfields

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import cruxui.android.maracuya.ui.components.inputs.basic.InputFieldBasicTokens

internal object TextFieldBehaviorResolver {

    fun resolve(
        variant: TextFieldVariant,
        baseInputTokens: InputFieldBasicTokens,
        textFieldTokens: TextFieldTokens,
        explicitTrailingAction: (() -> Unit)?,
        isPasswordVisible: Boolean,
        onPasswordVisibilityToggle: () -> Unit,
    ): TextFieldBehavior {
        if (variant != TextFieldVariant.PASSWORD) {
            return TextFieldBehavior(
                inputTokens = baseInputTokens,
                onTrailingIconClick = explicitTrailingAction,
                trailingIconContentDescription = null,
                keyboardTypeOverride = null,
                visualTransformation = VisualTransformation.None,
            )
        }

        val passwordIcon = if (isPasswordVisible) {
            textFieldTokens.passwordVisibleIcon
        } else {
            textFieldTokens.passwordHiddenIcon
        }

        val useImplicitPasswordBehavior = explicitTrailingAction == null &&
                textFieldTokens.passwordHiddenIcon != null &&
                textFieldTokens.passwordVisibleIcon != null

        return TextFieldBehavior(
            inputTokens = baseInputTokens.copy(trailingIcon = passwordIcon),
            onTrailingIconClick = explicitTrailingAction ?: if (useImplicitPasswordBehavior) {
                onPasswordVisibilityToggle
            } else {
                null
            },
            trailingIconContentDescription = if (isPasswordVisible) {
                "Ocultar contraseña"
            } else {
                "Mostrar contraseña"
            },
            keyboardTypeOverride = KeyboardType.Password,
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
        )
    }
}