package cruxui.android.lib.ui.components.buttons.simple.tokensrender

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokensRenderResolver reutiliza la resolución semántica del core.
 *
 * Justificación: La adaptación de plataforma no debe repetir la prioridad de colores.
 *
 * Se comunica con: El componente llama el resolver compartido y entrega su resultado al render.
 *
 * Uso:
 * ```kotlin
 * // Dentro del módulo DS:
 * val render = ButtonTokensRenderResolver.resolve(tokens, state)
 * ```
 *
 * Objetivos:
 * - Conservar un único resultado semántico en ambos frontends.
 *
 * Flujo: Receta completa y estado efectivo → colores resueltos → apariencia del TextView y
 * superficies.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal typealias ButtonTokensRenderResolver =
    cruxui.android.lib.core.components.buttons.tokensrender.ButtonTokensRenderResolver
