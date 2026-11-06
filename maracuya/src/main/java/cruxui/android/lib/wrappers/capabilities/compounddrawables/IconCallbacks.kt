package cruxui.android.lib.wrappers.capabilities.compounddrawables

import android.view.View

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `IconCallbacks` contiene las acciones y descripción de un único icono nativo.
 *
 * Justificación:
 * Evita que las acciones start/end se mezclen entre sí o con las del texto.
 *
 * Se comunica con:
 * Lo compone cada componente con iconos y alimenta CompoundDrawableBehaviorController.
 *
 * Uso:
 * Tipo interno del DS. El host asigna listeners y descripción usando los setters del lado correspondiente. Null
 * en un listener retira esa acción.
 *
 * Objetivos:
 * - Mantener acciones independientes por icono.
 * - Conservar una descripción para accesibilidad.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param onClick Acción sencilla o null.
 * @param onLongClick Acción larga que indica si consume el evento, o null.
 * @param contentDescription Descripción localizada para las acciones accesibles.
 * @see CompoundDrawableBehaviorController
 */
internal data class IconCallbacks(
    val onClick: View.OnClickListener? = null,
    val onLongClick: View.OnLongClickListener? = null,
    val contentDescription: CharSequence? = null,
)
