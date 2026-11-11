package cruxui.android.lib.wrappers.components.buttons.simple.render

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import cruxui.android.lib.core.components.buttons.tokens.ButtonLayoutTokens
import cruxui.android.lib.core.components.buttons.tokens.ButtonLoadingTokens
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.wrappers.components.buttons.simple.tokensrender.ButtonTokensRender
import cruxui.android.lib.wrappers.components.buttons.simple.variant.ButtonVariant
import cruxui.android.lib.wrappers.components.buttons.simple.variant.ButtonVariantResolver
import cruxui.android.lib.wrappers.components.textviews.TextView
import cruxui.android.lib.wrappers.components.textviews.tokens.TextViewNativeOverrides
import cruxui.android.lib.wrappers.core.resources.AndroidIconLoader
import kotlin.math.roundToInt

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonRenderer adapta el resultado semántico a superficies y contenido nativos.
 *
 * Justificación: El TextView del DS ya resuelve familia, tipografía, RTL, espacio e igual tinte de
 * iconos. No se reconstruye su layout.
 *
 * Se comunica con: Button aporta tema, capas textuales y estado resuelto; behavior anima el
 * ImageView por separado.
 *
 * Uso:
 * ```kotlin
 * // Dentro del módulo DS, con owner: View, label: TextView e indicator: ImageView:
 * val library = cruxui.android.lib.theme.LibraryThemes.Maracuya.resolve(dark = false)
 * val tokens = cruxui.android.lib.core.components.buttons.tokens.ButtonTokenDefaults.resolve(library)
 * val state = cruxui.android.lib.core.components.buttons.behavior.ButtonBehaviorResolver.resolve()
 * ButtonRenderer(label, indicator).render(
 *     owner = owner,
 *     library = library,
 *     tokens = cruxui.android.lib.core.components.buttons.tokensrender.ButtonTokensRenderResolver
 *         .resolve(tokens, state),
 *     variant = ButtonVariant.PRIMARY,
 *     textStyle = TextViewNativeOverrides(),
 *     loading = state.isLoading,
 *     radius = ButtonLayoutTokens.CornerRadiusDp * owner.resources.displayMetrics.density,
 *     useTokenBackground = true,
 * )
 * ```
 *
 * Objetivos:
 * - Dibujar datos resueltos y reutilizar la implementación textual.
 *
 * Flujo: Apariencia y comportamiento resueltos → superficies, TextView e indicador decorativo.
 *
 * @param label TextView DS contenido, sin acciones independientes.
 * @param indicator ImageView decorativo reservado para el indicador canónico.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal class ButtonRenderer(private val label: TextView, private val indicator: ImageView) {
    private val surface = GradientDrawable()
    private var previousText: Any? = null
    private var previousTheme: LibraryThemeSnapshot? = null
    private var previousVariant: ButtonVariant? = null
    private var previousStyle: TextViewNativeOverrides? = null

    init {
        indicator.setImageDrawable(
            AndroidIconLoader.load(indicator.context, ButtonLoadingTokens.Icon)
        )
    }

    /**
     * @param owner Superficie del botón, independiente del fondo del TextView.
     * @param library Snapshot que también se entrega al TextView interno.
     * @param tokens Apariencia resuelta según estado.
     * @param variant Variante textual requerida.
     * @param textStyle Capas nativas de tipografía/iconos; no decide colores del botón.
     * @param loading Oculta texto e iconos, pero conserva su medida.
     * @param radius Radio de esquina en píxeles.
     * @param useTokenBackground False conserva android:background o un drawable nativo del host.
     */
    fun render(
        owner: View,
        library: LibraryThemeSnapshot,
        tokens: ButtonTokensRender,
        variant: ButtonVariant,
        textStyle: TextViewNativeOverrides,
        loading: Boolean,
        radius: Float,
        useTokenBackground: Boolean,
    ) {
        surface.cornerRadius = radius
        surface.setColor(tokens.background.argb.toInt())
        surface.setStroke(
            (ButtonLayoutTokens.BorderWidthDp * owner.resources.displayMetrics.density)
                .roundToInt(),
            tokens.border.argb.toInt(),
        )
        if (useTokenBackground && owner.background !== surface) owner.background = surface
        if (
            previousTheme != library ||
                previousText != tokens.text ||
                previousVariant != variant ||
                previousStyle != textStyle
        ) {
            label.setThemeTokens(library)
            label.clearOverrides()
            label.setVariant(ButtonVariantResolver.resolve(variant))
            label.setTokensOverride(tokens.text)
            label.setNativeOverrides(textStyle)
            // Estado resuelto del botón: también fuerza el mismo tinte de sus compound drawables.
            label.setTextColor(requireNotNull(tokens.text.textColor).argb.toInt())
            previousTheme = library
            previousText = tokens.text
            previousVariant = variant
            previousStyle = textStyle
        }
        label.visibility = if (loading) View.INVISIBLE else View.VISIBLE
        indicator.visibility = if (loading) View.VISIBLE else View.INVISIBLE
        indicator.imageTintList =
            android.content.res.ColorStateList.valueOf(
                requireNotNull(tokens.text.textColor).argb.toInt()
            )
    }
}
