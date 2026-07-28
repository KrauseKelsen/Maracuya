package cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokensrender

import androidx.compose.ui.graphics.Shape
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.core.ButtonNavigationChild
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokens.ButtonNavigationChildTokensResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.variant.ButtonNavigationChildVariantResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior

/**
 * Convierte un child publico en parametros nativos listos para render.
 */
internal object ButtonNavigationChildTokensRenderResolver {

    fun resolve(
        child: ButtonNavigationChild,
        enabled: Boolean,
        shape: Shape,
    ): ButtonNavigationChildRender =
        ButtonNavigationChildRender(
            modifier = child.modifier,
            label = child.label,
            onClick = child.onClick,
            enabled = enabled,
            width = child.width,
            minWidth = child.minWidth,
            shape = shape,
            variant = ButtonNavigationChildVariantResolver.resolveNativeVariant(child.variant),
            buttonNavigationBehavior = ButtonNavigationBehavior(showIcon = child.showIcon),
            nativeTokensOverride = ButtonNavigationChildTokensResolver.resolveNativeOverride(
                override = child.buttonNavigationChildTokensOverride,
            ),
        )
}
