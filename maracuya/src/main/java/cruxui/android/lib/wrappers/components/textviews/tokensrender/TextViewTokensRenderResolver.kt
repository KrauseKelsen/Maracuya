package cruxui.android.lib.wrappers.components.textviews.tokensrender

import cruxui.android.lib.wrappers.capabilities.text.NativeTextStyleResolver
import cruxui.android.lib.wrappers.core.resources.pixels
import cruxui.android.lib.wrappers.core.resources.resolve
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.graphics.drawable.toDrawable
import android.util.TypedValue
import cruxui.android.lib.core.components.textviews.tokensrender.TextViewTokensRenderResolver as SemanticRenderResolver
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.core.components.textviews.tokens.TextViewLayoutTokens
import cruxui.android.lib.wrappers.components.textviews.tokens.TextViewResolvedTokens
import cruxui.android.lib.wrappers.components.textviews.tokens.TextViewNativeOverrides
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableRender
import cruxui.android.lib.wrappers.core.resources.AndroidTokenAdapter
import cruxui.android.lib.wrappers.core.tokens.*
import kotlin.math.roundToInt

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensRenderResolver` adapta capas semánticas y nativas a los modelos de render Android.
 *
 * Justificación:
 * Mantiene la conversión de unidades, fuentes, colores y drawables fuera de la View y del renderer. No
 * selecciona temas ni consulta registros.
 *
 * Se comunica con:
 * Recibe LibraryThemeSnapshot y TextViewResolvedTokens, consulta AndroidTokenAdapter y el resolutor visual
 * compartido; produce TextViewTokensRender y TextViewColorRender.
 *
 * Uso:
 * Objeto interno del DS. TextView llama resolve para métricas y color para estados.
 * La capa de ejecución precede a XML y ambas a la receta. La tipografía base aporta métricas; los valores
 * nativos explícitos pueden ajustarlas. Los dos iconos siguen el color efectivo del texto.
 *
 * Objetivos:
 * - Convertir la semántica en valores Android.
 * - Aplicar precedencia sin leer CompositionLocals.
 * - Conservar la política común de estados.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal object TextViewTokensRenderResolver {
    /**
     * Resuelve fuente, dimensiones, tracking, alineación e iconos para el renderer nativo.
     *
     * @param context Context para densidad y carga de recursos.
     * @param library Biblioteca del ámbito de la instancia.
     * @param tokens Capas semántica, XML y de ejecución.
     * @return Modelo nativo de métricas e iconos; los colores se resuelven por separado.
     */
    fun resolve(context: Context, library: LibraryThemeSnapshot, tokens: TextViewResolvedTokens): TextViewTokensRender {
        val patch = tokens.native
        val start = icon(context, library, patch.iconStart, patch.showIconStart, tokens.semantic.iconStart)
        val end = icon(context, library, patch.iconEnd, patch.showIconEnd, tokens.semantic.iconEnd)
        return TextViewTokensRender(
            textStyle = NativeTextStyleResolver.resolve(context, library, tokens.semantic.typography,
                tokens.semantic.fontFamily, patch.textStyle()),
            iconStart = start,
            iconEnd = end,
            iconPaddingPx = (patch.iconPadding ?: TextDimension(TextViewLayoutTokens.IconSpacingDp, TypedValue.COMPLEX_UNIT_DIP))
                .pixels(context).roundToInt(),
        )
    }

    /**
     * Resuelve el drawable y si el DS gestiona ese lado. Uso privado.
     *
     * @param context Context para recursos.
     * @param library Biblioteca para selectores semánticos.
     * @param source Fuente explícita; None retira el icono.
     * @param show false lo oculta, true permite mostrarlo si existe.
     * @param base Icono de la receta/defaults cuando no hay fuente explícita.
     * @return Datos de dibujo; el tinte se aplica después con el color efectivo del texto.
     */
    private fun icon(context: Context, library: LibraryThemeSnapshot, source: ViewIcon?, show: Boolean?, base: IconToken?): CompoundDrawableRender {
        val token = if (show == false || source == ViewIcon.None) null else source?.resolve(library) ?: base

        return CompoundDrawableRender(source != null || show != null || base != null,
            token?.let { AndroidTokenAdapter.icon(context, it) }, null)
    }

    /**
     * Resuelve colores por estado y superficies con prioridad ejecución > XML > semántica.
     *
     * @param context Context para recursos.
     * @param library Biblioteca activa.
     * @param tokens Capas visuales.
     * @param enabled Estado habilitado.
     * @param error Estado de error, subordinado a disabled.
     * @param drawableState Estados nativos actuales para selectores y superficies.
     * @return ColorStateList y drawables listos para aplicar.
     */
    fun color(context: Context, library: LibraryThemeSnapshot, tokens: TextViewResolvedTokens,
        enabled: Boolean, error: Boolean, drawableState: IntArray,
    ): TextViewColorRender {
        val semantic = SemanticRenderResolver.resolve(tokens.semantic, enabled, error)
        /**
         * Selecciona el color específico de estado de una capa o su color general como respaldo.
         * Función local del resolutor: permite comparar ejecución y XML sin perder su precedencia.
         * @return Fuente para el estado actual, o null si la capa no aporta color.
         */
        fun TextViewNativeOverrides.source(): TextColor? = when {
            !enabled -> foregroundDisabled
            error -> foregroundError
            else -> foregroundDefault
        } ?: textColor
        val source = tokens.runtime.source() ?: tokens.xml.source()
        val colors = source?.resolve(context, library)
            ?: ColorStateList.valueOf(AndroidTokenAdapter.color(semantic.textColor))
        val patch = tokens.native
        return TextViewColorRender(
            colors,
            if (patch.background != null) patch.background.resolve(context, library, drawableState)
            else AndroidTokenAdapter.color(semantic.backgroundColor).toDrawable(),
            if (patch.foreground != null) patch.foreground.resolve(context, library, drawableState)
            else AndroidTokenAdapter.color(semantic.foregroundColor).toDrawable(),
        )
    }
}
