package cruxui.android.maracuya.tokens.fonts

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Define los *tokens* de `line-height` utilizados en el sistema tipográfico.
 * En el sistema web original, el `line-height` se expresaba como **ratios**
 * (1.125, 1.25, 1.5). Aquí se mantienen esos mismos valores para garantizar
 * consistencia visual entre plataformas.
 *
 * En Jetpack Compose, el `lineHeight` se representa directamente en `sp`,
 * por lo que este objeto provee:
 *
 *  - Ratios base para cada categoría semántica (Tight, Short, Normal).
 *  - Una función auxiliar para convertir un `fontSize` en `sp` a su `lineHeight`
 *    equivalente, aplicando el ratio correspondiente.
 *
 * Uso:
 * ```
 * // Obtiene el line-height de un estilo de 16.sp usando el ratio Normal:
 * val lh = LibraryTypographyTokens.lineHeightFrom(16f, LineHeightTokens.NormalRatio)
 * ```
 *
 * No debe ser accedida directamente por el host — solo desde los estilos
 * tipográficos del Design System.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @param TightRatio 1.125f
 * @param ShortRatio 1.25f
 * @param NormalRatio 1.5f
 * @return
 *
 * @see cruxui.android.maracuya.tokens.fonts.LineHeightTokens
 */
object LineHeightTokens {
    /** Ratio equivalente a un line-height ajustado (tight). */
    const val TightRatio = 1.125f

    /** Ratio equivalente a un line-height corto. */
    const val ShortRatio = 1.25f

    /** Ratio estándar para lectura cómoda. */
    const val NormalRatio = 1.5f

    /**
     * Calcula el `lineHeight` final a partir de un tamaño de fuente en `sp`
     * multiplicado por el ratio tipográfico correspondiente.
     *
     * @return Line-height expresado en `sp`.
     */
    fun lineHeightFrom(fontSizeSp: Float, ratio: Float): Float = fontSizeSp * ratio
}
