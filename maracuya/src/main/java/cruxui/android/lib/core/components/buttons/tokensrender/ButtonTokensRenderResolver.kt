package cruxui.android.lib.core.components.buttons.tokensrender

import cruxui.android.lib.core.components.buttons.behavior.ButtonBehaviorState
import cruxui.android.lib.core.components.buttons.tokens.ButtonTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokensRenderResolver selecciona los tres colores efectivos y construye la
 * apariencia del TextView.
 *
 * Justificación: Loading usa exactamente los colores normales; los iconos conservan la política de
 * tinte de TextView.
 *
 * Se comunica con: Recibe ButtonTokens y ButtonBehaviorState; ambos frontends consumen
 * ButtonTokensRender.
 *
 * Uso:
 * ```kotlin
 * // Integración interna del DS:
 * val render = ButtonTokensRenderResolver.resolve(tokens, state)
 * // Entregar render.text a TextView(tokensOverride = ...).
 * ```
 *
 * Objetivos:
 * - Resolver estados una sola vez; conservar contenido para medir durante loading.
 *
 * Flujo: Receta completa y estado efectivo → colores resueltos → apariencia del TextView y
 * superficies.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal object ButtonTokensRenderResolver {
    /**
     * Resuelve superficies y texto sin adaptar unidades ni crear Views.
     *
     * @param tokens Receta completa, ya personalizada.
     * @param state Estado normalizado por behavior.
     * @return Colores finales y contrato heredado de TextView.
     */
    fun resolve(tokens: ButtonTokens, state: ButtonBehaviorState): ButtonTokensRender {
        val color =
            when {
                state.isDisabled -> tokens.disabledTextColor
                state.isPressed -> tokens.pressedTextColor
                else -> tokens.textColor
            }
        return ButtonTokensRender(
            background =
                when {
                    state.isDisabled -> tokens.disabledBackground
                    state.isPressed -> tokens.pressedBackground
                    else -> tokens.background
                },
            border =
                when {
                    state.isDisabled -> tokens.disabledBorder
                    state.isPressed -> tokens.pressedBorder
                    else -> tokens.border
                },
            textColor = color,
            typography = tokens.typography,
            fontFamily = tokens.fontFamily,
            iconStart = tokens.iconStart,
            iconEnd = tokens.iconEnd,
        )
    }
}
