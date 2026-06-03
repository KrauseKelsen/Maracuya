package cruxui.android.maracuya.tokens.icons

import cruxui.android.maracuya.atoms.LibraryIconTokens
import cruxui.android.maracuya.tokens.base.IconToken

data class ExtensionIcons(
    val lgDavibankHouseEmpty: IconToken
)

fun getExtensionIcons(atoms: LibraryIconTokens) = ExtensionIcons(
    lgDavibankHouseEmpty = atoms.extensionLibrary.davibankHouseEmpty
)