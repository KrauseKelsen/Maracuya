package com.example.mylibrary.atoms

import com.example.mylibrary.atoms.icons.LibraryAlertsIconsTokens
import com.example.mylibrary.atoms.icons.LibraryArrowIconsTokens
import com.example.mylibrary.atoms.icons.LibraryGeneralIconsTokens
import com.example.mylibrary.atoms.icons.LibraryMoneyIconsTokens
import com.example.mylibrary.atoms.icons.LibraryPeopleIconsTokens

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
    val moneyIconsLibrary: LibraryMoneyIconsTokens



) {
    companion object {
        val Default = LibraryIconTokens(

            generalIconsLibrary = LibraryGeneralIconsTokens.general,
            alertsIconsLibrary = LibraryAlertsIconsTokens.alerts,
            peopleIconsLibrary = LibraryPeopleIconsTokens.people,
            arrowIconsLibrary = LibraryArrowIconsTokens.arrows,
            moneyIconsLibrary = LibraryMoneyIconsTokens.money

        )
    }
}
