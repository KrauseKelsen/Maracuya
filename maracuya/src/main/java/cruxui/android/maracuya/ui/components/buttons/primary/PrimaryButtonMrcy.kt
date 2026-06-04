package cruxui.android.maracuya.ui.components.buttons.primary

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.button.ButtonMrcy
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokens
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokensOverride
import cruxui.android.maracuya.ui.components.buttons.button.ButtonVariant

@Composable
fun PrimaryButtonMrcy(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(100.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    primaryButtonTokens: PrimaryButtonTokens? = null,
    primaryButtonTokensOverride: PrimaryButtonTokensOverride? = null,
    showProgressOnPress: Boolean = true,
) {
    ButtonMrcy(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        interactionSource = interactionSource,
        buttonVariant = ButtonVariant.PRIMARY,
        buttonTokens = primaryButtonTokens?.toButtonTokens(),
        buttonTokensOverride = primaryButtonTokensOverride?.toButtonTokensOverride(),
        showProgressOnPress = showProgressOnPress,
    )
}

private fun PrimaryButtonTokens.toButtonTokens(): ButtonTokens = ButtonTokens(
    containerColor = containerColor,
    contentColor = contentColor,
    contentPressColor = contentColor,
    hoverContainerColor = hoverContainerColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor,
    borderContainerColor = borderContainerColor,
    borderDisabledColor = borderDisabledColor,
    textTypography = textTypography,
    fontFamilyToken = fontFamilyToken,
)

private fun PrimaryButtonTokensOverride.toButtonTokensOverride(): ButtonTokensOverride = ButtonTokensOverride(
    containerColor = containerColor,
    contentColor = contentColor,
    contentPressColor = contentColor,
    hoverContainerColor = hoverContainerColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor,
    borderContainerColor = borderContainerColor,
    borderDisabledColor = borderDisabledColor,
    textTypography = textTypography,
    fontFamilyToken = fontFamilyToken,
)
