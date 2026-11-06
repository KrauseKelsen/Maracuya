package cruxui.android.lib.wrappers.components.textviews.variant

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
 * API pública del host. Usar `view.setVariant(TextViewVariant.HIPERTEXT)` o `app:variant="hipertext"`. DEFAULT
 * se declara `standard` en XML porque default es una palabra reservada de Java. El texto y la acción permanecen
 * a cargo del host.
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
