package cruxui.android.maracuya.ui.components.buttons.simple

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.utils.compose.collectPressedAsState
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.FontFamiliesComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.TypographyComposeAdapter

@Composable
fun ButtonMrcy(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(100.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    buttonVariant: ButtonVariant = ButtonVariant.PRIMARY,
    buttonTokens: ButtonTokens? = null,
    buttonTokensOverride: ButtonTokensOverride? = null,
    showProgressOnPress: Boolean = true,
) {
    val resolver = ButtonTokensResolver.resolve(
        variant = buttonVariant,
        tokens = buttonTokens,
        override = buttonTokensOverride,
    )

    val pressed by interactionSource.collectPressedAsState()
    val showProgress = showProgressOnPress && pressed

    val backgroundColor = when {
        !enabled -> resolver.disabledContainerColor
        pressed -> resolver.hoverContainerColor
        else -> resolver.containerColor
    }

    val borderColor = when {
        !enabled -> resolver.borderDisabledColor
        pressed -> resolver.hoverContainerColor
        else -> resolver.borderContainerColor
    }

    val contentColor = when {
        !enabled -> resolver.disabledContentColor
        pressed -> resolver.contentPressColor
        else -> resolver.contentColor
    }

    val containerColorCompose = ColorComposeAdapter.toComposeColor(backgroundColor)
    val contentColorCompose = ColorComposeAdapter.toComposeColor(contentColor)
    val disabledContainerColorCompose = ColorComposeAdapter.toComposeColor(resolver.disabledContainerColor)
    val disabledContentColorCompose = ColorComposeAdapter.toComposeColor(resolver.disabledContentColor)
    val borderContainerColorCompose = ColorComposeAdapter.toComposeColor(borderColor)

    val resolvedTextStyle = TypographyComposeAdapter.toTextStyle(
        token = resolver.textTypography,
        fontFamily = FontFamiliesComposeAdapter.toCompose(resolver.fontFamilyToken),
    )

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        border = BorderStroke(1.dp, borderContainerColorCompose),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColorCompose,
            contentColor = contentColorCompose,
            disabledContainerColor = disabledContainerColorCompose,
            disabledContentColor = disabledContentColorCompose,
        ),
    ) {
        ButtonContent(
            text = text,
            textStyle = resolvedTextStyle,
            contentColor = contentColorCompose,
            showProgress = showProgress,
        )
    }
}
