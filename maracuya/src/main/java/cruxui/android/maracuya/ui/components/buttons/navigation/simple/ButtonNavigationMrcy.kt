package cruxui.android.maracuya.ui.components.buttons.navigation.simple

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehaviorResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokensrender.ButtonNavigationTokensRenderResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant
import cruxui.android.maracuya.ui.utils.compose.collectPressedAsState
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.FontFamiliesComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.TypographyComposeAdapter

/**
 * Construye el boton de navegacion a partir de behavior, variant y tokens resueltos.
 *
 * Orquesta las capas del componente y delega la logica de seleccion a sus resolvers.
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
    onLoadingClick: (ButtonNavigationLoadingScope.() -> Unit)? = null,
) {
    val tokens = ButtonNavigationTokensResolver.resolve(
        variant = variant,
        tokens = buttonNavigationTokens,
        override = buttonNavigationTokensOverride,
    )

    val behaviorState = ButtonNavigationBehaviorResolver.resolve(
        behavior = buttonNavigationBehavior,
        enabled = enabled,
        onClick = onClick,
        onLoadingClick = onLoadingClick ?: onTrailingClick,
    )

    val pressed by interactionSource.collectPressedAsState()
    val renderTokens = ButtonNavigationTokensRenderResolver.resolve(
        tokens = tokens,
        enabled = enabled,
        pressed = pressed,
        isLoading = behaviorState.isLoading,
        canClick = behaviorState.canClick,
        showIcon = behaviorState.showIcon,
    )

    val textStyle = TypographyComposeAdapter.toTextStyle(
        token = renderTokens.typographyToken,
        fontFamily = FontFamiliesComposeAdapter.toCompose(renderTokens.fontFamilyToken),
    )

    Button(
        onClick = behaviorState.onClick,
        enabled = behaviorState.canClick,
        modifier = modifier,
        shape = shape,
        border = BorderStroke(1.dp, ColorComposeAdapter.toComposeColor(renderTokens.borderColor)),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = ColorComposeAdapter.toComposeColor(renderTokens.containerColor),
            contentColor = ColorComposeAdapter.toComposeColor(renderTokens.contentColor),
            disabledContainerColor = ColorComposeAdapter.toComposeColor(renderTokens.disabledContainerColor),
            disabledContentColor = ColorComposeAdapter.toComposeColor(renderTokens.disabledContentColor),
        ),
    ) {
        ButtonNavigationContent(
            label = label,
            tokens = renderTokens,
            iconAtEnd = variant == ButtonNavigationVariant.PRIMARY,
            behaviorState = behaviorState,
            contentColor = ColorComposeAdapter.toComposeColor(renderTokens.contentColor),
            iconColor = ColorComposeAdapter.toComposeColor(renderTokens.iconColor),
            textStyle = textStyle,
        )
    }
}
