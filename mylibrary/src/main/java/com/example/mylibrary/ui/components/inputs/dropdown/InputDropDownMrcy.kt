package com.example.mylibrary.ui.components.inputs.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.ui.components.inputs.config.InputDropDownTokenGroup
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.ui.components.labels.LabelTokensOverride
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter

/**
 * Átomo visual de dropdown.
 */
@Composable
fun InputDropDownMrcy(
    value: String,
    placeholder: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    hasError: Boolean = false,
    expanded: Boolean = false,
    variant: InputDropDownVariant = InputDropDownVariant.SIMPLE,
    tokenGroup: InputDropDownTokenGroup = InputDropDownTokenGroup.BASIC,
    leadingIcon: IconToken? = null,
    inputDropDownTokens: InputDropDownTokens? = null,
) {
    val tokens = InputDropDownTokensResolver.resolve(
        group = tokenGroup,
        variant = variant,
        leadingIconOverride = leadingIcon,
        override = inputDropDownTokens,
    )

    val borderColor = when {
        !enabled -> tokens.borderDisabled
        hasError -> tokens.borderError
        expanded -> tokens.borderFocus
        else -> tokens.borderDefault
    }

    val backgroundColor = if (!enabled) {
        ColorComposeAdapter.toComposeColor(tokens.backgroundDisabled)
    } else {
        Color.Transparent
    }

    val labelTokensOverride = LabelTokensOverride(
        foregroundDefault = if (value.isEmpty()) {
            tokens.placeholderColor
        } else {
            tokens.textColor
        },
        foregroundError = if (value.isEmpty()) {
            tokens.placeholderColor
        } else {
            tokens.textColor
        },
        labelTypography = if (value.isEmpty()) {
            tokens.placeholderTypography
        } else {
            tokens.textTypography
        },
    )

    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .background(
                color = backgroundColor,
                shape = shape,
            )
            .border(
                width = if (expanded) 2.dp else 1.dp,
                color = ColorComposeAdapter.toComposeColor(borderColor),
                shape = shape,
            )
            .clip(shape)
            .then(
                if (enabled && !readOnly) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        tokens.leadingIcon?.let { icon ->
            IconComposeAdapter.Render(
                icon = icon,
                fillColor = ColorComposeAdapter.toComposeColor(tokens.iconColor),
                size = 16.dp,
            )
        }

        LabelMrcy(
            modifier = Modifier.weight(1f),
            text = value.ifEmpty { placeholder },
            labelTokensOverride = labelTokensOverride,
            limitMaxLabel = false,
        )

        IconComposeAdapter.Render(
            icon = if (expanded) tokens.trailingExpandedIcon else tokens.trailingCollapsedIcon,
            fillColor = ColorComposeAdapter.toComposeColor(tokens.iconColor),
            size = 16.dp,
            contentDescription = if (expanded) "Contraer" else "Expandir",
        )
    }
}
