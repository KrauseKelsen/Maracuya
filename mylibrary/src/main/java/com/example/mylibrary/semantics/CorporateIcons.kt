package com.example.mylibrary.semantics

import com.example.mylibrary.atoms.LibraryIconTokens
import com.example.mylibrary.tokens.icons.AlertsIcons
import com.example.mylibrary.tokens.icons.ArrowIcons
import com.example.mylibrary.tokens.icons.GeneralIcons
import com.example.mylibrary.tokens.icons.MoneyIcons
import com.example.mylibrary.tokens.icons.PeopleIcons
import com.example.mylibrary.tokens.icons.getAlertsIcons
import com.example.mylibrary.tokens.icons.getArrowIcons
import com.example.mylibrary.tokens.icons.getGeneralIcons
import com.example.mylibrary.tokens.icons.getMoneyIcons
import com.example.mylibrary.tokens.icons.getPeopleIcons

/**
 * Mapeo semántico de iconos.
 * Convierte la capa atomica (files) en iconos con significado.
 */
data class CorporateIcons(
    val general: GeneralIcons,
    val alerts: AlertsIcons,
    val people: PeopleIcons,
    val arrows: ArrowIcons,
    val money: MoneyIcons
) {

    companion object {

        fun fromAtoms(atoms: LibraryIconTokens): CorporateIcons {
            return CorporateIcons(
                general = getGeneralIcons(atoms),
                alerts = getAlertsIcons(atoms),
                people = getPeopleIcons(atoms),
                arrows = getArrowIcons(atoms),
                money = getMoneyIcons(atoms)
            )
        }
    }
}