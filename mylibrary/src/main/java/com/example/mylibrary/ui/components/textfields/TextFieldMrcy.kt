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
 * Componente TextFieldMrcy
 *
 * Orquesta:
 * - LabelMrcy
 * - InputFieldBasicMrcy
 * - Bottom text
 *
 * No contiene estilos hardcodeados
 */

//TODO Falta asegurarse que la tipografia coincida correctamente en el textfield

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

    textFieldTokens: TextFieldTokens? = null,
) {

    val resolver = TextFieldTokensResolver.resolve(
        variant = textFieldVariant,
        override = textFieldTokens,
    )

    Column(
        modifier = modifier
    ) {

        // ───────── Label ─────────
        LabelMrcy(
            text = label,
            optionalText = optionalText,
            showIcon = showLabelIcon,
            error = hasError
        )


        // ───────── Input ─────────
        InputFieldBasicMrcy(
            value = value,
            placeholder = placeholder,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            hasError = hasError,
            modifier = Modifier
                .padding(vertical = 6.dp),
            inputFieldBasicTokens = InputFieldBasicTokensResolver.resolve(
                group = resolver.inputFieldTokenGroup,
            )
        )


        // ───────── Bottom text ─────────
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
