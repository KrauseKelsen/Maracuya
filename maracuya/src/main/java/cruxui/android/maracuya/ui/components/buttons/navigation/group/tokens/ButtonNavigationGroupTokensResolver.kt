package cruxui.android.maracuya.ui.components.buttons.navigation.group.tokens

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import cruxui.android.maracuya.compositions.LocalLibraryIcons

/**
 * Resuelve tokens estructurales del grupo y adapta iconos necesarios del DS.
 *
 * Esta capa es la unica del group que lee `LocalLibraryIcons`; las capas de render
 * solo reciben el icono submit ya resuelto.
 */
internal object ButtonNavigationGroupTokensResolver {

    /**
     * Normaliza medidas del grupo y obtiene los tokens del Design System.
     */
    @Composable
    fun resolve(
        tokens: ButtonNavigationGroupTokens = ButtonNavigationGroupTokens(),
    ): ButtonNavigationGroupResolvedTokens {
        val icons = LocalLibraryIcons.current
        val hasExplicitButtonSpacing = tokens.buttonSpacing.isSpecified

        return ButtonNavigationGroupResolvedTokens(
            buttonSpacing = if (hasExplicitButtonSpacing) {
                tokens.buttonSpacing.coerceAtLeast(0.dp)
            } else {
                tokens.buttonSpacing
            },
            hasExplicitButtonSpacing = hasExplicitButtonSpacing,
            leadingButtonWidth = tokens.leadingButtonWidth,
            trailingButtonWidth = tokens.trailingButtonWidth,
            minLeadingButtonWidth = tokens.minLeadingButtonWidth,
            minTrailingButtonWidth = tokens.minTrailingButtonWidth,
            cornerRadius = tokens.cornerRadius.coerceAtLeast(0.dp),
            submitIconToken = icons.general.check,
        )
    }
}
