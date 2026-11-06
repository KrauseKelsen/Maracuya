package cruxui.android.lib.wrappers.components.textviews.variant

import android.graphics.Paint

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewVariantResolver` adapta la decoración de variante a los flags de pintura Android.
 *
 * Justificación:
 * Permite aplicar o retirar subrayado sin borrar otros efectos nativos del texto.
 *
 * Se comunica con:
 * TextViewRenderer entrega los flags actuales y la variante; el resolutor devuelve los flags a aplicar.
 *
 * Uso:
 * Objeto interno del DS. El host usa app:variant o setVariant. DEFAULT retira únicamente UNDERLINE_TEXT_FLAG;
 * HIPERTEXT lo agrega.
 *
 * Objetivos:
 * - Adaptar la variante a Android.
 * - Preservar los demás flags de pintura.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal object TextViewVariantResolver {
    /**
     * Agrega o retira el bit de subrayado según la variante.
     * @param flags Flags actuales de la pintura.
     * @param variant Presentación solicitada.
     * @return Flags nuevos conservando todos los bits ajenos al subrayado.
     */
    fun paintFlags(flags: Int, variant: TextViewVariant): Int = when (variant) {
        TextViewVariant.DEFAULT -> flags and Paint.UNDERLINE_TEXT_FLAG.inv()
        TextViewVariant.HIPERTEXT -> flags or Paint.UNDERLINE_TEXT_FLAG
    }
}
