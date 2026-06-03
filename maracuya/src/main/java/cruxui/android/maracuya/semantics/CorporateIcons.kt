package cruxui.android.maracuya.semantics

import cruxui.android.maracuya.atoms.LibraryIconTokens
import cruxui.android.maracuya.tokens.icons.AlertsIcons
import cruxui.android.maracuya.tokens.icons.ArrowIcons
import cruxui.android.maracuya.tokens.icons.ExtensionIcons
import cruxui.android.maracuya.tokens.icons.GeneralIcons
import cruxui.android.maracuya.tokens.icons.MoneyIcons
import cruxui.android.maracuya.tokens.icons.PeopleIcons
import cruxui.android.maracuya.tokens.icons.getAlertsIcons
import cruxui.android.maracuya.tokens.icons.getArrowIcons
import cruxui.android.maracuya.tokens.icons.getExtensionIcons
import cruxui.android.maracuya.tokens.icons.getGeneralIcons
import cruxui.android.maracuya.tokens.icons.getMoneyIcons
import cruxui.android.maracuya.tokens.icons.getPeopleIcons

/**
 * Mapeo semántico de iconos.
 * Convierte la capa atomica (files) en iconos con significado.
 */
data class CorporateIcons(
    val general: GeneralIcons,
    val alerts: AlertsIcons,
    val people: PeopleIcons,
    val arrows: ArrowIcons,
    val money: MoneyIcons,
    val extension: ExtensionIcons
) {

    companion object {

        fun fromAtoms(atoms: LibraryIconTokens): CorporateIcons {
            return CorporateIcons(
                general = getGeneralIcons(atoms),
                alerts = getAlertsIcons(atoms),
                people = getPeopleIcons(atoms),
                arrows = getArrowIcons(atoms),
                money = getMoneyIcons(atoms),
                extension = getExtensionIcons(atoms)
            )
        }
    }
}