package cruxui.android.maracuya.theme

import androidx.annotation.FontRes
import cruxui.android.maracuya.R
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import kotlin.math.abs

/** A physical font face shared by the Compose and Android View adapters. */
data class FontResourceFace(
    @param:FontRes val resourceId: Int,
    val weight: Int,
    val italic: Boolean = false,
)

/** Single catalog of bundled TTFs. Family XML files and duplicated platform catalogs are unnecessary. */
object FontResourceCatalog {
    private val mulish = listOf(
        FontResourceFace(R.font.mulish_extra_light, 200),
        FontResourceFace(R.font.mulish_extra_light_italic, 200, true),
        FontResourceFace(R.font.mulish_light, 300),
        FontResourceFace(R.font.mulish_light_italic, 300, true),
        FontResourceFace(R.font.mulish_regular, 400),
        FontResourceFace(R.font.mulish_italic, 400, true),
        FontResourceFace(R.font.mulish_medium, 500),
        FontResourceFace(R.font.mulish_medium_italic, 500, true),
        FontResourceFace(R.font.mulish_semi_bold, 600),
        FontResourceFace(R.font.mulish_semi_bold_italic, 600, true),
        FontResourceFace(R.font.mulish_bold, 700),
        FontResourceFace(R.font.mulish_bold_italic, 700, true),
        FontResourceFace(R.font.mulish_extra_bold, 800),
        FontResourceFace(R.font.mulish_extra_bold_italic, 800, true),
        FontResourceFace(R.font.mulish_black, 900),
        FontResourceFace(R.font.mulish_black_italic, 900, true),
    )

    private val davivienda = listOf(
        FontResourceFace(R.font.davivienda_light, 300),
        FontResourceFace(R.font.davivienda_light_italic, 300, true),
        FontResourceFace(R.font.davivienda_regular, 400),
        FontResourceFace(R.font.davivienda_italic, 400, true),
        FontResourceFace(R.font.davivienda_semi_bold, 600),
        FontResourceFace(R.font.davivienda_semi_bold_italic, 600, true),
        FontResourceFace(R.font.davivienda_bold, 700),
        FontResourceFace(R.font.davivienda_bold_italic, 700, true),
        FontResourceFace(R.font.davivienda_extra_bold, 800),
        FontResourceFace(R.font.davivienda_extra_bold_italic, 800, true),
    )

    fun faces(family: FontFamilyToken): List<FontResourceFace> = when (family) {
        FontFamilyToken.MULISH -> mulish
        FontFamilyToken.DAVIVIENDA -> davivienda
    }

    fun closest(family: FontFamilyToken, weight: Int = 400, italic: Boolean = false): FontResourceFace {
        require(weight in 1..1000) { "Font weight must be between 1 and 1000" }
        return faces(family).minBy { abs(it.weight - weight) + if (it.italic == italic) 0 else 1000 }
    }
}
