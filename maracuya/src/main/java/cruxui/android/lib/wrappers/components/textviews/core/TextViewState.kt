package cruxui.android.lib.wrappers.components.textviews.core

import cruxui.android.lib.wrappers.components.textviews.tokens.TextViewNativeOverrides
import cruxui.android.lib.wrappers.components.textviews.variant.TextViewVariant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewState` conserva la configuración propia de una instancia del wrapper nativo.
 *
 * Justificación:
 * Separa el estado de variante, error, receta y parches de las capacidades de texto que ya mantiene Android.
 *
 * Se comunica con:
 * La View modifica sus campos; los resolutores consumen variant, error, overrideName y runtime.
 *
 * Uso:
 * Tipo interno del DS; no existe un estado global compartido. El host utiliza los setters de TextView.
 * El contenido, enabled y selección efectiva siguen perteneciendo a la View nativa.
 *
 * Objetivos:
 * - Mantener configuración por instancia.
 * - Facilitar resolución y limpieza de parches.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param variant Variante seleccionada.
 * @param error Estado visual de error.
 * @param overrideName Nombre de receta o null.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal class TextViewState(
    var variant: TextViewVariant,
    var error: Boolean,
    var overrideName: String?,
) {
    /**
     * Parche de ejecución acumulado por setters. Se sustituye por otra instancia al aplicar cambios o limpiar
     * overrides.
     */
    var runtime = TextViewNativeOverrides()
}
