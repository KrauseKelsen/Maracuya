package cruxui.android.lib.wrappers.components.textviews.tokens

import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
import cruxui.android.lib.wrappers.components.textviews.variant.TextViewVariant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensResolver` prepara las capas visuales del componente XML.
 *
 * Justificación:
 * Mantiene la receta semántica compartida y separa los valores Android para que el render aplique precedencia
 * sin duplicar defaults.
 *
 * Se comunica con:
 * Invoca TextViewTokenDefaults con biblioteca, variante y receta. Empaqueta el resultado junto con XML y
 * ejecución en TextViewResolvedTokens.
 *
 * Uso:
 * Objeto interno del DS. La View llama resolve durante su render. El host utiliza el registro y los setters; no
 * tiene acceso a este resolutor interno.
 *
 * Objetivos:
 * - Reutilizar defaults Compose/XML.
 * - Conservar las capas Android por separado.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal object TextViewTokensResolver {
    /**
     * Resuelve la base semántica y conserva las capas nativas.
     *
     * @param library Tema del ámbito de la View.
     * @param variant Variante seleccionada.
     * @param recipe Override obtenido del registro, o null.
     * @param xml Atributos y estilos ya decodificados.
     * @param runtime Parches de ejecución acumulados.
     * @return Capas listas para adaptar a valores de render.
     */
    fun resolve(
        library: LibraryThemeSnapshot,
        variant: TextViewVariant,
        recipe: TextViewTokensOverride?,
        xml: TextViewNativeOverrides,
        runtime: TextViewNativeOverrides,
    ): TextViewResolvedTokens = TextViewResolvedTokens(
        TextViewTokenDefaults.resolve(library, variant, recipe),
        xml, runtime,
    )
}
