package cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokens

import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride

/**
 * Traduce los overrides publicos del child al override nativo de `ButtonNavigationMrcy`.
 */
internal object ButtonNavigationChildTokensResolver {

    fun resolveNativeOverride(
        override: ButtonNavigationChildTokensOverride?,
    ): ButtonNavigationTokensOverride? =
        override?.let {
            ButtonNavigationTokensOverride(
                contentColor = it.contentColor,
                contentPressColor = it.contentPressColor,
                containerColor = it.containerColor,
                disabledContentColor = it.disabledContentColor,
                disabledContainerColor = it.disabledContainerColor,
                hoverContainerColor = it.hoverContainerColor,
                borderContainerColor = it.borderContainerColor,
                borderDisabledColor = it.borderDisabledColor,
                iconToken = it.iconToken,
                iconColor = it.iconColor,
                typographyToken = it.typographyToken,
                fontFamilyToken = it.fontFamilyToken,
            )
        }
}
