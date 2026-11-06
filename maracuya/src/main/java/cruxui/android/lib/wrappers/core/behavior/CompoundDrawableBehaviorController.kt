package cruxui.android.lib.wrappers.core.behavior

import android.widget.TextView

/** Posición lógica de un drawable; Android determina el lado físico según la dirección de lectura. */
enum class CompoundDrawableSlot { Start, End }

/**
 * Fachada de compatibilidad para consumidores existentes Kotlin y Java.
 * La implementación está en capabilities.compounddrawables; no contiene reglas de tokens o de componente.
 * Los componentes nuevos importan la capacidad directamente.
 */
class CompoundDrawableBehaviorController(
    view: TextView,
    canClick: (CompoundDrawableSlot) -> Boolean = { true },
    canLongClick: (CompoundDrawableSlot) -> Boolean = { true },
) : cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableBehaviorController(view, canClick, canLongClick)
