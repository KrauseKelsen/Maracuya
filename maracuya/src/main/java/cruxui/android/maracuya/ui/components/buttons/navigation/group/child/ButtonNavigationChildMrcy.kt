package cruxui.android.maracuya.ui.components.buttons.navigation.group.child

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.isSpecified
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokensrender.ButtonNavigationChildTokensRender
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationMrcy

/**
 * Renderiza un child resuelto usando el boton nativo como detalle interno.
 */
@Composable
internal fun ButtonNavigationChildMrcy(
    child: ButtonNavigationChildTokensRender,
) {
    ButtonNavigationMrcy(
        modifier = child.modifier.buttonNavigationChildWidth(
            width = child.width,
            minWidth = child.minWidth,
        ),
        label = child.label,
        onClick = child.onClick,
        enabled = child.enabled,
        shape = child.shape,
        variant = child.variant,
        buttonNavigationBehavior = child.buttonNavigationBehavior,
        buttonNavigationTokens = child.nativeTokens,
    )
}

private fun Modifier.buttonNavigationChildWidth(
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
