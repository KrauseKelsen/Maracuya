package cruxui.android.lib.core.components.textviews.tokensrender

import cruxui.android.lib.core.components.textviews.tokens.TextViewTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensRenderResolver` selecciona los colores efectivos del texto y los iconos según enabled y error.
 *
 * Justificación:
 * Implementa una sola política de prioridad para que los estados tengan el mismo significado en ambos
 * componentes.
 *
 * Se comunica con:
 * Recibe `TextViewTokens` y produce `TextViewTokensRender`; los adaptadores de plataforma consumen el resultado.
 *
 * Uso:
 * Uso interno del DS; el host configura `enabled`/`error` en la API pública.
 * El orden es deshabilitado, error y normal. Fondo, capa superior, iconos y tipografía se conservan.
 *
 * Objetivos:
 * - Compartir la prioridad de estados.
 * - Mantener el tinte de iconos coherente con el texto.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal object TextViewTokensRenderResolver {

    /**
     * Resuelve el color de contenido con prioridad disabled > error > normal.
     *
     * @param tokens Configuración visual completa.
     * @param enabled Indica si el componente permite interacción.
     * @param error Indica el estado de error; no precede a disabled.
     * @return Valores semánticos finales, sin conversiones a tipos de UI.
     */
    fun resolve(
        tokens: TextViewTokens,
        enabled: Boolean,
        error: Boolean,
    ): TextViewTokensRender {
        val effectiveContentColor = when {
            !enabled -> tokens.disabledColor
            error -> tokens.errorColor
            else -> tokens.textColor
        }

        return TextViewTokensRender(
            textColor = effectiveContentColor,
            backgroundColor = tokens.backgroundColor,
            foregroundColor = tokens.foregroundColor,
            iconStartColor = effectiveContentColor,
            iconEndColor = effectiveContentColor,
            iconStart = tokens.iconStart,
            iconEnd = tokens.iconEnd,
            typography = tokens.typography,
            fontFamily = tokens.fontFamily,
        )
    }
}
