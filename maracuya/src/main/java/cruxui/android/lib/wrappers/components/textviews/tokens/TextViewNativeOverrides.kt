package cruxui.android.lib.wrappers.components.textviews.tokens

import cruxui.android.lib.wrappers.capabilities.text.NativeTextStyleOverrides
import cruxui.android.lib.wrappers.core.tokens.ViewIcon
import cruxui.android.lib.wrappers.core.tokens.TextColor
import cruxui.android.lib.wrappers.core.tokens.TextDimension
import cruxui.android.lib.wrappers.core.tokens.TextFont
import cruxui.android.lib.wrappers.core.tokens.TextLineSpacing
import cruxui.android.lib.wrappers.core.tokens.TextTypography
import cruxui.android.lib.wrappers.core.tokens.ViewSurface

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewNativeOverrides` describe personalizaciones que necesitan tipos o convenciones Android.
 *
 * Justificación:
 * Complementa el override semántico compartido sin introducir Context, recursos Android o Typeface dentro del
 * núcleo puro.
 *
 * Se comunica con:
 * Lo construyen TextViewAttributeReader, el adaptador toNativeOverrides y los setters de la View. merge combina
 * capas y el resolutor nativo las convierte a render.
 *
 * Uso:
 * API pública del host para necesidades nativas. Para una receta de marca preferir TextViewTokensOverride.
 * ```kotlin
 * view.setNativeOverrides(TextViewNativeOverrides(
 *     textColor = TextColor.Resource(R.color.my_text_selector),
 *     textSize = TextDimension(18f), // sp por defecto
 *     iconEnd = ViewIcon.Semantic { it.alerts.info },
 *     showIconEnd = true,
 * ))
 * ```
 * El recurso my_text_selector debe existir en la app. También se admiten TextFont.Resource/Value/Family/System,
 * TextTypography.Value/Role/Semantic y ViewSurface para fondos.
 * Todos los campos null significan ausencia. ViewIcon.None retira un icono y ViewSurface.Value(null) retira una
 * superficie.
 * lineHeight y lineSpacing no pueden declararse simultáneamente. El tracking nativo letterSpacingEm está en em;
 * el tracking de TypographyToken está en sp.
 * Un nuevo textColor sustituye los colores de estado de la capa anterior; los colores específicos presentes en
 * la misma capa prevalecen sobre ese color general.
 * Una nueva typography sustituye las métricas anteriores; campos específicos en ese mismo parche pueden
 * ajustarla. La familia font es independiente. Usar clearOverrides/clearTypographyOverrides/clearColorOverride
 * en la View para recuperar capas inferiores.
 *
 * Objetivos:
 * - Conservar fuentes de valores Android.
 * - Expresar ausencia y eliminación de forma distinta.
 * - Centralizar precedencia y validaciones.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param textColor Color general o selector nativo para todos los estados.
 * @param background Fuente del fondo; Value(null) lo elimina.
 * @param foreground Fuente de la capa superior; Value(null) la elimina.
 * @param foregroundDefault Color específico normal.
 * @param foregroundError Color específico de error.
 * @param foregroundDisabled Color específico deshabilitado.
 * @param typography Token o selector semántico que aporta las métricas base.
 * @param font Familia/recurso/Typeface nativos, independiente de typography.
 * @param textSize Tamaño con unidad, sp por defecto.
 * @param lineHeight Altura de línea con unidad; excluyente con lineSpacing.
 * @param lineSpacing Espacio adicional en px y multiplicador; excluyente con lineHeight.
 * @param letterSpacingEm Tracking nativo finito en em.
 * @param weight Peso explícito entre 1 y 1000.
 * @param italic Cursiva explícita; false también es un override.
 * @param textAlignment Constante View.TEXT_ALIGNMENT_* explícita.
 * @param gravity Máscara Gravity nativa.
 * @param justificationMode Modo nativo de justificación.
 * @param iconStart Fuente del icono inicial; None lo retira.
 * @param iconEnd Fuente del icono final; None lo retira.
 * @param showIconStart Visibilidad explícita inicial.
 * @param showIconEnd Visibilidad explícita final.
 * @param iconPadding Separación entre iconos y texto con unidad.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
data class TextViewNativeOverrides(
    val textColor: TextColor? = null,
    val background: ViewSurface? = null,
    val foreground: ViewSurface? = null,
    val foregroundDefault: TextColor? = null,
    val foregroundError: TextColor? = null,
    val foregroundDisabled: TextColor? = null,
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
    val iconStart: ViewIcon? = null,
    val iconEnd: ViewIcon? = null,
    val showIconStart: Boolean? = null,
    val showIconEnd: Boolean? = null,
    val iconPadding: TextDimension? = null,
) {
    init {
        require(lineHeight == null || lineSpacing == null) { "Choose lineHeight or native lineSpacing" }
        require(weight == null || weight in 1..1000)
        require(letterSpacingEm == null || letterSpacingEm.isFinite())
    }

    /** Adapta la API plana al contrato de la capacidad tipográfica compartida. */
    internal fun textStyle() = NativeTextStyleOverrides(
        typography = typography,
        font = font,
        textSize = textSize,
        lineHeight = lineHeight,
        lineSpacing = lineSpacing,
        letterSpacingEm = letterSpacingEm,
        weight = weight,
        italic = italic,
        textAlignment = textAlignment,
        gravity = gravity,
        justificationMode = justificationMode,
    )

    /** Incorpora únicamente la capacidad tipográfica; conserva colores, superficies e iconos. */
    internal fun withTextStyle(style: NativeTextStyleOverrides) = copy(
        typography = style.typography,
        font = style.font,
        textSize = style.textSize,
        lineHeight = style.lineHeight,
        lineSpacing = style.lineSpacing,
        letterSpacingEm = style.letterSpacingEm,
        weight = style.weight,
        italic = style.italic,
        textAlignment = style.textAlignment,
        gravity = style.gravity,
        justificationMode = style.justificationMode,
    )

    /**
     * Combina este parche con otro de prioridad mayor sin mutar los originales.
     *
     * El nuevo color general retira colores específicos anteriores; una nueva tipografía retira métricas
     * anteriores.
     * Una altura de línea y un lineSpacing se sustituyen entre sí; un icono nuevo reinicia su visibilidad
     * anterior si no se aporta otra.
     * @param next Parche superior; sus campos específicos prevalecen dentro de la misma capa.
     * @return Parche combinado. Los campos null conservan valores salvo las sustituciones de grupo descritas.
     */
    fun merge(next: TextViewNativeOverrides): TextViewNativeOverrides {
        val style = textStyle().merge(next.textStyle())
        return TextViewNativeOverrides(
            textColor = next.textColor ?: textColor,
            background = next.background ?: background,
            foreground = next.foreground ?: foreground,
            foregroundDefault = next.foregroundDefault ?: foregroundDefault.takeIf { next.textColor == null },
            foregroundError = next.foregroundError ?: foregroundError.takeIf { next.textColor == null },
            foregroundDisabled = next.foregroundDisabled ?: foregroundDisabled.takeIf { next.textColor == null },
            typography = style.typography,
            font = style.font,
            textSize = style.textSize,
            lineHeight = style.lineHeight,
            lineSpacing = style.lineSpacing,
            letterSpacingEm = style.letterSpacingEm,
            weight = style.weight,
            italic = style.italic,
            textAlignment = style.textAlignment,
            gravity = style.gravity,
            justificationMode = style.justificationMode,
            iconStart = next.iconStart ?: iconStart,
            iconEnd = next.iconEnd ?: iconEnd,
            showIconStart = next.showIconStart ?: showIconStart.takeIf { next.iconStart == null },
            showIconEnd = next.showIconEnd ?: showIconEnd.takeIf { next.iconEnd == null },
            iconPadding = next.iconPadding ?: iconPadding,
        )
    }

    /**
     * Crea una copia sin color general ni colores específicos de texto.
     * Uso del DS al limpiar personalizaciones; el host puede utilizarlo al construir parches.
     * @return Copia que conserva fondos, tipografía, iconos y demás campos.
     */
    fun withoutColors() = copy(textColor = null, foregroundDefault = null, foregroundError = null, foregroundDisabled = null)
    /**
     * Crea una copia sin tipografía, fuente, métricas ni alineación/gravedad/justificación explícitas.
     * @return Parche que conserva colores, superficies e iconos.
     */
    fun withoutTypography() = copy(typography = null, font = null, textSize = null, lineHeight = null,
        lineSpacing = null, letterSpacingEm = null, weight = null, italic = null,
        textAlignment = null, gravity = null, justificationMode = null)
}
