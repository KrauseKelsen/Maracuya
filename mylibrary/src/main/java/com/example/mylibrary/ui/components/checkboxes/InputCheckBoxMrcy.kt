package com.example.mylibrary.ui.components.checkboxes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mylibrary.tokens.spacings.InputCheckBoxSpacings
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter

/**
 * Átomo base para checkbox.
 *
 * Soporta modo card (con borde externo) y modo plano (sin borde),
 * para reutilizarlo en checkbox simple y checkbox list.
 */
@Composable
fun InputCheckBoxMrcy(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showContainerBorder: Boolean = true,
    tokensGroup: InputCheckBoxTokensGroup = InputCheckBoxTokensGroup.DEFAULT,
    inputCheckBoxTokens: InputCheckBoxTokens? = null,
) {
    val tokens = InputCheckBoxTokensResolver.resolve(
        group = tokensGroup,
        override = inputCheckBoxTokens,
    )

    val borderColor = if (enabled) tokens.borderDefault else tokens.borderDisabled
    val labelColor = if (enabled) tokens.labelColor else tokens.labelDisabledColor

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (showContainerBorder) {
                    Modifier
                        .border(
                            width = InputCheckBoxSpacings.CardBorderWidth.dp,
                            color = ColorComposeAdapter.toComposeColor(borderColor),
                            shape = RoundedCornerShape(InputCheckBoxSpacings.CardCornerRadius.dp),
                        )
                        .padding(
                            horizontal = InputCheckBoxSpacings.RowHorizontalPadding.dp,
                            vertical = InputCheckBoxSpacings.RowVerticalPadding.dp,
                        )
                } else {
                    Modifier.padding(vertical = 4.dp)
                }
            )
            .clickable(enabled = enabled) { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(InputCheckBoxSpacings.ContentSpacing.dp),
    ) {
        val checkBackground = when {
            checked && enabled -> tokens.checkBackground
            checked && !enabled -> tokens.borderDefault
            else -> null
        }

        Box(
            modifier = Modifier
                .size(InputCheckBoxSpacings.CheckSize.dp)
                .clip(RoundedCornerShape(InputCheckBoxSpacings.CheckCornerRadius.dp))
                .border(
                    width = InputCheckBoxSpacings.CheckBorderWidth.dp,
                    color = ColorComposeAdapter.toComposeColor(borderColor),
                    shape = RoundedCornerShape(InputCheckBoxSpacings.CheckCornerRadius.dp),
                )
                .then(
                    if (checkBackground != null) {
                        Modifier.background(ColorComposeAdapter.toComposeColor(checkBackground))
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                IconComposeAdapter.Render(
                    icon = tokens.checkIcon,
                    fillColor = ColorComposeAdapter.toComposeColor(tokens.checkIconColor),
                    modifier = Modifier.size(18.dp),
                )
            }
        }

        Text(
            text = label,
            style = TypographyComposeAdapter.toTextStyle(
                tokens.labelTypography,
                FontFamiliesComposeAdapter.toCompose(tokens.fontFamilyToken),
            ),
            color = ColorComposeAdapter.toComposeColor(labelColor),
            modifier = Modifier.weight(1f),
        )
    }
}
