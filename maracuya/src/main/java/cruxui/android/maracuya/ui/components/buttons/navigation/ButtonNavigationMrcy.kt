package cruxui.android.maracuya.ui.components.buttons.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.utils.IconPosition
import cruxui.android.maracuya.ui.utils.compose.collectPressedAsState
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.FontFamiliesComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.IconComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.TypographyComposeAdapter

/**
 * ButtonNavigationMrcy construye el boton de navegacion a partir de los tokens visuales de ButtonMrcy.
 *
 * La variante define color y posicion del icono. onClick ejecuta la accion del boton y
 * onTrailingClick solo administra el ciclo de loading cuando el comportamiento lo requiere.
 */
@Composable
fun ButtonNavigationMrcy(
    modifier: Modifier = Modifier,
    label: String? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(100.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    variant: ButtonNavigationVariant = ButtonNavigationVariant.PRIMARY,
    buttonNavigationBehavior: ButtonNavigationBehavior = ButtonNavigationBehavior.Default,
    buttonNavigationTokens: ButtonNavigationTokens? = null,
    buttonNavigationTokensOverride: ButtonNavigationTokensOverride? = null,
    onTrailingClick: (ButtonNavigationLoadingScope.() -> Unit)? = null,
) {
    val tokens = ButtonNavigationTokensResolver.resolve(
        variant = variant,
        tokens = buttonNavigationTokens,
        override = buttonNavigationTokensOverride,
    )

    val resolvedBehavior = ButtonNavigationBehaviorResolver.resolve(
        variant = variant,
        behavior = buttonNavigationBehavior,
        enabled = enabled,
        onClick = onClick,
        onTrailingClick = onTrailingClick,
    )

    val pressed by interactionSource.collectPressedAsState()
    val isVisuallyDisabled = !enabled
    val showPressedState = pressed && resolvedBehavior.canClick

    val backgroundColor = when {
        isVisuallyDisabled -> tokens.disabledContainerColor
        showPressedState -> tokens.hoverContainerColor
        else -> tokens.containerColor
    }

    val borderColor = when {
        isVisuallyDisabled -> tokens.borderDisabledColor
        showPressedState -> tokens.hoverContainerColor
        else -> tokens.borderContainerColor
    }

    val contentColor = when {
        isVisuallyDisabled -> tokens.disabledContentColor
        showPressedState -> tokens.contentPressColor
        else -> tokens.contentColor
    }

    val iconColor = when {
        isVisuallyDisabled -> tokens.disabledContentColor
        showPressedState -> tokens.contentPressColor
        else -> tokens.iconColor
    }

    val disabledContainerColor = if (resolvedBehavior.isLoading) {
        backgroundColor
    } else {
        tokens.disabledContainerColor
    }
    val disabledContentColor = if (resolvedBehavior.isLoading) {
        contentColor
    } else {
        tokens.disabledContentColor
    }

    val textStyle = TypographyComposeAdapter.toTextStyle(
        token = tokens.typographyToken,
        fontFamily = FontFamiliesComposeAdapter.toCompose(tokens.fontFamilyToken),
    )

    Button(
        onClick = resolvedBehavior.onClick,
        enabled = resolvedBehavior.canClick,
        modifier = modifier,
        shape = shape,
        border = BorderStroke(1.dp, ColorComposeAdapter.toComposeColor(borderColor)),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorComposeAdapter.toComposeColor(backgroundColor),
            contentColor = ColorComposeAdapter.toComposeColor(contentColor),
            disabledContainerColor = ColorComposeAdapter.toComposeColor(disabledContainerColor),
            disabledContentColor = ColorComposeAdapter.toComposeColor(disabledContentColor),
        ),
    ) {
        ButtonNavigationContent(
            label = label,
            tokens = tokens,
            behavior = buttonNavigationBehavior,
            resolvedBehavior = resolvedBehavior,
            contentColor = ColorComposeAdapter.toComposeColor(contentColor),
            iconColor = ColorComposeAdapter.toComposeColor(iconColor),
            textStyle = textStyle,
        )
    }
}

/**
 * Renderiza texto e icono respetando la posicion resuelta por la variante.
 */
@Composable
private fun ButtonNavigationContent(
    label: String?,
    tokens: ButtonNavigationTokens,
    behavior: ButtonNavigationBehavior,
    resolvedBehavior: ButtonNavigationResolvedBehavior,
    contentColor: Color,
    iconColor: Color,
    textStyle: TextStyle,
) {
    val iconToken = if (resolvedBehavior.isLoading) {
        tokens.loadingIconToken
    } else {
        tokens.iconToken
    }
    val visibleIconToken = if (behavior.showIcon) iconToken else null
    val hasLabel = !label.isNullOrBlank()

    Row(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (visibleIconToken != null && resolvedBehavior.iconPosition != IconPosition.END) {
            IconComposeAdapter.Render(
                icon = visibleIconToken,
                modifier = Modifier.rotate(resolvedBehavior.loadingIconRotationDegrees),
                fillColor = iconColor,
                contentDescription = null,
                size = visibleIconToken.iconSize.dp,
            )
            if (hasLabel) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        if (hasLabel) {
            Text(
                text = label,
                style = textStyle,
                color = contentColor,
            )
        }

        if (visibleIconToken != null && resolvedBehavior.iconPosition == IconPosition.END) {
            if (hasLabel) {
                Spacer(modifier = Modifier.width(8.dp))
            }
            IconComposeAdapter.Render(
                icon = visibleIconToken,
                modifier = Modifier.rotate(resolvedBehavior.loadingIconRotationDegrees),
                fillColor = iconColor,
                contentDescription = null,
                size = visibleIconToken.iconSize.dp,
            )
        }
    }
}
