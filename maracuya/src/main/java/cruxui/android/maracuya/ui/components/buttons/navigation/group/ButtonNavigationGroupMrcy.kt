package cruxui.android.maracuya.ui.components.buttons.navigation.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.ButtonNavigationChildMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.core.ButtonNavigationChild
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupDefaults
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokens.ButtonNavigationGroupTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokens.ButtonNavigationGroupTokensResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokensrender.ButtonNavigationGroupTokensRender
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokensrender.ButtonNavigationGroupTokensRenderResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant

/**
 * Une una lista personalizada de `ButtonNavigationChild`.
 *
 * Si no se envia separacion, los botones se pegan y el grupo resuelve las paredes
 * externas redondas e internas rectas. Si se envia separacion, cada boton conserva
 * su forma propia y se pinta con el espacio solicitado.
 */
@Composable
fun ButtonNavigationGroupMrcy(
    children: List<ButtonNavigationChild>,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = ButtonNavigationGroupDefaults.ButtonSpacing,
    enabled: Boolean = true,
) {
    val groupTokens = ButtonNavigationGroupTokensResolver.resolve(
        tokens = ButtonNavigationGroupTokens(
            buttonSpacing = buttonSpacing,
        ),
    )
    val renderTokens = ButtonNavigationGroupTokensRenderResolver.resolveCustomChildren(
        children = children,
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
 * Pinta la fila final con hijos ya resueltos por `TokensRenderResolver`.
 */
@Composable
private fun ButtonNavigationGroupLayout(
    modifier: Modifier,
    renderTokens: ButtonNavigationGroupTokensRender,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(renderTokens.childSpacing),
    ) {
        renderTokens.children.forEachIndexed { index, child ->
            ButtonNavigationChildMrcy(child = child)
            if (renderTokens.useMaxEdgeSeparation && index == 0) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

/**
 * Distribuye botones nativos, incluidas variantes square, conservando su forma y comportamiento.
 *
 * @param modifier Modificador de la fila, que ocupa el ancho disponible.
 * @param buttonSpacing Separación entre botones; por defecto `12.dp`. Debe ser no negativa.
 * @param horizontalAlignment Alineación horizontal del conjunto; por defecto al inicio.
 * @param content Botones del grupo. Cada botón define su propio estado habilitado y acción.
 */
@Composable
fun ButtonNavigationGroupMrcy(
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 12.dp,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing, horizontalAlignment),
        content = content,
    )
}
