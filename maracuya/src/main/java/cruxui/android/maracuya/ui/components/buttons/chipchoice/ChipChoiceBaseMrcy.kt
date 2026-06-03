package cruxui.android.maracuya.ui.components.buttons.chipchoice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import cruxui.android.maracuya.ui.utils.compose.collectPressedAsState
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.FontFamiliesComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.TypographyComposeAdapter

/**
 * Chip base visual (item individual).
 * No contiene label ni layout de lista.
 */
@Composable
fun ChipChoiceBaseMrcy(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },

    // Precedencia:
    // 1) tokens explícitos
    // 2) Library tokens
    // 3) Material
    chipTokens: ChipChoiceBaseTokens? = null,
) {
    val tokens = ChipChoiceBaseTokensResolver.resolve(chipTokens)
    val pressedState by interactionSource.collectPressedAsState()

    // --- Resolución de colores según estado ---
    val containerColorToken = when {
        !enabled -> tokens.disabledContainerColor // si el boton se desabilita
        pressedState -> tokens.pressedContainerColor
        else -> tokens.containerColor
    }

    val contentColorToken = when { // color de letra
        !enabled -> tokens.disabledContentColor // si el boton se desabilita
        pressedState -> tokens.pressedContentColor
        else -> tokens.contentColor
    }

    val borderColorToken = when {
        !enabled -> tokens.borderColor // si el boton se desabilita
        pressedState -> tokens.contentColor
        else -> tokens.borderColor
    }

    // --- Adaptación a Compose ---
    val containerColor = ColorComposeAdapter.toComposeColor(containerColorToken)
    val contentColor = ColorComposeAdapter.toComposeColor(contentColorToken)
    val borderColor = ColorComposeAdapter.toComposeColor(borderColorToken)

    val textStyle = TypographyComposeAdapter.toTextStyle(
        token = tokens.textTypography,
        fontFamily = FontFamiliesComposeAdapter.toCompose(tokens.fontFamilyToken)
    )

    Surface(
        modifier = modifier
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = shape,
        color = containerColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = contentColor,
            style = textStyle
        )
    }
}
