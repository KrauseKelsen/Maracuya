package cruxui.android.maracuya.ui.components.buttons.secondary

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
fun SecondaryButtonMrcy(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(100.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    secondaryButtonTokens: SecondaryButtonTokens? = null,
    secondaryButtonTokensOverride: SecondaryButtonTokensOverride? = null,
    showProgressOnPress: Boolean = false,
) {
    ButtonMrcy(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        interactionSource = interactionSource,
        buttonVariant = ButtonVariant.SECONDARY,
        buttonTokens = secondaryButtonTokens?.toButtonTokens(),
        buttonTokensOverride = secondaryButtonTokensOverride?.toButtonTokensOverride(),
        showProgressOnPress = showProgressOnPress,
    )
}

private fun SecondaryButtonTokens.toButtonTokens(): ButtonTokens = ButtonTokens(
    containerColor = containerColor,
    contentColor = contentColor,
    contentPressColor = contentPressColor,
    hoverContainerColor = hoverContainerColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor,
    borderContainerColor = borderContainerColor,
    borderDisabledColor = borderDisabledColor,
    textTypography = textTypography,
    fontFamilyToken = fontFamilyToken,
)

private fun SecondaryButtonTokensOverride.toButtonTokensOverride(): ButtonTokensOverride = ButtonTokensOverride(
    containerColor = containerColor,
    contentColor = contentColor,
    contentPressColor = contentPressColor,
    hoverContainerColor = hoverContainerColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor,
    borderContainerColor = borderContainerColor,
    borderDisabledColor = borderDisabledColor,
    textTypography = textTypography,
    fontFamilyToken = fontFamilyToken,
)
