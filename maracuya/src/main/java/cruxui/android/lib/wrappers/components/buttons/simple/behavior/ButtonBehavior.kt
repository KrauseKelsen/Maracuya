package cruxui.android.lib.wrappers.components.buttons.simple.behavior

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonBehavior expone el contrato core desde la API wrappers.
 *
 * Justificación: Es un typealias: no agrega estado ni mantiene una segunda definición.
 *
 * Se comunica con: Reutiliza cruxui.android.lib.core.components.buttons.behavior.ButtonBehavior.
 *
 * Uso:
 * ```kotlin
 * val value = ButtonBehavior(isLoading = true)
 * ```
 *
 * Consultar el contrato core y ButtonSimpleArchitecture.md para la integración completa.
 *
 * Objetivos:
 * - Mantener la misma identidad de tipo en Compose y XML.
 *
 * Flujo: Entrada del host y gesto → prioridad compartida → bloqueo de acciones y motor de
 * animación.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
typealias ButtonBehavior = cruxui.android.lib.core.components.buttons.behavior.ButtonBehavior
