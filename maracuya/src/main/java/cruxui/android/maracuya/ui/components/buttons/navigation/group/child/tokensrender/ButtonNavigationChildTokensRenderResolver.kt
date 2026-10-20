package cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokensrender

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.core.ButtonNavigationChild
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokens.ButtonNavigationChildTokensResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.variant.ButtonNavigationChildVariantResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior

/**
 * Convierte un child publico en parametros nativos listos para render.
 */
internal object ButtonNavigationChildTokensRenderResolver {

    @Composable
    fun resolve(
        child: ButtonNavigationChild,
        enabled: Boolean,
        shape: Shape,
    ): ButtonNavigationChildTokensRender {
        val tokens = ButtonNavigationChildTokensResolver.resolve(
            variant = child.variant,
            tokens = child.buttonNavigationChildTokens,
            override = child.buttonNavigationChildTokensOverride,
        )

        return ButtonNavigationChildTokensRender(
            modifier = child.modifier,
            label = child.label,
            onClick = child.onClick,
            enabled = enabled,
            width = child.width,
            minWidth = child.minWidth,
            shape = shape,
            variant = ButtonNavigationChildVariantResolver.resolveNativeVariant(child.variant),
            buttonNavigationBehavior = ButtonNavigationBehavior(showIcon = child.showIcon),
            nativeTokens = ButtonNavigationChildTokensResolver.toNativeTokens(tokens),
        )
    }
}
