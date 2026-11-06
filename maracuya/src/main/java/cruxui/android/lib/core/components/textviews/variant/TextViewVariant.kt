package cruxui.android.lib.core.components.textviews.variant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewVariant` identifica las dos presentaciones semánticas del componente: DEFAULT e HIPERTEXT.
 *
 * Justificación:
 * Evita duplicar variantes entre Compose y Views y separar una variante visual de estados como error o
 * deshabilitado.
 *
 * Se comunica con:
 * `TextViewTokenDefaults` selecciona el color; cada `TextViewVariantResolver` aplica la decoración de su
 * plataforma.
 *
 * Uso:
 * API pública del host. En Compose declarar `variant = TextViewVariant.HIPERTEXT`; en Views llamar a
 * `setVariant(...)`.
 * En XML usar `app:variant="standard"` para DEFAULT o `app:variant="hipertext"` para HIPERTEXT.
 * HIPERTEXT aplica color de enlace y subrayado, pero no cambia el texto ni abre una URL. El host configura la
 * acción.
 *
 * Objetivos:
 * - Compartir la selección de presentación.
 * - Mantener los estados y callbacks independientes de la variante.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
enum class TextViewVariant {
    DEFAULT,
    HIPERTEXT,
}
