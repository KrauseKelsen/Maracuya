package cruxui.android.lib.wrappers.components.textviews.behavior

import cruxui.android.lib.wrappers.capabilities.compounddrawables.IconCallbacks
import android.view.View
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableSlot

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewCallbacks` reúne los listeners de texto e iconos de una instancia nativa.
 *
 * Justificación:
 * Mantiene los eventos separados de TextViewBehavior, que solo describe capacidades, y permite restaurar
 * listeners al alternar selección.
 *
 * Se comunica con:
 * TextView actualiza copias al usar setters; TextViewBehaviorResolver y los controladores leen las acciones
 * correspondientes.
 *
 * Uso:
 * Tipo interno del DS. El host utiliza setOnClickListener, setOnLongClickListener, setOnDoubleClickListener y
 * los setters de iconos de la View.
 *
 * Objetivos:
 * - Almacenar callbacks por instancia.
 * - Evitar que selección elimine su configuración.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param onClick Listener sencillo del texto.
 * @param onLongClick Listener largo del texto.
 * @param onDoubleClick Listener de doble toque del texto.
 * @param iconStart Acciones y descripción del icono inicial.
 * @param iconEnd Acciones y descripción del icono final.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal data class TextViewCallbacks(
    val onClick: View.OnClickListener? = null,
    val onLongClick: View.OnLongClickListener? = null,
    val onDoubleClick: View.OnClickListener? = null,
    val iconStart: IconCallbacks = IconCallbacks(),
    val iconEnd: IconCallbacks = IconCallbacks(),
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewBehaviorResolver` expresa las reglas nativas de habilitación y propiedad de gestos.
 *
 * Justificación:
 * Separa las decisiones de comportamiento del despacho de MotionEvent y los listeners.
 *
 * Se comunica con:
 * Consulta TextViewBehavior y TextViewCallbacks; TextView lo utiliza al activar gestos y sincronizar iconos.
 *
 * Uso:
 * Objeto interno del DS. El host configura enabled, selección y callbacks en la View.
 * Los gestos personalizados largos/dobles del texto solo están disponibles cuando está habilitado y no hay
 * selección; cada icono mantiene su propia configuración.
 *
 * Objetivos:
 * - Mantener reglas funcionales explícitas.
 * - Separar selección del texto y acciones de iconos.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal object TextViewBehaviorResolver {
    /** Exige una descripción localizada para cada icono visible con acciones, igual que Compose. */
    fun requireIconDescription(actions: IconCallbacks, visible: Boolean) {
        require(!visible || (actions.onClick == null && actions.onLongClick == null) ||
            !actions.contentDescription.isNullOrBlank()) {
            "Los iconos interactivos de TextView requieren una descripción localizada"
        }
    }
    /**
     * Evalúa la habilitación básica de una acción nativa.
     * @param enabled Estado del componente.
     * @return El mismo indicador enabled; no introduce restricciones adicionales.
     */
    fun canActivate(enabled: Boolean) = enabled
    /**
     * Determina si los gestos personalizados del texto están disponibles.
     * @param behavior Capacidad de selección solicitada.
     * @param enabled Estado del componente.
     * @return true solo cuando está habilitado y la selección no reserva los gestos.
     */
    fun customTextGestures(behavior: TextViewBehavior, enabled: Boolean) = enabled && !behavior.textSelectionEnabled
    /**
     * Selecciona los callbacks del lado relativo solicitado.
     * @param callbacks Configuración de eventos de la View.
     * @param slot Start o End.
     * @return Acciones y descripción del icono correspondiente.
     */
    fun icon(callbacks: TextViewCallbacks, slot: CompoundDrawableSlot) =
        if (slot == CompoundDrawableSlot.Start) callbacks.iconStart else callbacks.iconEnd
}
