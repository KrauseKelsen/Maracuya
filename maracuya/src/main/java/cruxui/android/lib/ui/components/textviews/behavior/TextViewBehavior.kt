package cruxui.android.lib.ui.components.textviews.behavior

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
 * API pública del host. En Compose pasar `behavior = TextViewBehavior.Selectable` para permitir selección y
 * copia, o Default para desactivarlas. Los callbacks se declaran en TextView. La selección reserva pulsación
 * larga y doble toque del texto.
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
