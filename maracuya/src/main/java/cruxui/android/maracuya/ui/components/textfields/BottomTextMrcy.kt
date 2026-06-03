package cruxui.android.maracuya.ui.components.textfields

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
 * Texto inferior del TextField
 *
 * Estado:
 * - Informativo
 * - Error
 */

@Composable
internal fun BottomTextMrcy(
    modifier: Modifier = Modifier,
    text: String,
    hasError: Boolean,
    showBottomIcon: Boolean = true,
    textFieldTokens: TextFieldTokens
) {

    val bottomIconColor = when {
        !hasError -> textFieldTokens.bottomTextColor // si hay error
        else -> textFieldTokens.bottomTextErrorColor // color del borde
    }

    Row (
        modifier = modifier.padding(top = 10.dp)
    ){

        // Icono opcional (futuro, vía tokens)
        if (showBottomIcon) {
            IconComposeAdapter.Render(
                icon = textFieldTokens.bottomTextIcon,
                fillColor = ColorComposeAdapter.toComposeColor(bottomIconColor),
                modifier = Modifier.padding(start = 2.dp)
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        val fontFamily =
            FontFamiliesComposeAdapter.toCompose(textFieldTokens.fontFamilyToken)

        val baseStyle =
            TypographyComposeAdapter.toTextStyle(
                textFieldTokens.bottomTextTypography,
                fontFamily
            )

        val resolvedStyle = baseStyle.copy(
            color = if (hasError) {
                ColorComposeAdapter.toComposeColor(textFieldTokens.bottomTextErrorColor)
            } else {
                ColorComposeAdapter.toComposeColor(textFieldTokens.bottomTextColor)
            }
        )

        Text(
            modifier = Modifier.widthIn(max = LabelSpacings.MaxLabelWidth.dp),
            text = text,
            style = resolvedStyle
        )
    }
}
