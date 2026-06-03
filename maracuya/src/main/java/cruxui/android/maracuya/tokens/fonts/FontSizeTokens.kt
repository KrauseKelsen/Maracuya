package cruxui.android.maracuya.tokens.fonts


/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Define los **tamaños tipográficos atómicos** del Design System, expresados en `float`.
 * Estos tokens representan la escala tipográfica base que se utiliza para construir:
 *
 *  - `fonts/FontSizeTokens` (tokens → tamaño, peso y estilo)
 *  - `atoms/LibraryTypographyTokens` (construcción semántica)
 *  - `semantics/CorporateTypography` (inyección final al tema)
 *
 * Esta escala debe mantenerse **sincronizada con el archivo de Foundations › Page: Tipografía
 * en Figma**, garantizando consistencia visual entre diseño y desarrollo.
 *
 * Importante:
 * - No deben modificarse desde el host.
 * - No deben utilizarse directamente en componentes.
 * - Su propósito es ser consumidos únicamente por los niveles superiores del sistema.
 *
 * Uso:
 * ```
 * // Interno, solo para construcción tipográfica
 * val titleSize = FontSizeTokens.s24.sp
 * ```
 *
 * @author Krause Kelsen
 * @since 10-31-2025
 * @version 1.5.4
 *
 * escala tipográfica:
 * @param s12
 * @param s14
 * @param s16
 * @param s18
 * @param s20
 * @param s21
 * @param s24
 * @param s28
 * @param s32
 * @param s36
 * @param s48
 *
 * @see cruxui.android.maracuya.tokens.fonts.FontSizeTokens
 */
object FontSizeTokens {
    const val s12 = 12f
    const val s14 = 14f
    const val s16 = 16f
    const val s18 = 18f
    const val s20 = 20f
    const val s21 = 21f
    const val s24 = 24f
    const val s28 = 28f
    const val s32 = 32f
    const val s36 = 36f
    const val s48 = 48f
}
