package cruxui.android.lib.core.components.textviews.tokensrender

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensRender` transporta los valores semánticos después de resolver el estado visual.
 *
 * Justificación:
 * Permite que los adaptadores reciban colores finales sin volver a decidir qué estado tiene prioridad.
 *
 * Se comunica con:
 * Lo construye `TextViewTokensRenderResolver`; el contenido Compose y el adaptador nativo convierten sus tokens
 * a tipos de plataforma.
 *
 * Uso:
 * Tipo interno del DS. El host no puede importarlo ni debe construirlo.
 * El flujo interno obtiene este resultado con `TextViewTokensRenderResolver.resolve(tokens, enabled, error)` y
 * después adapta cada token.
 *
 * Objetivos:
 * - Transportar el resultado del estado visual.
 * - Mantener la resolución independiente de Android y Compose.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param textColor Color del texto en estado normal; error y deshabilitado tienen tokens propios.
 * @param backgroundColor Color de fondo del componente.
 * @param foregroundColor Color de la capa visual dibujada sobre el contenido.
 * @param iconStartColor Color efectivo del icono; coincide con el del texto en el estado resuelto.
 * @param iconEndColor Color efectivo del icono; coincide con el del texto en el estado resuelto.
 * @param iconStart Icono inicial relativo a la dirección de lectura; null representa ausencia en los tokens
 *     resueltos.
 * @param iconEnd Icono final relativo a la dirección de lectura; null representa ausencia en los tokens
 *     resueltos.
 * @param typography Estilo semántico con tamaño, altura de línea, tracking, peso, cursiva y alineación.
 * @param fontFamily Familia corporativa independiente de las métricas de typography.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal data class TextViewTokensRender(
    val textColor: ColorToken,
    val backgroundColor: ColorToken,
    val foregroundColor: ColorToken,
    val iconStartColor: ColorToken,
    val iconEndColor: ColorToken,
    val iconStart: IconToken?,
    val iconEnd: IconToken?,
    val typography: TypographyToken,
    val fontFamily: FontFamilyToken,
)
