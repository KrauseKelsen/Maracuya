package cruxui.android.lib.utils.composeadapters

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import cruxui.android.lib.tokens.base.ColorToken

/**
 * Adaptador único para convertir ColorToken -> Compose Color.
 */
object ColorComposeAdapter {
    fun toComposeColor(token: ColorToken): Color =
        Color(token.argb.toInt())

    fun toArgb(token: ColorToken): Int {
        val color = Color(token.red, token.green, token.blue)
        return color.toArgb()
    }
}