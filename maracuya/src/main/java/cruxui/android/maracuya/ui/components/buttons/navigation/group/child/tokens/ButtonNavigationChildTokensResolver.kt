package cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokens

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.variant.ButtonNavigationChildVariant
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.variant.ButtonNavigationChildVariantResolver
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokens
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensResolver

/**
 * Resuelve tokens visuales del child y los adapta al boton nativo.
 */
internal object ButtonNavigationChildTokensResolver {

    @Composable
    fun resolve(
        variant: ButtonNavigationChildVariant,
        tokens: ButtonNavigationChildTokens?,
        override: ButtonNavigationChildTokensOverride?,
    ): ButtonNavigationChildTokens {
        val base = tokens ?: ButtonNavigationTokensResolver.resolve(
            variant = ButtonNavigationChildVariantResolver.resolveNativeVariant(variant),
        ).toChildTokens()

        return base.merge(override)
    }

    fun toNativeTokens(
        tokens: ButtonNavigationChildTokens,
    ): ButtonNavigationTokens =
        ButtonNavigationTokens(
            contentColor = tokens.contentColor,
            contentPressColor = tokens.contentPressColor,
            containerColor = tokens.containerColor,
            disabledContentColor = tokens.disabledContentColor,
            disabledContainerColor = tokens.disabledContainerColor,
            hoverContainerColor = tokens.hoverContainerColor,
            borderContainerColor = tokens.borderContainerColor,
            borderDisabledColor = tokens.borderDisabledColor,
            iconToken = tokens.iconToken,
            loadingIconToken = null,
            iconColor = tokens.iconColor,
            typographyToken = tokens.typographyToken,
            fontFamilyToken = tokens.fontFamilyToken,
        )

    private fun ButtonNavigationTokens.toChildTokens(): ButtonNavigationChildTokens =
        ButtonNavigationChildTokens(
            contentColor = contentColor,
            contentPressColor = contentPressColor,
            containerColor = containerColor,
            disabledContentColor = disabledContentColor,
            disabledContainerColor = disabledContainerColor,
            hoverContainerColor = hoverContainerColor,
            borderContainerColor = borderContainerColor,
            borderDisabledColor = borderDisabledColor,
            iconToken = iconToken,
            iconColor = iconColor,
            typographyToken = typographyToken,
            fontFamilyToken = fontFamilyToken,
        )
}
