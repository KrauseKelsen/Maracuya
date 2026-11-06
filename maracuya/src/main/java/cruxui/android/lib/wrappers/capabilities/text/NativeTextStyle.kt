package cruxui.android.lib.wrappers.capabilities.text

import android.graphics.Typeface
import cruxui.android.lib.wrappers.core.tokens.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * NativeTextStyle transporta las métricas Android resueltas desde NativeTextStyleResolver al renderer.
 *
 * Justificación:
 * Separa la elección de fuente y dimensiones de su aplicación al widget. No incorpora políticas
 * de color, iconos ni interacción, por lo que sirve también a Button y EditText.
 *
 * Uso:
 * Modelo interno del DS. NativeTextStyleRenderer consume esta instancia; el host configura tokens
 * u opciones nativas mediante el componente, sin construir este modelo.
 *
 * Objetivos:
 * - Conservar unidades y opciones tipográficas hasta el render nativo.
 * - Mantener una capacidad de texto independiente del componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param typeface Fuente efectiva, incluidos peso y estilo.
 * @param textSize Tamaño del texto y su unidad.
 * @param lineHeight Altura de línea efectiva cuando no se configura lineSpacing.
 * @param lineSpacing Espaciado Android explícito con prioridad sobre lineHeight.
 * @param letterSpacingEm Separación entre caracteres en em.
 * @param alignment Alineación semántica y propiedades nativas resueltas.
 */
internal data class NativeTextStyle(
    val typeface: Typeface,
    val textSize: TextDimension,
    val lineHeight: TextDimension,
    val lineSpacing: TextLineSpacing?,
    val letterSpacingEm: Float,
    val alignment: TextAlignmentRender,
)
