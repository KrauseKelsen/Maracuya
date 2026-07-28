package cruxui.android.maracuya.tokens.fonts

/**
 * Define los tokens puros de alineacion horizontal para textos.
 *
 * Estos valores no dependen de Compose y deben traducirse unicamente en
 * `TypographyComposeAdapter` cuando el token tipografico llega a UI.
 */
object TextAlignTokens {
    const val Unspecified = 0
    const val Left = 1
    const val Right = 2
    const val Center = 3
    const val Justify = 4
    const val Start = 5
    const val End = 6
}
