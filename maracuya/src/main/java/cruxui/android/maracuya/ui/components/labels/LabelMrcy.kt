package cruxui.android.maracuya.ui.components.labels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.tokens.spacings.LabelSpacings
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.FontFamiliesComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.IconComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.TypographyComposeAdapter

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
    optionalText: Boolean = false,
    showIcon: Boolean = false,
    error: Boolean = false,
    onClick: (() -> Unit)? = null,
    onClickContentDescription: String? = null,
    labelTokens: LabelTokens? = null,
    labelTokensOverride: LabelTokensOverride? = null,
    limitMaxLabel: Boolean? = true
) {

    val tokens = LabelTokensResolver.resolve(
        tokens = labelTokens,
        override = labelTokensOverride
    )

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
            modifier = Modifier
                .then(if(limitMaxLabel == true){
                    modifier.widthIn(max = LabelSpacings.MaxLabelWidth.dp)
                }else{
                    Modifier
                }
                )
                .then(
                    if(onClick!=null){
                        Modifier.clickable(
                            onClickLabel = onClickContentDescription,
                            onClick = onClick
                        )
                    } else {
                        Modifier
                    }
                )
        )

        if (optionalText) {
            Text(
                text = "(Opcional)",
                style = TypographyComposeAdapter.toTextStyle(
                    tokens.optionalTypography,
                    FontFamiliesComposeAdapter.toCompose(tokens.fontFamily)
                ),
                color = ColorComposeAdapter.toComposeColor(currentColor)
            )
        }

        if (showIcon) {
            IconComposeAdapter.Render(
                icon = tokens.infoIcon,
                fillColor = ColorComposeAdapter.toComposeColor(currentColor),
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}
