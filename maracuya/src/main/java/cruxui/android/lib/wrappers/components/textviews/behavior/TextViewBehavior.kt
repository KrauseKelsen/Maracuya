package cruxui.android.lib.wrappers.components.textviews.behavior

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewBehavior` es un alias del contrato compartido
 * `cruxui.android.lib.core.components.textviews.behavior.TextViewBehavior`.
 *
 * Justificación:
 * Conserva la ruta de importación de esta plataforma sin crear otra clase ni duplicar campos o reglas.
 *
 * Se comunica con:
 * Reutiliza directamente `cruxui.android.lib.core.components.textviews.behavior.TextViewBehavior`; los
 * resolutores del componente trabajan con esa misma identidad de tipo.
 *
 * Uso:
 * API pública del host. En Views llamar `view.setBehavior(TextViewBehavior.Selectable)` o
 * `view.setTextIsSelectable(true)`. En XML declarar android:textIsSelectable. La selección reserva gestos del
 * texto; los listeners se asignan en la View y no en este objeto.
 *
 * Objetivos:
 * - Compartir el modelo entre Compose y XML.
 * - Mantener una ruta de acceso clara para esta plataforma.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.behavior.TextViewBehavior
 */
typealias TextViewBehavior = cruxui.android.lib.core.components.textviews.behavior.TextViewBehavior
