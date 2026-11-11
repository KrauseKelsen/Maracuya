package cruxui.android.lib.wrappers.components.buttons.simple.variant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonVariant expone el contrato core desde la API wrappers.
 *
 * Justificación: Es un typealias: no agrega estado ni mantiene una segunda definición.
 *
 * Se comunica con: Reutiliza cruxui.android.lib.core.components.buttons.variant.ButtonVariant.
 *
 * Uso:
 * ```kotlin
 * val value = ButtonVariant.SECONDARY
 * ```
 *
 * Consultar el contrato core y ButtonSimpleArchitecture.md para la integración completa.
 *
 * Objetivos:
 * - Mantener la misma identidad de tipo en Compose y XML.
 *
 * Flujo: Variante del botón → receta semántica y selección DEFAULT del TextView.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
typealias ButtonVariant = cruxui.android.lib.core.components.buttons.variant.ButtonVariant
