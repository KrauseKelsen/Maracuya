package cruxui.android.lib.core.components.textviews.tokens

import cruxui.android.lib.core.components.textviews.variant.TextViewVariant
import cruxui.android.lib.theme.LibraryThemeSnapshot

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokenDefaults` resuelve la receta visual base del TextView a partir de una biblioteca y una variante.
 *
 * Justificación:
 * Centraliza las decisiones semánticas: texto normal o enlace, colores de error/deshabilitado, tipografía label
 * y ausencia inicial de iconos.
 *
 * Se comunica con:
 * Recibe `LibraryThemeSnapshot`, `TextViewVariant` y `TextViewTokensOverride`; entrega `TextViewTokens` a los
 * resolutores Compose y XML.
 *
 * Uso:
 * Es público para integraciones del DS y del host que ya dispongan de un snapshot. No lee CompositionLocals ni
 * un Context.
 * ```kotlin
 * val library = LibraryThemes.Maracuya.resolve(false)
 * val tokens = TextViewTokenDefaults.resolve(
 *     library = library,
 *     variant = TextViewVariant.HIPERTEXT,
 *     override = TextViewTokensOverride(typography = library.typography.body1),
 * )
 * ```
 * En una pantalla habitual usar directamente el TextView de la plataforma.
 *
 * Objetivos:
 * - Mantener una sola receta semántica.
 * - Aplicar el override después de elegir la variante.
 * - Permitir resolución determinista sin UI.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
object TextViewTokenDefaults {
    /**
     * Construye los defaults de variante y combina el override al final.
     *
     * @param library Colecciones activas, ya resueltas para una marca y modo.
     * @param variant DEFAULT usa fgDefault; HIPERTEXT usa linkDefault.
     * @param override Campos que sustituyen los defaults, o null.
     * @return Tokens completos; el color de estado se elige posteriormente.
     */
    fun resolve(
        library: LibraryThemeSnapshot,
        variant: TextViewVariant = TextViewVariant.DEFAULT,
        override: TextViewTokensOverride? = null,
    ): TextViewTokens = TextViewTokens(
        textColor = if (variant == TextViewVariant.HIPERTEXT) library.colors.linkDefault else library.colors.fgDefault,
        backgroundColor = library.colors.transparent,
        errorColor = library.colors.fgError,
        foregroundColor = library.colors.transparent,
        disabledColor = library.colors.fgSubtle,
        iconStart = null,
        iconEnd = null,
        typography = library.typography.label,
        fontFamily = library.fontFamily,
    ).merge(override)
}
