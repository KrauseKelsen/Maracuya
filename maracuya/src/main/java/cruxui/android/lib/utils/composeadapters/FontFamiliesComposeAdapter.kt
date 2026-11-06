package cruxui.android.lib.utils.composeadapters

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import cruxui.android.lib.theme.FontResourceCatalog
import cruxui.android.lib.tokens.base.FontFamilyToken

/** Compose representation of the same bundled TTF catalog used by Android Views. */
object FontFamiliesComposeAdapter {
    val Mulish: FontFamily = create(FontFamilyToken.MULISH)
    val Davivienda: FontFamily = create(FontFamilyToken.DAVIVIENDA)

    fun toCompose(token: FontFamilyToken): FontFamily = when (token) {
        FontFamilyToken.MULISH -> Mulish
        FontFamilyToken.DAVIVIENDA -> Davivienda
    }

    // Both renderers use every declared weight and italic face from the same catalog.
    private fun create(family: FontFamilyToken): FontFamily = FontFamily(
        FontResourceCatalog.faces(family).map { face ->
            Font(
                resId = face.resourceId,
                weight = FontWeight(face.weight),
                style = if (face.italic) FontStyle.Italic else FontStyle.Normal,
            )
        },
    )
}
