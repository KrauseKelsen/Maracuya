package com.example.mylibrary.ui.components.textfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicMrcy
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicType
import com.example.mylibrary.ui.components.inputs.config.InputFieldBasicTokensResolver
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter

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
            modifier = Modifier.padding(top = 8.dp),
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

@Composable
fun TextFieldMrcy(
    value: List<String>,
    onValueChange: (List<String>) -> Unit,
    label: String,
    length: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    hasError: Boolean = false,
    optionalText: Boolean = false,
    showLabelIcon: Boolean = false,
    bottomText: String? = null,
    showBottomIcon: Boolean = false,
    textFieldVariant: TextFieldVariant = TextFieldVariant.PIN,
    pinIconToken: IconToken? = null,
    textFieldTokens: TextFieldTokens? = null,
) {
    require(length == 4 || length == 6) { "PIN length must be 4 or 6" }
    require(textFieldVariant == TextFieldVariant.PIN) {
        "TextFieldMrcy(value = List<String>) only supports TextFieldVariant.PIN"
    }

    val normalizedPin = remember(value, length) {
        value.take(length).map { it.takeLast(1).filter(Char::isDigit) } +
            List((length - value.size).coerceAtLeast(0)) { "" }
    }

    val resolver = TextFieldTokensResolver.resolve(
        variant = textFieldVariant,
        override = textFieldTokens,
    )

    var isPinVisible by remember { mutableStateOf(false) }

    val inputTokens = InputFieldBasicTokensResolver.resolve(
        group = resolver.inputFieldTokenGroup,
    )

    val trailingHiddenIcon = pinIconToken ?: resolver.passwordHiddenIcon
    val trailingVisibleIcon = resolver.passwordVisibleIcon ?: trailingHiddenIcon

    Column(modifier = modifier) {
        LabelMrcy(
            text = label,
            optionalText = optionalText,
            showIcon = showLabelIcon,
            error = hasError
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(length) { index ->
                val digit = normalizedPin[index]

                InputFieldBasicMrcy(
                    modifier = Modifier.weight(1f),
                    value = digit,
                    placeholder = "",
                    onValueChange = { newValue ->
                        if (!enabled || readOnly) return@InputFieldBasicMrcy

                        val filtered = newValue.filter(Char::isDigit)
                        val next = normalizedPin.toMutableList()

                        if (filtered.isEmpty()) {
                            next[index] = ""
                        } else {
                            next[index] = filtered.last().toString()
                        }

                        onValueChange(next)
                    },
                    inputType = InputFieldBasicType.NUMBER,
                    enabled = enabled,
                    readOnly = readOnly,
                    hasError = hasError,
                    keyboardTypeOverride = KeyboardType.NumberPassword,
                    visualTransformation = if (isPinVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    enableImplicitTrailingClear = false,
                    inputFieldBasicTokens = inputTokens,
                    expandToContainerWidth = true,
                )
            }

            if (trailingHiddenIcon != null || trailingVisibleIcon != null) {
                PinVisibilityIcon(
                    isVisible = isPinVisible,
                    onClick = { isPinVisible = !isPinVisible },
                    hiddenIcon = trailingHiddenIcon,
                    visibleIcon = trailingVisibleIcon,
                    iconColor = inputTokens.iconColor ?: inputTokens.placeholderColor,
                    enabled = enabled && !readOnly,
                )
            }
        }

        if (bottomText != null) {
            BottomTextMrcy(
                text = bottomText,
                hasError = hasError,
                showBottomIcon = showBottomIcon,
                textFieldTokens = resolver,
            )
        }
    }
}

@Composable
private fun PinVisibilityIcon(
    isVisible: Boolean,
    onClick: () -> Unit,
    hiddenIcon: IconToken?,
    visibleIcon: IconToken?,
    iconColor: com.example.mylibrary.tokens.base.ColorToken,
    enabled: Boolean,
) {
    val icon = if (isVisible) {
        visibleIcon ?: hiddenIcon
    } else {
        hiddenIcon ?: visibleIcon
    } ?: return

    IconComposeAdapter.Render(
        icon = icon,
        fillColor = ColorComposeAdapter.toComposeColor(iconColor),
        modifier = Modifier
            .size(24.dp)
            .then(
                if (enabled) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        contentDescription = if (isVisible) {
            "Ocultar PIN"
        } else {
            "Mostrar PIN"
        },
    )
}
