package cruxui.android.maracuya.ui.components.buttons.navigation.group.tokensrender

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehaviorResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.core.ButtonNavigationChild
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokensrender.ButtonNavigationChildRender
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokensrender.ButtonNavigationChildTokensRenderResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokens.ButtonNavigationGroupResolvedTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariantResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant

/**
 * Convierte tokens de variant, behavior y layout en hijos listos para render.
 *
 * Esta capa concentra la traduccion final hacia `ButtonNavigationMrcy` sin leer
 * composiciones locales ni decidir nuevos tokens base.
 */
internal object ButtonNavigationGroupTokensRenderResolver {

    /**
     * Resuelve una lista personalizada de hijos enviados por el developer.
     */
    fun resolveCustomChildren(
        children: List<ButtonNavigationChild>,
        enabled: Boolean,
        tokens: ButtonNavigationGroupResolvedTokens,
    ): ButtonNavigationGroupTokensRender {
        val hasJoinedChildren = children.size > 1 && !tokens.hasSeparatedButtons

        return ButtonNavigationGroupTokensRender(
            children = children.mapIndexed { index, child ->
                ButtonNavigationChildTokensRenderResolver.resolve(
                    child = child,
                    enabled = enabled && child.enabled,
                    shape = resolveCustomChildShape(
                        child = child,
                        index = index,
                        childCount = children.size,
                        tokens = tokens,
                    ),
                )
            },
            childSpacing = resolveChildSpacing(
                tokens = tokens,
                hasJoinedChildren = hasJoinedChildren,
            ),
            useMaxEdgeSeparation = false,
        )
    }

    /**
     * Resuelve una de las variantes fijas del grupo.
     */
    fun resolveVariant(
        variant: ButtonNavigationGroupVariant,
        behavior: ButtonNavigationGroupBehavior,
        tokens: ButtonNavigationGroupResolvedTokens,
        leadingLabel: String?,
        trailingLabel: String?,
        leadingEnabled: Boolean,
        trailingEnabled: Boolean,
        onLeadingButtonClick: () -> Unit,
        onTrailingButtonClick: () -> Unit,
    ): ButtonNavigationGroupTokensRender {
        val useMaxEdgeSeparation = !tokens.hasExplicitButtonSpacing
        val hasJoinedChildren = !useMaxEdgeSeparation && tokens.buttonSpacing == 0.dp

        return ButtonNavigationGroupTokensRender(
            children = listOf(
                createVariantChildRender(
                    label = ButtonNavigationGroupVariantResolver.resolveLeadingLabel(
                        variant = variant,
                        explicitLabel = leadingLabel,
                    ),
                    variant = ButtonNavigationGroupVariantResolver.resolveLeadingButtonVariant(),
                    behavior = ButtonNavigationGroupBehaviorResolver.resolveLeadingButtonBehavior(
                        showIcon = ButtonNavigationGroupVariantResolver.resolveLeadingIconVisibility(variant),
                    ),
                    enabled = leadingEnabled,
                    width = tokens.leadingButtonWidth,
                    minWidth = tokens.minLeadingButtonWidth,
                    shape = RoundedCornerShape(tokens.cornerRadius),
                    nativeTokensOverride = null,
                    onClick = onLeadingButtonClick,
                ),
                createVariantChildRender(
                    label = ButtonNavigationGroupBehaviorResolver.resolveTrailingLabel(
                        behavior = behavior,
                        explicitLabel = trailingLabel,
                        defaultLabel = ButtonNavigationGroupVariantResolver.resolveTrailingDefaultLabel(variant),
                    ),
                    variant = ButtonNavigationGroupVariantResolver.resolveTrailingButtonVariant(),
                    behavior = ButtonNavigationGroupBehaviorResolver.resolveTrailingButtonBehavior(
                        behavior = behavior,
                        showIcon = true,
                    ),
                    enabled = trailingEnabled,
                    width = tokens.trailingButtonWidth,
                    minWidth = tokens.minTrailingButtonWidth,
                    shape = RoundedCornerShape(tokens.cornerRadius),
                    nativeTokensOverride = if (ButtonNavigationGroupVariantResolver.resolveTrailingSubmitIcon(variant)) {
                        tokens.submitTokensOverride()
                    } else {
                        null
                    },
                    onClick = onTrailingButtonClick,
                ),
            ),
            childSpacing = resolveChildSpacing(
                tokens = tokens,
                hasJoinedChildren = hasJoinedChildren,
            ),
            useMaxEdgeSeparation = useMaxEdgeSeparation,
        )
    }

    private fun createVariantChildRender(
        label: String,
        variant: ButtonNavigationVariant,
        behavior: ButtonNavigationBehavior,
        enabled: Boolean,
        width: Dp,
        minWidth: Dp,
        shape: Shape,
        nativeTokensOverride: ButtonNavigationTokensOverride?,
        onClick: () -> Unit,
    ): ButtonNavigationChildRender =
        ButtonNavigationChildRender(
            modifier = Modifier,
            label = label,
            onClick = onClick,
            enabled = enabled,
            width = width,
            minWidth = minWidth,
            shape = shape,
            variant = variant,
            buttonNavigationBehavior = behavior,
            nativeTokensOverride = nativeTokensOverride,
        )

    private fun resolveCustomChildShape(
        child: ButtonNavigationChild,
        index: Int,
        childCount: Int,
        tokens: ButtonNavigationGroupResolvedTokens,
    ): Shape {
        if (childCount <= 1 || tokens.hasSeparatedButtons) return child.shape

        val radius = tokens.cornerRadius
        return when (index) {
            0 -> RoundedCornerShape(
                topStart = radius,
                bottomStart = radius,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
            )

            childCount - 1 -> RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = radius,
                bottomEnd = radius,
            )

            else -> RoundedCornerShape(0.dp)
        }
    }

    /**
     * Colapsa el borde compartido entre hijos unidos sin depender de su variante visual.
     */
    private fun resolveChildSpacing(
        tokens: ButtonNavigationGroupResolvedTokens,
        hasJoinedChildren: Boolean,
    ): Dp =
        when {
            tokens.hasSeparatedButtons -> tokens.buttonSpacing
            hasJoinedChildren -> -tokens.joinedChildBorderOverlap
            else -> 0.dp
        }

    private fun ButtonNavigationGroupResolvedTokens.submitTokensOverride(): ButtonNavigationTokensOverride =
        ButtonNavigationTokensOverride(
            iconToken = submitIconToken,
        )
}
