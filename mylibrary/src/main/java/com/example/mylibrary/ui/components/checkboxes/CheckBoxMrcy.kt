package com.example.mylibrary.ui.components.checkboxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylibrary.tokens.spacings.LabelSpacings
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter

/**
 * Modelo para modo checkbox list.
 */
data class CheckBoxOption(
    val id: String,
    val label: String,
    val checked: Boolean = false,
    val enabled: Boolean = true,
)

/**
 * CheckBox compuesto en modo single checkbox.
 */
@Composable
fun CheckBoxMrcy(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkBoxText: String,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    optionalText: Boolean = false,
    showLabelIcon: Boolean = false,
    hasError: Boolean = false,
    bottomText: String? = null,
    showBottomIcon: Boolean = true,
    showInputBorder: Boolean = false,
    inputCheckBoxTokens: InputCheckBoxTokens? = null,
    checkBoxTokens: CheckBoxTokens? = null,
) {
    val resolvedTokens = CheckBoxTokensResolver.resolve(override = checkBoxTokens)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LabelMrcy(
            text = label,
            optionalText = optionalText,
            showIcon = showLabelIcon,
            error = hasError,
        )

        InputCheckBoxMrcy(
            label = checkBoxText,
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            showContainerBorder = showInputBorder,
            tokensGroup = resolvedTokens.inputCheckBoxTokensGroup,
            inputCheckBoxTokens = inputCheckBoxTokens,
        )

        if (bottomText != null) {
            CheckBoxBottomText(
                text = bottomText,
                hasError = hasError,
                showBottomIcon = showBottomIcon,
                checkBoxTokens = resolvedTokens,
            )
        }
    }
}

/**
 * CheckBox compuesto en modo lista de checkboxes.
 */
@Composable
fun CheckBoxMrcy(
    items: List<CheckBoxOption>,
    onItemsChange: (List<CheckBoxOption>) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    optionalText: Boolean = false,
    showLabelIcon: Boolean = false,
    hasError: Boolean = false,
    bottomText: String? = null,
    showBottomIcon: Boolean = true,
    showInputBorder: Boolean = false,
    inputCheckBoxTokens: InputCheckBoxTokens? = null,
    checkBoxTokens: CheckBoxTokens? = null,
) {
    val resolvedTokens = CheckBoxTokensResolver.resolve(override = checkBoxTokens)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        LabelMrcy(
            text = label,
            optionalText = optionalText,
            showIcon = showLabelIcon,
            error = hasError,
        )

        items.forEach { item ->
            InputCheckBoxMrcy(
                label = item.label,
                checked = item.checked,
                onCheckedChange = { nextChecked ->
                    val nextItems = items.map { current ->
                        if (current.id == item.id) {
                            current.copy(checked = nextChecked)
                        } else {
                            current
                        }
                    }
                    onItemsChange(nextItems)
                },
                enabled = enabled && item.enabled,
                showContainerBorder = showInputBorder,
                tokensGroup = resolvedTokens.inputCheckBoxTokensGroup,
                inputCheckBoxTokens = inputCheckBoxTokens,
            )
        }

        if (bottomText != null) {
            CheckBoxBottomText(
                text = bottomText,
                hasError = hasError,
                showBottomIcon = showBottomIcon,
                checkBoxTokens = resolvedTokens,
            )
        }
    }
}

@Composable
private fun CheckBoxBottomText(
    text: String,
    hasError: Boolean,
    showBottomIcon: Boolean,
    checkBoxTokens: CheckBoxTokens,
) {
    val color = if (hasError) {
        checkBoxTokens.bottomTextErrorColor
    } else {
        checkBoxTokens.bottomTextColor
    }

    androidx.compose.foundation.layout.Row(
        modifier = Modifier.padding(top = 4.dp),
    ) {
        if (showBottomIcon) {
            IconComposeAdapter.Render(
                icon = checkBoxTokens.bottomTextIcon,
                fillColor = ColorComposeAdapter.toComposeColor(color),
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            modifier = Modifier.widthIn(max = LabelSpacings.MaxLabelWidth.dp),
            text = text,
            style = TypographyComposeAdapter.toTextStyle(
                checkBoxTokens.bottomTextTypography,
                FontFamiliesComposeAdapter.toCompose(checkBoxTokens.fontFamilyToken),
            ).copy(color = ColorComposeAdapter.toComposeColor(color)),
        )
    }
}
