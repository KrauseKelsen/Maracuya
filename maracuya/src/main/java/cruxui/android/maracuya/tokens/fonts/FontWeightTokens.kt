package cruxui.android.maracuya.tokens.fonts

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Define los **pesos tipográficos atómicos** del Design System.
 * Estos valores representan exactamente los pesos usados en Figma
 * y sirven como la única fuente de verdad para construir los estilos
 * tipográficos superiores.
 *
 * Estos tokens representan la escala tipográfica base que se utiliza para construir:
 *
 *  - `fonts/FontSizeTokens` (tokens → tamaño, peso y estilo)
 *  - `atoms/LibraryTypographyTokens` (construcción semántica)
 *  - `semantics/CorporateTypography` (inyección final al tema)
 *
 * Importante:
 * - No deben ser accedidos directamente desde el host.
 * - No deben usarse directamente en componentes.
 * - Su uso es exclusivo para los niveles superiores del sistema tipográfico.
 *
 * Uso:
 * ```
 * // Interno únicamente
 * val weight = FontWeightTokens.Bold
 * ```
 *
 * @author Krause Kelsen
 * @since 10-31-2025
 * @version 1.5.4
 *
 * Pesos disponibles:
 * @param Headline  Equivalente a FontWeight.ExtraBold (800).
 * @param Bold      Equivalente a FontWeight.Bold (700).
 * @param Medium    Equivalente a FontWeight.Medium (500).
 * @param Regular   Equivalente a FontWeight.Normal (400).
 *
 * @see cruxui.android.maracuya.tokens.fonts.FontWeightTokens
 */
object FontWeightTokens {
    const val Headline = 800
    const val Bold     = 700
    const val Medium   = 500
    const val Regular  = 400
}
