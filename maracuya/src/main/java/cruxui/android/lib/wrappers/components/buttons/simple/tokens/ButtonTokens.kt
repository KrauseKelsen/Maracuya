package cruxui.android.lib.wrappers.components.buttons.simple.tokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokens expone el contrato core desde la API wrappers.
 *
 * Justificación: Es un typealias: no agrega estado ni mantiene una segunda definición.
 *
 * Se comunica con: Reutiliza cruxui.android.lib.core.components.buttons.tokens.ButtonTokens.
 *
 * Uso:
 * ```kotlin
 * val library = cruxui.android.lib.theme.LibraryThemes.Maracuya.resolve(false)
 * val override = ButtonTokensOverride(iconStart = library.icons.alerts.info)
 * ```
 *
 * Consultar el contrato core y ButtonSimpleArchitecture.md para la integración completa.
 *
 * Objetivos:
 * - Mantener la misma identidad de tipo en Compose y XML.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
typealias ButtonTokens = cruxui.android.lib.core.components.buttons.tokens.ButtonTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokensOverride expone el contrato core desde la API wrappers.
 *
 * Justificación: Es un typealias: no agrega estado ni mantiene una segunda definición.
 *
 * Se comunica con: Reutiliza
 * cruxui.android.lib.core.components.buttons.tokens.ButtonTokensOverride.
 *
 * Uso:
 * ```kotlin
 * val library = cruxui.android.lib.theme.LibraryThemes.Maracuya.resolve(false)
 * val override = ButtonTokensOverride(iconStart = library.icons.alerts.info)
 * ```
 *
 * Consultar el contrato core y ButtonSimpleArchitecture.md para la integración completa.
 *
 * Objetivos:
 * - Mantener la misma identidad de tipo en Compose y XML.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
typealias ButtonTokensOverride =
    cruxui.android.lib.core.components.buttons.tokens.ButtonTokensOverride
