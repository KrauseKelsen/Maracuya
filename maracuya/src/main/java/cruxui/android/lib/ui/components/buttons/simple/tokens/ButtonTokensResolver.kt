package cruxui.android.lib.ui.components.buttons.simple.tokens

import androidx.compose.runtime.Composable
import cruxui.android.lib.core.components.buttons.tokens.ButtonTokenDefaults
import cruxui.android.lib.theme.LibraryTheme
import cruxui.android.lib.ui.components.buttons.simple.variant.ButtonVariant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokensResolver lee el tema del ámbito Compose y delega la receta.
 *
 * Justificación: Aísla CompositionLocals; el core permanece independiente de UI.
 *
 * Se comunica con: LibraryTheme.current aporta el snapshot a ButtonTokenDefaults.
 *
 * Uso:
 * ```kotlin
 * // Dentro de MyLibraryTheme y una función @Composable:
 * val tokens = ButtonTokensResolver.resolve(ButtonVariant.SECONDARY)
 * ```
 *
 * El host normalmente entrega tokensOverride al Button directamente.
 *
 * Objetivos:
 * - Evitar fallbacks ajenos a Library.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
object ButtonTokensResolver {
    /** PRIMARY del tema actual; no almacena un tema global. */
    @get:Composable
    val default: ButtonTokens
        get() = resolve()

    /**
     * @param variant Receta semántica; PRIMARY por defecto.
     * @param override Sustituciones parciales de la pantalla.
     * @return Tokens efectivos antes de resolver estados.
     */
    @Composable
    fun resolve(
        variant: ButtonVariant = ButtonVariant.PRIMARY,
        override: ButtonTokensOverride? = null,
    ): ButtonTokens = ButtonTokenDefaults.resolve(LibraryTheme.current, variant, override)
}
