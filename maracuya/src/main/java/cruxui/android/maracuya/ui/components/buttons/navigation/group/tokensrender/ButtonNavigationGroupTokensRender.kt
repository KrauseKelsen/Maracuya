package cruxui.android.maracuya.ui.components.buttons.navigation.group.tokensrender

import androidx.compose.ui.unit.Dp
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokensrender.ButtonNavigationChildRender

/**
 * Tokens finales que necesita el render del grupo para pintar sus botones hijos.
 */
internal data class ButtonNavigationGroupTokensRender(
    val children: List<ButtonNavigationChildRender>,
    val childSpacing: Dp,
    val useMaxEdgeSeparation: Boolean,
)
