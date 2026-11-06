package cruxui.android.lib.wrappers.components.textviews.tokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensOverride` es un alias del contrato compartido
 * `cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride`.
 *
 * Justificación:
 * Conserva la ruta de importación de esta plataforma sin crear otra clase ni duplicar campos o reglas.
 *
 * Se comunica con:
 * Reutiliza directamente `cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride`; los
 * resolutores del componente trabajan con esa misma identidad de tipo.
 *
 * Uso:
 * API pública del host. Usar en `TextViewTokensOverrideRegistry.register` o `view.setTokensOverride`.
 * Ejemplo: `TextViewTokensOverride(typography = library.typography.headline24, textColor =
 * library.colors.brandPrimary)`.
 * Admite además backgroundColor, foregroundColor, errorColor, disabledColor, iconStart, iconEnd y fontFamily.
 * Todos son opcionales; null conserva valores inferiores.
 * Para recursos Android, Typeface, selectores o eliminación explícita de iconos usar TextViewNativeOverrides.
 *
 * Objetivos:
 * - Compartir el modelo entre Compose y XML.
 * - Mantener una ruta de acceso clara para esta plataforma.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride
 */
typealias TextViewTokensOverride = cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride
