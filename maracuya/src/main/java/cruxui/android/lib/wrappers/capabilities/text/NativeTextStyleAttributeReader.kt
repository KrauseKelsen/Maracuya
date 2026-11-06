package cruxui.android.lib.wrappers.capabilities.text

import android.content.res.TypedArray
import android.graphics.Typeface
import cruxui.android.lib.wrappers.core.attributes.*
import cruxui.android.lib.wrappers.core.tokens.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextAttributeIndices identifica los atributos de texto dentro del styleable de un componente.
 *
 * Justificación:
 * Permite que NativeTextStyleAttributeReader lea cualquier tabla sin depender del R de un componente.
 *
 * Uso:
 * Interno del DS. El lector de atributos del componente construye esta tabla con índices de su
 * propio styleable y la entrega junto al TypedArray correspondiente. El host declara atributos XML.
 *
 * Objetivos:
 * - Compartir lectura tipográfica sin acoplar componentes.
 * - Mantener explícita la correspondencia entre índices y propiedades.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param textStyle Índice de android:textStyle.
 * @param fontFamily Índice de android:fontFamily.
 * @param lineHeight Índice de android:lineHeight.
 * @param lineSpacingExtra Índice de android:lineSpacingExtra.
 * @param lineSpacingMultiplier Índice de android:lineSpacingMultiplier.
 * @param textSize Índice de android:textSize.
 * @param letterSpacing Índice de android:letterSpacing.
 * @param weight Índice de android:textFontWeight.
 * @param textAlignment Índice de android:textAlignment.
 * @param gravity Índice de android:gravity.
 * @param justificationMode Índice de android:justificationMode.
 */
internal data class TextAttributeIndices(
    val textStyle: Int,
    val fontFamily: Int,
    val lineHeight: Int,
    val lineSpacingExtra: Int,
    val lineSpacingMultiplier: Int,
    val textSize: Int,
    val letterSpacing: Int,
    val weight: Int,
    val textAlignment: Int,
    val gravity: Int,
    val justificationMode: Int,
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * NativeTextStyleAttributeReader convierte atributos Android en NativeTextStyleOverrides.
 *
 * Justificación:
 * Centraliza la lectura de métricas preservando ausencia y unidades; TextAttributeIndices permite
 * reutilizarla con cualquier styleable. No conoce colores, iconos, recetas ni variantes.
 *
 * Uso:
 * Capacidad interna. El lector del componente obtiene el TypedArray, invoca read y después lo recicla.
 * El host configura los atributos Android de su widget; no accede al lector directamente.
 *
 * Objetivos:
 * - Distinguir valores omitidos de overrides explícitos.
 * - Interpretar fuentes, peso, espaciado y alineación de manera consistente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object NativeTextStyleAttributeReader {
    /** Decodifica el array del llamador; quien lo creó conserva la responsabilidad de reciclarlo. */
    fun read(a: TypedArray, indices: TextAttributeIndices): NativeTextStyleOverrides {
        val style = a.optionalInt(indices.textStyle)
        val fontIndex = indices.fontFamily
        val fontRes = a.getResourceId(fontIndex, 0).takeIf {
            it != 0 && a.resources.getResourceTypeName(it) == "font"
        } ?: 0
        val lineHeight = a.dimension(indices.lineHeight)
        val hasLineSpacing = a.hasValue(indices.lineSpacingExtra) ||
            a.hasValue(indices.lineSpacingMultiplier)
        return NativeTextStyleOverrides(
            font = if (fontRes != 0) TextFont.Resource(fontRes)
                else a.getString(fontIndex)?.let(TextFont::System),
            textSize = a.dimension(indices.textSize),
            lineHeight = lineHeight,
            lineSpacing = if (lineHeight == null && hasLineSpacing) TextLineSpacing(
                a.getDimension(indices.lineSpacingExtra, 0f),
                a.getFloat(indices.lineSpacingMultiplier, 1f),
            ) else null,
            letterSpacingEm = if (a.hasValue(indices.letterSpacing))
                a.getFloat(indices.letterSpacing, 0f) else null,
            weight = a.optionalInt(indices.weight)?.takeIf { it > 0 }
                ?: style?.let { if (it and Typeface.BOLD != 0) 700 else 400 },
            italic = style?.let { it and Typeface.ITALIC != 0 },
            textAlignment = a.optionalInt(indices.textAlignment),
            gravity = a.optionalInt(indices.gravity),
            justificationMode = a.optionalInt(indices.justificationMode),
        )
    }
}
