package cruxui.android.lib.ui.components.buttons.simple.variant

import cruxui.android.lib.core.components.textviews.variant.TextViewVariant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonVariantResolver selecciona la variante del TextView contenido.
 *
 * Justificación: Ninguna variante de Button es hipertexto. Todas delegan el contenido en TextView
 * DEFAULT.
 *
 * Se comunica con: El render recibe este mapeo junto al override de tipografía buttons.
 *
 * Uso:
 * ```kotlin
 * // Dentro del módulo DS:
 * val textVariant = ButtonVariantResolver.resolve(ButtonVariant.TERCIARY)
 * ```
 *
 * Objetivos:
 * - Reutilizar el comportamiento visual del TextView existente.
 *
 * Flujo: Variante del botón → contenido TextView DEFAULT, sin subrayado.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal object ButtonVariantResolver {
    /** @param variant Receta del botón. @return Variante textual del DS, sin alterar colores. */
    fun resolve(variant: ButtonVariant): TextViewVariant =
        when (variant) {
            ButtonVariant.PRIMARY,
            ButtonVariant.SECONDARY,
            ButtonVariant.TERCIARY -> TextViewVariant.DEFAULT
        }
}
