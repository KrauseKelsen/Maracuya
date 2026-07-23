package cruxui.android.maracuya.ui.components.buttons.navigation.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupButton
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupDefaults
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokens.ButtonNavigationGroupTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokens.ButtonNavigationGroupTokensResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokensrender.ButtonNavigationGroupButtonRender
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokensrender.ButtonNavigationGroupTokensRender
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokensrender.ButtonNavigationGroupTokensRenderResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationMrcy

/**
 * Une una lista personalizada de `ButtonNavigationMrcy`.
 *
 * Si no se envia separacion, los botones se pegan y el grupo resuelve las paredes
 * externas redondas e internas rectas. Si se envia separacion, cada boton conserva
 * su forma propia y se pinta con el espacio solicitado.
 */
@Composable
fun ButtonNavigationGroupMrcy(
    buttons: List<ButtonNavigationGroupButton>,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = ButtonNavigationGroupDefaults.ButtonSpacing,
    enabled: Boolean = true,
) {
    val groupTokens = ButtonNavigationGroupTokensResolver.resolve(
        tokens = ButtonNavigationGroupTokens(
            buttonSpacing = buttonSpacing,
        ),
    )
    val renderTokens = ButtonNavigationGroupTokensRenderResolver.resolveCustomButtons(
        buttons = buttons,
        enabled = enabled,
        tokens = groupTokens,
    )

    ButtonNavigationGroupLayout(
        modifier = modifier,
        renderTokens = renderTokens,
    )
}

/**
 * Renderiza una de las variantes fijas del grupo de navegacion.
 *
 * El grupo solo orquesta resolvers: variant define la combinacion fija, behavior
 * maneja loading y tokens/tokensrender calculan medidas y especificaciones finales.
 */
@Composable
fun ButtonNavigationGroupMrcy(
    modifier: Modifier = Modifier,
    variant: ButtonNavigationGroupVariant,
    onLeadingButtonClick: () -> Unit,
    onTrailingButtonClick: () -> Unit,
    leadingLabel: String? = null,
    trailingLabel: String? = null,
    leadingButtonWidth: Dp = ButtonNavigationGroupDefaults.LeadingButtonWidth,
    trailingButtonWidth: Dp = ButtonNavigationGroupDefaults.TrailingButtonWidth,
    buttonSpacing: Dp = ButtonNavigationGroupDefaults.ButtonSpacing,
    enabled: Boolean = true,
    leadingEnabled: Boolean = enabled,
    trailingEnabled: Boolean = enabled,
    buttonNavigationGroupBehavior: ButtonNavigationGroupBehavior = ButtonNavigationGroupBehavior.Default,
) {
    val groupTokens = ButtonNavigationGroupTokensResolver.resolve(
        tokens = ButtonNavigationGroupTokens(
            buttonSpacing = buttonSpacing,
            leadingButtonWidth = leadingButtonWidth,
            trailingButtonWidth = trailingButtonWidth,
        ),
    )
    val renderTokens = ButtonNavigationGroupTokensRenderResolver.resolveVariant(
        variant = variant,
        behavior = buttonNavigationGroupBehavior,
        tokens = groupTokens,
        leadingLabel = leadingLabel,
        trailingLabel = trailingLabel,
        leadingEnabled = leadingEnabled,
        trailingEnabled = trailingEnabled,
        onLeadingButtonClick = onLeadingButtonClick,
        onTrailingButtonClick = onTrailingButtonClick,
    )

    ButtonNavigationGroupLayout(
        modifier = modifier,
        renderTokens = renderTokens,
    )
}

/**
 * Pinta la fila final con botones ya resueltos por `TokensRenderResolver`.
 */
@Composable
private fun ButtonNavigationGroupLayout(
    modifier: Modifier,
    renderTokens: ButtonNavigationGroupTokensRender,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(renderTokens.buttonSpacing),
    ) {
        renderTokens.buttons.forEachIndexed { index, button ->
            ButtonNavigationGroupChild(button = button)
            if (renderTokens.useMaxEdgeSeparation && index == 0) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

/**
 * Renderiza un boton hijo conservando la API nativa de `ButtonNavigationMrcy`.
 */
@Composable
private fun ButtonNavigationGroupChild(
    button: ButtonNavigationGroupButtonRender,
) {
    ButtonNavigationMrcy(
        modifier = button.modifier.buttonNavigationGroupWidth(
            width = button.width,
            minWidth = button.minWidth,
        ),
        label = button.label,
        onClick = button.onClick,
        enabled = button.enabled,
        shape = button.shape,
        variant = button.variant,
        buttonNavigationBehavior = button.buttonNavigationBehavior,
        buttonNavigationTokens = button.buttonNavigationTokens,
        buttonNavigationTokensOverride = button.buttonNavigationTokensOverride,
        onTrailingClick = button.onTrailingClick,
        onLoadingClick = button.onLoadingClick,
    )
}

private fun Modifier.buttonNavigationGroupWidth(
    width: Dp,
    minWidth: Dp,
): Modifier {
    val hasWidth = width.isSpecified
    val hasMinWidth = minWidth.isSpecified

    return when {
        hasWidth && hasMinWidth -> width(width.coerceAtLeast(minWidth))
        hasWidth -> width(width)
        hasMinWidth -> widthIn(min = minWidth)
        else -> this
    }
}
