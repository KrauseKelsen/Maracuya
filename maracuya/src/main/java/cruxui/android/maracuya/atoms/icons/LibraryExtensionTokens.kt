package cruxui.android.maracuya.atoms.icons

import cruxui.android.maracuya.R
import cruxui.android.maracuya.tokens.base.IconToken

data class LibraryExtensionTokens (
    // GENERAL ICONS (172)
    val davibankHouseEmpty: IconToken
) {
    companion object {
        val extension = LibraryExtensionTokens(
            davibankHouseEmpty = IconToken("logo_davibank_house_empty", R.drawable.logo_davibank_house_empty)
        )
    }
}