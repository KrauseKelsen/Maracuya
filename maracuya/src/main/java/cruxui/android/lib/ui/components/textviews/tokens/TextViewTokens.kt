package cruxui.android.lib.ui.components.textviews.tokens

import cruxui.android.lib.core.components.textviews.tokens.merge as sharedMerge

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokens` es un alias del contrato compartido
 * `cruxui.android.lib.core.components.textviews.tokens.TextViewTokens`.
 *
 * Justificación:
 * Conserva la ruta de importación de esta plataforma sin crear otra clase ni duplicar campos o reglas.
 *
 * Se comunica con:
 * Reutiliza directamente `cruxui.android.lib.core.components.textviews.tokens.TextViewTokens`; los
 * resolutores del componente trabajan con esa misma identidad de tipo.
 *
 * Uso:
 * API pública del host. Los resolutores del DS producen este modelo completo. El host usa normalmente
 * TextViewTokensOverride, pues TextView no recibe un parámetro de tokens completos. En integraciones propias
 * puede consultar TextViewTokensResolver.resolve dentro de composición.
 *
 * Objetivos:
 * - Compartir el modelo entre Compose y XML.
 * - Mantener una ruta de acceso clara para esta plataforma.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokens
 */
typealias TextViewTokens = cruxui.android.lib.core.components.textviews.tokens.TextViewTokens
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
 * API pública del host. Importar este alias para declarar `tokensOverride = TextViewTokensOverride(typography =
 * LibraryTheme.current.typography.headline24)`.
 * También acepta textColor, backgroundColor, foregroundColor, errorColor, disabledColor, fontFamily, iconStart e
 * iconEnd. Los campos null no sustituyen el valor previo. El mismo objeto se puede devolver desde una receta
 * XML.
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

/**
 * Delega la combinación al merge compartido del núcleo.
 *
 * Uso público de compatibilidad para integraciones que ya trabajan con tokens completos; el componente aplica el
 * merge por sí mismo.
 * @param override Sustituciones parciales; null conserva el receptor.
 * @return Tokens completos combinados sin modificar el receptor.
 */
fun TextViewTokens.merge(override: TextViewTokensOverride?): TextViewTokens = sharedMerge(override)
