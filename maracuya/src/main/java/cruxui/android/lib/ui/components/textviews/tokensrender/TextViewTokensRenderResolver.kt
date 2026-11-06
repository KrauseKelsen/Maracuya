package cruxui.android.lib.ui.components.textviews.tokensrender

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensRenderResolver` es un alias del contrato compartido
 * `cruxui.android.lib.core.components.textviews.tokensrender.TextViewTokensRenderResolver`.
 *
 * Justificación:
 * Conserva la ruta de importación de esta plataforma sin crear otra clase ni duplicar campos o reglas.
 *
 * Se comunica con:
 * Reutiliza directamente
 * `cruxui.android.lib.core.components.textviews.tokensrender.TextViewTokensRenderResolver`; los resolutores
 * del componente trabajan con esa misma identidad de tipo.
 *
 * Uso:
 * Alias interno del DS; el host no puede importarlo desde otro módulo. El TextView llama a resolve(tokens,
 * enabled, error). La política deshabilitado > error > normal pertenece al núcleo y no se redefine aquí.
 *
 * Objetivos:
 * - Compartir el modelo entre Compose y XML.
 * - Mantener una ruta de acceso clara para esta plataforma.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokensrender.TextViewTokensRenderResolver
 */
internal typealias TextViewTokensRenderResolver = cruxui.android.lib.core.components.textviews.tokensrender.TextViewTokensRenderResolver
