package com.example.mylibrary.ui.components.labels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylibrary.tokens.spacings.LabelSpacings
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter

/**
 * LabelMrcy
 *
 * - UI pura
 * - No conoce DS
 * - No usa LocalComposition
 */
@Composable
fun LabelMrcy(
    modifier: Modifier = Modifier,
    text: String,
    optional: Boolean = false,
    icon: Boolean = false,
    error: Boolean = false,
    labelTokens: LabelTokens? = null,
) {

    val tokens = LabelTokensResolver.resolve(labelTokens)

    val currentColor =
        if (error) tokens.foregroundError
        else tokens.foregroundDefault

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Text(
            text = text,
            style = TypographyComposeAdapter.toTextStyle(
                tokens.labelTypography,
                FontFamiliesComposeAdapter.toCompose(tokens.fontFamily)
            ),
            color = ColorComposeAdapter.toComposeColor(currentColor),
            modifier = Modifier.widthIn(max = LabelSpacings.MaxLabelWidth.dp)
        )

        if (optional) {
            Text(
                text = "(Opcional)",
                style = TypographyComposeAdapter.toTextStyle(
                    tokens.optionalTypography,
                    FontFamiliesComposeAdapter.toCompose(tokens.fontFamily)
                ),
                color = ColorComposeAdapter.toComposeColor(currentColor)
            )
        }

        if (icon) {
            IconComposeAdapter.Render(
                icon = tokens.infoIcon,
                fillColor = ColorComposeAdapter.toComposeColor(currentColor),
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}
