package cruxui.android.maracuya.tokens.fonts

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Define los tokens atómicos del estilo tipográfico (normal/italic)
 * sin depender de Compose.
 *
 * Esto permite que tipografía superior (LibraryTypographyTokens)
 * especifique estilo tipográfico sin usar booleanos.
 *
 * El adaptador en theme convertirá estos tokens a Compose.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
object FontStyleTokens {
    const val Normal = 0
    const val Italic = 1
    const val Bold = 2
}