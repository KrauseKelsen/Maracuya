package com.example.mylibrary.ui.components.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicMrcy
import com.example.mylibrary.ui.components.inputs.basic.InputFieldBasicTokensResolver
import com.example.mylibrary.ui.components.labels.LabelMrcy

/**
 * Componente de alto nivel que orquesta:
 * - Label
 * - InputFieldBasic
 * - Bottom text
 *
 * Reglas de ícono derecho:
 * - Si no hay trailing explícito, puede mostrarse clear implícito con `enableImplicitTrailingClear`.
 * - Por defecto, la variante `USER_WITH_CLEAR` limpia el texto al hacer tap.
 * - Para variantes con acción externa (ej: Face ID), se puede inyectar `onTrailingIconClick`.
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
            onTrailingIconClick = onTrailingIconClick,
            enableImplicitTrailingClear = enableImplicitTrailingClear,
            modifier = Modifier
                .padding(vertical = 6.dp),
            inputFieldBasicTokens = InputFieldBasicTokensResolver.resolve(
                group = resolver.inputFieldTokenGroup,
            )
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

