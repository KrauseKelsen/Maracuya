package cruxui.android.lib.wrappers.components.buttons.simple.tokens

import cruxui.android.lib.core.components.buttons.tokens.ButtonTokenDefaults
import cruxui.android.lib.core.components.buttons.tokens.merge
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.wrappers.components.buttons.simple.variant.ButtonVariant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonTokensResolver ordena las capas semánticas del botón nativo.
 *
 * Justificación: Mantiene defaults → receta → XML → runtime; no duplica el catálogo ni mezcla
 * lectura de atributos con render.
 *
 * Se comunica con: ButtonTokenDefaults aporta la base y merge conserva los campos ausentes de cada
 * capa.
 *
 * Uso:
 * ```kotlin
 * // Dentro del módulo DS:
 * val tokens = ButtonTokensResolver.resolve(library, variant, recipe, xml, runtime)
 * ```
 *
 * Objetivos:
 * - Preservar las personalizaciones al refrescar el tema.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal object ButtonTokensResolver {
    /**
     * @param library Tema de la instancia.
     * @param variant Receta seleccionada.
     * @param recipe Parche registrado, o null.
     * @param xml Colores explícitos decodificados del layout.
     * @param runtime Último override del host; null lo retira.
     * @return Receta semántica completa sin estado aplicado.
     */
    fun resolve(
        library: LibraryThemeSnapshot,
        variant: ButtonVariant,
        recipe: ButtonTokensOverride?,
        xml: ButtonTokensOverride?,
        runtime: ButtonTokensOverride?,
    ): ButtonTokens =
        ButtonTokenDefaults.resolve(library, variant, recipe).merge(xml).merge(runtime)
}
