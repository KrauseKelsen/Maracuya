package com.example.mylibrary.ui.components.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicMrcy
import com.example.mylibrary.ui.components.inputs.config.InputFieldBasicTokensResolver
import com.example.mylibrary.ui.components.labels.LabelMrcy

/**
 * Componente de alto nivel que orquesta:
 * - Label
 * - InputFieldBasic
 * - Bottom text
 *
 * Reglas de comportamiento por variante:
 * - La variante define comportamiento e íconos del trailing.
 * - El host puede sobreescribir acción con `onTrailingIconClick`.
 */
@Composable
fun TextFieldMrcy(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    hasError: Boolean = false,
    optionalText: Boolean = false,
    showLabelIcon: Boolean = false,
    bottomText: String? = null,
    showBottomIcon: Boolean = false,
    textFieldVariant: TextFieldVariant = TextFieldVariant.DEFAULT,
    onTrailingIconClick: (() -> Unit)? = null,
    enableImplicitTrailingClear: Boolean = true,
    textFieldTokens: TextFieldTokens? = null,
) {

    val resolver = TextFieldTokensResolver.resolve(
        variant = textFieldVariant,
        override = textFieldTokens,
    )

    var isPasswordVisible by remember(textFieldVariant) { mutableStateOf(false) }

    val inputTokens = InputFieldBasicTokensResolver.resolve(
        group = resolver.inputFieldTokenGroup,
    )

    val behavior = TextFieldBehaviorResolver.resolve(
        variant = textFieldVariant,
        baseInputTokens = inputTokens,
        textFieldTokens = resolver,
        explicitTrailingAction = onTrailingIconClick,
        isPasswordVisible = isPasswordVisible,
        onPasswordVisibilityToggle = { isPasswordVisible = !isPasswordVisible },
    )

    Column(
        modifier = modifier
    ) {

        LabelMrcy(
            text = label,
            optionalText = optionalText,
            showIcon = showLabelIcon,
            error = hasError
        )

        InputFieldBasicMrcy(
            value = value,
            placeholder = placeholder,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            hasError = hasError,
            onTrailingIconClick = behavior.onTrailingIconClick,
            trailingIconContentDescription = behavior.trailingIconContentDescription,
            keyboardTypeOverride = behavior.keyboardTypeOverride,
            visualTransformation = behavior.visualTransformation,
            enableImplicitTrailingClear = enableImplicitTrailingClear,
            //modifier = Modifier.padding(vertical = 6.dp),
            modifier = modifier.padding(top = 8.dp),
            inputFieldBasicTokens = behavior.inputTokens,
        )

        if (bottomText != null) {
            BottomTextMrcy(
                text = bottomText,
                hasError = hasError,
                showBottomIcon = showBottomIcon,
                textFieldTokens = resolver
            )
        }
    }
}
