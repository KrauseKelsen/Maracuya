package com.example.mylibrary.ui.components.buttons.icon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.mylibrary.ui.components.utils.IconPosition
import com.example.mylibrary.ui.utils.compose.collectPressedAsState
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter

@Composable
fun ButtonIconMrcy(
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(100.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    group: ButtonIconTokenGroup = ButtonIconTokenGroup.PRIMARY,
    variant: ButtonIconVariant? = null,
    buttonIconTokens: ButtonIconTokens? = null,
    buttonIconTokensOverride: ButtonIconTokensOverride? = null,
    onTrailingClick: (() -> Unit)? = null,
) {
    val tokens = ButtonIconTokensResolver.resolve(
        group = group,
        tokens = buttonIconTokens,
        override = buttonIconTokensOverride,
    )

    val behavior = ButtonIconBehaviorResolver.resolve(
        variant = variant,
        onTrailingClick = onTrailingClick,
    )

    val pressed by interactionSource.collectPressedAsState()
    val canClick = enabled && behavior.onTrailingClick != null

    val backgroundColor = when {
        !canClick -> tokens.disabledContainerColor
        pressed -> tokens.hoverContainerColor
        else -> tokens.containerColor
    }

    val contentColor = if (canClick) tokens.contentColor else tokens.disabledContentColor
    val iconColor = if (canClick) tokens.iconColor else tokens.disabledContentColor

    val textStyle = TypographyComposeAdapter.toTextStyle(
        token = tokens.typographyToken,
        fontFamily = FontFamiliesComposeAdapter.toCompose(tokens.fontFamilyToken),
    )

    Button(
        onClick = behavior.onTrailingClick ?: {},
        enabled = canClick,
        modifier = modifier,
        shape = shape,
        border = BorderStroke(1.dp, ColorComposeAdapter.toComposeColor(backgroundColor)),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorComposeAdapter.toComposeColor(backgroundColor),
            contentColor = ColorComposeAdapter.toComposeColor(contentColor),
            disabledContainerColor = ColorComposeAdapter.toComposeColor(tokens.disabledContainerColor),
            disabledContentColor = ColorComposeAdapter.toComposeColor(tokens.disabledContentColor),
        ),
    ) {
        val showIcon = behavior.iconPosition != null || label.isNullOrBlank()

        Box(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                if (showIcon && behavior.iconPosition != IconPosition.END) {
                    IconComposeAdapter.Render(
                        icon = tokens.iconToken,
                        fillColor = ColorComposeAdapter.toComposeColor(iconColor),
                        contentDescription = null,
                        size = tokens.iconToken.iconSize.dp,
                    )
                    if (!label.isNullOrBlank()) Spacer(modifier = Modifier.width(8.dp))
                }

                if (!label.isNullOrBlank()) {
                    Text(
                        modifier = Modifier,
                        text = label,
                        style = textStyle,
                        color = ColorComposeAdapter.toComposeColor(contentColor),
                    )

                }

                if (showIcon && behavior.iconPosition == IconPosition.END) {
                    if (!label.isNullOrBlank()) Spacer(modifier = Modifier.width(8.dp))
                    IconComposeAdapter.Render(
                        icon = tokens.iconToken,
                        fillColor = ColorComposeAdapter.toComposeColor(iconColor),
                        contentDescription = null,
                        size = tokens.iconToken.iconSize.dp,
                    )
                }
            }

        }
    }
}