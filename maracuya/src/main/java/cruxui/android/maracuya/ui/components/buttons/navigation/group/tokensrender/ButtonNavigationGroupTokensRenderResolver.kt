package cruxui.android.maracuya.ui.components.buttons.navigation.group.tokensrender

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupButton
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehaviorResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.group.tokens.ButtonNavigationGroupResolvedTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariantResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant

/**
 * Convierte tokens de variant, behavior y layout en botones listos para render.
 *
 * Esta capa concentra la traduccion final hacia `ButtonNavigationMrcy` sin leer
 * composiciones locales ni decidir nuevos tokens base.
 */
internal object ButtonNavigationGroupTokensRenderResolver {

    /**
     * Resuelve una lista personalizada de botones enviados por el developer.
     */
    fun resolveCustomButtons(
        buttons: List<ButtonNavigationGroupButton>,
        enabled: Boolean,
        tokens: ButtonNavigationGroupResolvedTokens,
    ): ButtonNavigationGroupTokensRender =
        ButtonNavigationGroupTokensRender(
            buttons = buttons.mapIndexed { index, button ->
                button.toRenderButton(
                    enabled = enabled && button.enabled,
                    shape = resolveCustomButtonShape(
                        button = button,
                        index = index,
                        buttonCount = buttons.size,
                        tokens = tokens,
                    ),
                )
            },
            buttonSpacing = if (tokens.hasSeparatedButtons) tokens.buttonSpacing else 0.dp,
            useMaxEdgeSeparation = false,
        )

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
    ): ButtonNavigationGroupTokensRender =
        ButtonNavigationGroupTokensRender(
            buttons = listOf(
                createVariantButtonRender(
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
                    tokensOverride = null,
                    onClick = onLeadingButtonClick,
                ),
                createVariantButtonRender(
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
                    tokensOverride = if (ButtonNavigationGroupVariantResolver.resolveTrailingSubmitIcon(variant)) {
                        tokens.submitTokensOverride()
                    } else {
                        null
                    },
                    onClick = onTrailingButtonClick,
                ),
            ),
            buttonSpacing = if (tokens.hasExplicitButtonSpacing) tokens.buttonSpacing else 0.dp,
            useMaxEdgeSeparation = !tokens.hasExplicitButtonSpacing,
        )

    private fun ButtonNavigationGroupButton.toRenderButton(
        enabled: Boolean,
        shape: Shape,
    ): ButtonNavigationGroupButtonRender =
        ButtonNavigationGroupButtonRender(
            modifier = modifier,
            label = label,
            onClick = onClick,
            enabled = enabled,
            width = width,
            minWidth = minWidth,
            shape = shape,
            variant = variant,
            buttonNavigationBehavior = buttonNavigationBehavior,
            buttonNavigationTokens = buttonNavigationTokens,
            buttonNavigationTokensOverride = buttonNavigationTokensOverride,
            onTrailingClick = onTrailingClick,
            onLoadingClick = onLoadingClick,
        )

    private fun createVariantButtonRender(
        label: String,
        variant: ButtonNavigationVariant,
        behavior: ButtonNavigationBehavior,
        enabled: Boolean,
        width: Dp,
        minWidth: Dp,
        shape: Shape,
        tokensOverride: ButtonNavigationTokensOverride?,
        onClick: () -> Unit,
    ): ButtonNavigationGroupButtonRender =
        ButtonNavigationGroupButtonRender(
            modifier = Modifier,
            label = label,
            onClick = onClick,
            enabled = enabled,
            width = width,
            minWidth = minWidth,
            shape = shape,
            variant = variant,
            buttonNavigationBehavior = behavior,
            buttonNavigationTokens = null,
            buttonNavigationTokensOverride = tokensOverride,
            onTrailingClick = null,
            onLoadingClick = null,
        )

    private fun resolveCustomButtonShape(
        button: ButtonNavigationGroupButton,
        index: Int,
        buttonCount: Int,
        tokens: ButtonNavigationGroupResolvedTokens,
    ): Shape {
        if (buttonCount <= 1 || tokens.hasSeparatedButtons) return button.shape

        val radius = tokens.cornerRadius
        return when (index) {
            0 -> RoundedCornerShape(
                topStart = radius,
                bottomStart = radius,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
            )

            buttonCount - 1 -> RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = radius,
                bottomEnd = radius,
            )

            else -> RoundedCornerShape(0.dp)
        }
    }

    private fun ButtonNavigationGroupResolvedTokens.submitTokensOverride(): ButtonNavigationTokensOverride =
        ButtonNavigationTokensOverride(
            iconToken = submitIconToken,
        )
}
