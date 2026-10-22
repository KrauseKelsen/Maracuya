package cruxui.android.maracuya.utils.composeadapters

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import cruxui.android.maracuya.theme.FontResourceCatalog
import cruxui.android.maracuya.tokens.base.FontFamilyToken

/** Compose representation of the same bundled TTF catalog used by Android Views. */
object FontFamiliesComposeAdapter {
    val Mulish: FontFamily = create(FontFamilyToken.MULISH, setOf(400, 500, 700))
    val Davivienda: FontFamily = create(FontFamilyToken.DAVIVIENDA, setOf(400, 700))

    fun toCompose(token: FontFamilyToken): FontFamily = when (token) {
        FontFamilyToken.MULISH -> Mulish
        FontFamilyToken.DAVIVIENDA -> Davivienda
    }

    // Keep the existing Compose face selection; both adapters share physical resource mappings.
    private fun create(family: FontFamilyToken, weights: Set<Int>): FontFamily = FontFamily(
        FontResourceCatalog.faces(family).filter { !it.italic && it.weight in weights }.map { face ->
            Font(
                resId = face.resourceId,
                weight = FontWeight(face.weight),
                style = if (face.italic) FontStyle.Italic else FontStyle.Normal,
            )
        },
    )
}
