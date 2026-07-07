package cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokensrender

import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokens

/**
 * Resuelve los tokens finales que se aplican al render segun estado e interaccion.
 */
internal object ButtonNavigationTokensRenderResolver {

    /**
     * Calcula colores e icono renderizables a partir de los tokens base y el estado actual.
     */
    fun resolve(
        tokens: ButtonNavigationTokens,
        enabled: Boolean,
        pressed: Boolean,
        isLoading: Boolean,
        canClick: Boolean,
        showIcon: Boolean,
    ): ButtonNavigationTokensRender {
        val isVisuallyDisabled = !enabled
        val showPressedState = pressed && canClick

        val containerColor = when {
            isVisuallyDisabled -> tokens.disabledContainerColor
            showPressedState -> tokens.hoverContainerColor
            else -> tokens.containerColor
        }

        val borderColor = when {
            isVisuallyDisabled -> tokens.borderDisabledColor
            showPressedState -> tokens.hoverContainerColor
            else -> tokens.borderContainerColor
        }

        val contentColor = when {
            isVisuallyDisabled -> tokens.disabledContentColor
            showPressedState -> tokens.contentPressColor
            else -> tokens.contentColor
        }

        val iconColor = when {
            isVisuallyDisabled -> tokens.disabledContentColor
            showPressedState -> tokens.contentPressColor
            else -> tokens.iconColor
        }

        return ButtonNavigationTokensRender(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = if (isLoading) {
                containerColor
            } else {
                tokens.disabledContainerColor
            },
            disabledContentColor = if (isLoading) {
                contentColor
            } else {
                tokens.disabledContentColor
            },
            borderColor = borderColor,
            iconColor = iconColor,
            iconToken = resolveIconToken(
                tokens = tokens,
                isLoading = isLoading,
                showIcon = showIcon,
            ),
            typographyToken = tokens.typographyToken,
            fontFamilyToken = tokens.fontFamilyToken,
        )
    }

    private fun resolveIconToken(
        tokens: ButtonNavigationTokens,
        isLoading: Boolean,
        showIcon: Boolean,
    ): IconToken? {
        if (!showIcon) return null
        return if (isLoading) {
            tokens.loadingIconToken
        } else {
            tokens.iconToken
        }
    }
}
