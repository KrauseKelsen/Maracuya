package cruxui.android.lib.wrappers.components.textviews.tokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokens` es un alias del contrato compartido
 * `cruxui.android.lib.core.components.textviews.tokens.TextViewTokens`.
 *
 * Justificación:
 * Conserva la ruta de importación de esta plataforma sin crear otra clase ni duplicar campos o reglas.
 *
 * Se comunica con:
 * Reutiliza directamente `cruxui.android.lib.core.components.textviews.tokens.TextViewTokens`; los
 * resolutores del componente trabajan con esa misma identidad de tipo.
 *
 * Uso:
 * API pública del host. El DS resuelve este modelo completo; el host configura la View con
 * TextViewTokensOverride o con setters. Este alias no añade un método setTokens ni un catálogo propio de XML.
 *
 * Objetivos:
 * - Compartir el modelo entre Compose y XML.
 * - Mantener una ruta de acceso clara para esta plataforma.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokens
 */
typealias TextViewTokens = cruxui.android.lib.core.components.textviews.tokens.TextViewTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewResolvedTokens` conserva las capas semántica, XML y de ejecución de una instancia.
 *
 * Justificación:
 * Evita perder la procedencia de valores al aplicar precedencia, especialmente al resolver colores de estado y
 * métricas tipográficas.
 *
 * Se comunica con:
 * Lo produce TextViewTokensResolver y lo consume TextViewTokensRenderResolver. Su propiedad native combina XML y
 * ejecución.
 *
 * Uso:
 * Tipo interno del DS. El host usa recetas y setters. El resolutor de colores consulta las capas por separado;
 * el de métricas utiliza native, donde la ejecución prevalece sobre XML.
 *
 * Objetivos:
 * - Conservar las entradas de cada capa.
 * - Permitir precedencia explícita y restauración.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param semantic Defaults compartidos ya combinados con la receta registrada.
 * @param xml Valores obtenidos de layout, estilos y TextAppearance.
 * @param runtime Parche acumulado por setters de ejecución.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal data class TextViewResolvedTokens(
    val semantic: TextViewTokens,
    val xml: TextViewNativeOverrides,
    val runtime: TextViewNativeOverrides,
) {
    /**
     * Resultado de xml.merge(runtime): el parche de ejecución tiene prioridad y conserva las reglas de
     * sustitución por grupo.
     */
    val native = xml.merge(runtime)
}
