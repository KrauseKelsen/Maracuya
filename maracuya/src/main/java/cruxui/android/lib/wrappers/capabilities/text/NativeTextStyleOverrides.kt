package cruxui.android.lib.wrappers.capabilities.text

import cruxui.android.lib.wrappers.core.tokens.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * NativeTextStyleOverrides agrupa únicamente personalizaciones de tipografía nativa.
 *
 * Justificación:
 * Button, Label, TextField y TextView pueden compartir métricas sin compartir tokens, variantes o behavior.
 * La precedencia tipográfica queda en un único lugar; los componentes deciden cómo combinar sus otras capas.
 *
 * Uso:
 * API reutilizable por overrides nativos del host. Los componentes nuevos pueden exponer una propiedad
 * textStyle de este tipo. TextView conserva su API plana y la adapta internamente. Null significa ausencia.
 * merge recibe la capa de mayor prioridad; una tipografía nueva retira las métricas anteriores.
 *
 * @param typography Estilo semántico o selector que aporta las métricas base.
 * @param font Familia, recurso o Typeface explícitos; independiente de typography.
 * @param textSize Tamaño con unidad; null conserva el tamaño semántico.
 * @param lineHeight Altura de línea; excluyente con lineSpacing.
 * @param lineSpacing Espaciado adicional y multiplicador; excluyente con lineHeight.
 * @param letterSpacingEm Tracking nativo finito expresado en em.
 * @param weight Peso entre 1 y 1000; null conserva el peso semántico.
 * @param italic Cursiva explícita; false también sustituye el valor base.
 * @param textAlignment Alineación nativa View.TEXT_ALIGNMENT_*.
 * @param gravity Gravedad nativa; el render conserva el eje vertical de base.
 * @param justificationMode Modo Android de justificación del párrafo.
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
data class NativeTextStyleOverrides(
    val typography: TextTypography? = null,
    val font: TextFont? = null,
    val textSize: TextDimension? = null,
    val lineHeight: TextDimension? = null,
    val lineSpacing: TextLineSpacing? = null,
    val letterSpacingEm: Float? = null,
    val weight: Int? = null,
    val italic: Boolean? = null,
    val textAlignment: Int? = null,
    val gravity: Int? = null,
    val justificationMode: Int? = null,
) {
    init {
        require(lineHeight == null || lineSpacing == null)
        require(weight == null || weight in 1..1000)
        require(letterSpacingEm == null || letterSpacingEm.isFinite())
    }

    /** Combina capas conservando exclusión de altura/espaciado y precedencia del párrafo. */
    fun merge(next: NativeTextStyleOverrides) = NativeTextStyleOverrides(
        typography = next.typography ?: typography,
        font = next.font ?: font,
        textSize = next.textSize ?: textSize.takeIf { next.typography == null },
        lineHeight = if (next.lineSpacing != null) null else next.lineHeight ?: lineHeight.takeIf { next.typography == null },
        lineSpacing = if (next.lineHeight != null) null else next.lineSpacing ?: lineSpacing.takeIf { next.typography == null },
        letterSpacingEm = next.letterSpacingEm ?: letterSpacingEm.takeIf { next.typography == null },
        weight = next.weight ?: weight.takeIf { next.typography == null },
        italic = next.italic ?: italic.takeIf { next.typography == null },
        textAlignment = next.textAlignment ?: textAlignment.takeIf { next.typography == null && next.gravity == null },
        gravity = next.gravity ?: gravity.takeIf { next.typography == null },
        justificationMode = next.justificationMode ?: justificationMode.takeIf { next.typography == null },
    )
}
