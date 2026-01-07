package com.example.mylibrary.ui.components.inputs.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import com.example.mylibrary.tokens.spacings.InputFieldBasicSpacings
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter

/**
 * InputFieldBasicMrcy
 *
 * - UI pura
 * - Sin validaciones
 * - Sin lógica de negocio
 * - Reacciona SOLO a estado visual
 */
@Composable
fun InputFieldBasicMrcy(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    inputType: InputFieldBasicType = InputFieldBasicType.TEXT,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    hasError: Boolean = false,
    inputFieldBasicTokens: InputFieldBasicTokens? = null
) {

    val tokens = InputFieldBasicTokensResolver.resolve(inputFieldBasicTokens)

    /**
     * Fuente única de verdad para foco
     */
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    /**
     * Conversión px → dp (solo en capa Compose)
     */
    val density = LocalDensity.current
    val widthDp = with(density) { InputFieldBasicSpacings.WidthInput.toDp() }
    val minHeightDp = with(density) { InputFieldBasicSpacings.HugInput.toDp() }

    // Color del borde según estado
    val borderColor = when {
        hasError -> tokens.borderError
        isFocused -> tokens.borderFocus
        else -> tokens.borderDefault
    }

    // Grosor del borde
    val borderWidth =
        if (isFocused) InputFieldBasicSpacings.BorderWidthFocused
        else InputFieldBasicSpacings.BorderWidthIdle

    // Fondo (solo disabled)
    val backgroundColor =
        if (!enabled)
            ColorComposeAdapter.toComposeColor(tokens.backgroundDisabled)
        else
            Color.Transparent

    // Teclado
    val keyboardOptions = KeyboardOptions(
        keyboardType = when (inputType) {
            InputFieldBasicType.TEXT -> KeyboardType.Text
            InputFieldBasicType.NUMBER -> KeyboardType.Number
        }
    )

    Box(
        modifier = modifier
            .width(widthDp)
            .heightIn(min = minHeightDp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(InputFieldBasicSpacings.CornerRadius.dp)
            )
            .then(
                if (!readOnly)
                    Modifier.border(
                        width = borderWidth.dp,
                        color = ColorComposeAdapter.toComposeColor(borderColor),
                        shape = RoundedCornerShape(InputFieldBasicSpacings.CornerRadius.dp)
                    )
                else Modifier
            )
            .padding(
                horizontal = InputFieldBasicSpacings.HorizontalPadding.dp,
                vertical = InputFieldBasicSpacings.VerticalPadding.dp,

            )
    ) {

        BasicTextField(
            value = value,
            onValueChange = {
                val filteredValue = filterInputByType(it, inputType)
                onValueChange(filteredValue)
            },
            enabled = enabled,
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            maxLines = 1,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(
                ColorComposeAdapter.toComposeColor(tokens.textColor)
            ),
            textStyle = TypographyComposeAdapter
                .toTextStyle(
                    tokens.textTypography,
                    FontFamiliesComposeAdapter.toCompose(tokens.fontFamilyToken)
                )
                .copy(
                    color = if (readOnly)
                        ColorComposeAdapter.toComposeColor(tokens.placeholderColor)
                    else
                        ColorComposeAdapter.toComposeColor(tokens.textColor)
                ),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = TypographyComposeAdapter.toTextStyle(
                            tokens.placeholderTypography,
                            FontFamiliesComposeAdapter.toCompose(tokens.fontFamilyToken)
                        ),
                        color = ColorComposeAdapter.toComposeColor(tokens.placeholderColor)
                    )
                }
                innerTextField()
            }
        )
    }
}

private fun filterInputByType(
    input: String,
    type: InputFieldBasicType
): String {
    return when (type) {
        InputFieldBasicType.TEXT -> input
        InputFieldBasicType.NUMBER -> input.filter { it.isDigit() }
    }
}

