package cruxui.android.lib.ui.components.textviews.variant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewVariant` es un alias del contrato compartido
 * `cruxui.android.lib.core.components.textviews.variant.TextViewVariant`.
 *
 * Justificación:
 * Conserva la ruta de importación de esta plataforma sin crear otra clase ni duplicar campos o reglas.
 *
 * Se comunica con:
 * Reutiliza directamente `cruxui.android.lib.core.components.textviews.variant.TextViewVariant`; los
 * resolutores del componente trabajan con esa misma identidad de tipo.
 *
 * Uso:
 * API pública del host. En Compose pasar `variant = TextViewVariant.HIPERTEXT` para color de enlace y subrayado.
 * DEFAULT es la presentación normal. Ninguna variante cambia el texto ni ejecuta acciones por sí misma.
 *
 * Objetivos:
 * - Compartir el modelo entre Compose y XML.
 * - Mantener una ruta de acceso clara para esta plataforma.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.variant.TextViewVariant
 */
typealias TextViewVariant = cruxui.android.lib.core.components.textviews.variant.TextViewVariant
