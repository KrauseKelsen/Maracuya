package com.example.mylibrary.tokens.icons

import com.example.mylibrary.atoms.LibraryIconTokens
import com.example.mylibrary.tokens.base.IconToken

data class ExtensionIcons(
    val lgDavibankHouseEmpty: IconToken
)

fun getExtensionIcons(atoms: LibraryIconTokens) = ExtensionIcons(
    lgDavibankHouseEmpty = atoms.extensionLibrary.davibankHouseEmpty
)