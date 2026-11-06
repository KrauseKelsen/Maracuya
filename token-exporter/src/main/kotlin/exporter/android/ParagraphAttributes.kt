package exporter.android

import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.tokens.fonts.TextAlignTokens

/** Traduce alineación semántica al vocabulario de párrafo Android; no decide variantes. */
internal fun StringBuilder.appendParagraphAlignment(token: TypographyToken) {
    val alignment = when (token.textAlign) {
        TextAlignTokens.Left, TextAlignTokens.Right -> "gravity"
        TextAlignTokens.Center -> "center"
        TextAlignTokens.Start, TextAlignTokens.Justify -> "textStart"
        TextAlignTokens.End -> "textEnd"
        else -> null
    }
    if (alignment != null) appendLine("        <item name=\"android:textAlignment\">$alignment</item>")
    if (token.textAlign == TextAlignTokens.Left || token.textAlign == TextAlignTokens.Right) {
        val gravity = if (token.textAlign == TextAlignTokens.Left) "left" else "right"
        appendLine("        <item name=\"android:gravity\">$gravity</item>")
    }
    if (token.textAlign == TextAlignTokens.Justify)
        appendLine("        <item name=\"android:justificationMode\">inter_word</item>")
}
