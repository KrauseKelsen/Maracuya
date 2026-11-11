package cruxui.android.lib.wrappers.components.buttons.simple.tokens

import cruxui.android.lib.wrappers.core.registry.TokensOverrideRegistry

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokensOverrideRegistry registra recetas de botón por nombre para layouts XML.
 *
 * Justificación: Cada receta recibe el snapshot de su View; no captura Activity ni almacena un tema
 * global.
 *
 * Se comunica con: ComponentTokenBinding resuelve este registro; la misma función puede usarse
 * desde Compose.
 *
 * Uso:
 * ```kotlin
 * ButtonTokensOverrideRegistry.register("confirm") { library: cruxui.android.lib.theme.LibraryThemeSnapshot ->
 *     ButtonTokensOverride(iconEnd = library.icons.alerts.info)
 * }
 * ```
 *
 * Registrar antes de inflar y usar app:buttonTokensOverride="confirm". Después de reemplazar una
 * receta, llamar button.refreshTheme(). unregister y clear retiran recetas; no cambian
 * automáticamente Views existentes.
 *
 * Objetivos:
 * - Compartir recetas por ámbito sin depender de Compose.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
object ButtonTokensOverrideRegistry : TokensOverrideRegistry<ButtonTokensOverride>()
