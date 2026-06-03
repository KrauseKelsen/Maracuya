package cruxui.android.maracuya.atoms

import cruxui.android.maracuya.atoms.icons.LibraryAlertsIconsTokens
import cruxui.android.maracuya.atoms.icons.LibraryArrowIconsTokens
import cruxui.android.maracuya.atoms.icons.LibraryExtensionTokens
import cruxui.android.maracuya.atoms.icons.LibraryGeneralIconsTokens
import cruxui.android.maracuya.atoms.icons.LibraryMoneyIconsTokens
import cruxui.android.maracuya.atoms.icons.LibraryPeopleIconsTokens

/**
 * Iconos atómicos: mapea a recursos drawables (vectoriales).
 * NO contiene colores — solo identifica archivos físicos.
 */
data class LibraryIconTokens(

    // GENERAL ICONS (172)
    val generalIconsLibrary : LibraryGeneralIconsTokens,
    //ALERTS ICONS (31)
    val alertsIconsLibrary : LibraryAlertsIconsTokens,
    //PEOPLE ICONS (62)
    val peopleIconsLibrary : LibraryPeopleIconsTokens,
    //ARROW ICONS (65)
    val arrowIconsLibrary: LibraryArrowIconsTokens,
    //MONEY ICONS (44)
    val moneyIconsLibrary: LibraryMoneyIconsTokens,

    val extensionLibrary: LibraryExtensionTokens,



) {
    companion object {
        val Default = LibraryIconTokens(

            generalIconsLibrary = LibraryGeneralIconsTokens.general,
            alertsIconsLibrary = LibraryAlertsIconsTokens.alerts,
            peopleIconsLibrary = LibraryPeopleIconsTokens.people,
            arrowIconsLibrary = LibraryArrowIconsTokens.arrows,
            moneyIconsLibrary = LibraryMoneyIconsTokens.money,
            extensionLibrary = LibraryExtensionTokens.extension

        )
    }
}
