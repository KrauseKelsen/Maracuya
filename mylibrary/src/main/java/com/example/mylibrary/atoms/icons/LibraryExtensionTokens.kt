package com.example.mylibrary.atoms.icons

import com.example.mylibrary.R
import com.example.mylibrary.tokens.base.IconToken

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