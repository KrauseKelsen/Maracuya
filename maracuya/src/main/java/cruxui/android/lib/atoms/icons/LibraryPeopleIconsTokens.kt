package cruxui.android.lib.atoms.icons

import cruxui.android.lib.tokens.base.IconToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryPeopleIconsTokens declara los IconToken de una categoría Library.
 *
 * Justificación:
 * Es la única declaración de los iconos de esta categoría. LibraryIconTokens la incorpora al tema;
 * token-exporter descubre todos sus getters para generar roles, referencias XML y consultas sin duplicarlos.
 *
 * Uso:
 * API pública de configuración. Personalizar con copy(nombreDelIcono = otroToken) y entregar la categoría
 * al campo correspondiente de LibraryIconTokens; LibraryThemes consume esa colección.
 * El host normalmente lee library.icons.<grupo>.<icono> desde LibraryTheme.current o una receta.
 * Para ampliar el DS agregar un val IconToken al constructor y su valor en el catálogo del companion.
 * resourceName debe nombrar un drawable existente en res/drawable, sin extensión ni prefijo @drawable/.
 * Recompilar genera LibraryIconRole, LibraryXmlReferences.icons y ?attr/libraryIcon<Grupo><Icono>.
 * Cambiar el recurso del token existente actualiza ambos consumidores sin modificar el exportador.
 * Los recursos propios del host mediante filledResId son solo de ejecución, no exportables por este módulo.
 *
 * Objetivos:
 * - Mantener nombres, assets y personalizaciones en una única fuente.
 * - Evitar mapeos por icono en semantics o wrappers.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.atoms.LibraryIconTokens
 */
data class LibraryPeopleIconsTokens(

    /** Token accessibleMenu; puede sustituirse al configurar la categoría del tema. */
    val accessibleMenu: IconToken,
    /** Token accessibility; puede sustituirse al configurar la categoría del tema. */
    val accessibility: IconToken,
    /** Token accessibilityNew; puede sustituirse al configurar la categoría del tema. */
    val accessibilityNew: IconToken,
    /** Token accessible; puede sustituirse al configurar la categoría del tema. */
    val accessible: IconToken,
    /** Token accessibleForward; puede sustituirse al configurar la categoría del tema. */
    val accessibleForward: IconToken,
    /** Token accountChildInvert; puede sustituirse al configurar la categoría del tema. */
    val accountChildInvert: IconToken,
    /** Token accountCircle; puede sustituirse al configurar la categoría del tema. */
    val accountCircle: IconToken,
    /** Token accountCircleOff; puede sustituirse al configurar la categoría del tema. */
    val accountCircleOff: IconToken,
    /** Token artist; puede sustituirse al configurar la categoría del tema. */
    val artist: IconToken,
    /** Token assistWalker; puede sustituirse al configurar la categoría del tema. */
    val assistWalker: IconToken,
    /** Token babyChangingStation; puede sustituirse al configurar la categoría del tema. */
    val babyChangingStation: IconToken,
    /** Token blind; puede sustituirse al configurar la categoría del tema. */
    val blind: IconToken,
    /** Token bodySystem; puede sustituirse al configurar la categoría del tema. */
    val bodySystem: IconToken,
    /** Token boy; puede sustituirse al configurar la categoría del tema. */
    val boy: IconToken,
    /** Token coPresent; puede sustituirse al configurar la categoría del tema. */
    val coPresent: IconToken,
    /** Token communication; puede sustituirse al configurar la categoría del tema. */
    val communication: IconToken,
    /** Token conditions; puede sustituirse al configurar la categoría del tema. */
    val conditions: IconToken,
    /** Token directionsRun; puede sustituirse al configurar la categoría del tema. */
    val directionsRun: IconToken,
    /** Token directionsWalk; puede sustituirse al configurar la categoría del tema. */
    val directionsWalk: IconToken,
    /** Token diversity1; puede sustituirse al configurar la categoría del tema. */
    val diversity1: IconToken,
    /** Token diversity2; puede sustituirse al configurar la categoría del tema. */
    val diversity2: IconToken,
    /** Token diversity3; puede sustituirse al configurar la categoría del tema. */
    val diversity3: IconToken,
    /** Token elderly; puede sustituirse al configurar la categoría del tema. */
    val elderly: IconToken,
    /** Token elderlyWoman; puede sustituirse al configurar la categoría del tema. */
    val elderlyWoman: IconToken,
    /** Token emojiPeople; puede sustituirse al configurar la categoría del tema. */
    val emojiPeople: IconToken,
    /** Token falling; puede sustituirse al configurar la categoría del tema. */
    val falling: IconToken,
    /** Token familyRestroom; puede sustituirse al configurar la categoría del tema. */
    val familyRestroom: IconToken,
    /** Token followTheSigns; puede sustituirse al configurar la categoría del tema. */
    val followTheSigns: IconToken,
    /** Token group; puede sustituirse al configurar la categoría del tema. */
    val group: IconToken,
    /** Token groupAdd; puede sustituirse al configurar la categoría del tema. */
    val groupAdd: IconToken,
    /** Token groupAuto; puede sustituirse al configurar la categoría del tema. */
    val groupAuto: IconToken,
    /** Token groupRemove; puede sustituirse al configurar la categoría del tema. */
    val groupRemove: IconToken,
    /** Token groupSearch; puede sustituirse al configurar la categoría del tema. */
    val groupSearch: IconToken,
    /** Token guardian; puede sustituirse al configurar la categoría del tema. */
    val guardian: IconToken,
    /** Token hail; puede sustituirse al configurar la categoría del tema. */
    val hail: IconToken,
    /** Token hiking; puede sustituirse al configurar la categoría del tema. */
    val hiking: IconToken,
    /** Token howToReg; puede sustituirse al configurar la categoría del tema. */
    val howToReg: IconToken,
    /** Token man; puede sustituirse al configurar la categoría del tema. */
    val man: IconToken,
    /** Token man4; puede sustituirse al configurar la categoría del tema. */
    val man4: IconToken,
    /** Token manageAccounts; puede sustituirse al configurar la categoría del tema. */
    val manageAccounts: IconToken,
    /** Token notAccessible; puede sustituirse al configurar la categoría del tema. */
    val notAccessible: IconToken,
    /** Token passkey; puede sustituirse al configurar la categoría del tema. */
    val passkey: IconToken,
    /** Token person; puede sustituirse al configurar la categoría del tema. */
    val person: IconToken,
    /** Token personAdd; puede sustituirse al configurar la categoría del tema. */
    val personAdd: IconToken,
    /** Token personAlert; puede sustituirse al configurar la categoría del tema. */
    val personAlert: IconToken,
    /** Token personCancel; puede sustituirse al configurar la categoría del tema. */
    val personCancel: IconToken,
    /** Token personCelebrate; puede sustituirse al configurar la categoría del tema. */
    val personCelebrate: IconToken,
    /** Token personEdit; puede sustituirse al configurar la categoría del tema. */
    val personEdit: IconToken,
    /** Token personHeart; puede sustituirse al configurar la categoría del tema. */
    val personHeart: IconToken,
    /** Token personInjury; puede sustituirse al configurar la categoría del tema. */
    val personInjury: IconToken,
    /** Token personOff; puede sustituirse al configurar la categoría del tema. */
    val personOff: IconToken,
    /** Token personRemove; puede sustituirse al configurar la categoría del tema. */
    val personRemove: IconToken,
    /** Token personSearch; puede sustituirse al configurar la categoría del tema. */
    val personSearch: IconToken,
    /** Token personShield; puede sustituirse al configurar la categoría del tema. */
    val personShield: IconToken,
    /** Token productivity; puede sustituirse al configurar la categoría del tema. */
    val productivity: IconToken,
    /** Token recentPatient; puede sustituirse al configurar la categoría del tema. */
    val recentPatient: IconToken,
    /** Token reduceCapacity; puede sustituirse al configurar la categoría del tema. */
    val reduceCapacity: IconToken,
    /** Token safetyDivider; puede sustituirse al configurar la categoría del tema. */
    val safetyDivider: IconToken,
    /** Token supervisedUserCircle; puede sustituirse al configurar la categoría del tema. */
    val supervisedUserCircle: IconToken,
    /** Token supervisorAccount; puede sustituirse al configurar la categoría del tema. */
    val supervisorAccount: IconToken,
    /** Token transferWithinAStation; puede sustituirse al configurar la categoría del tema. */
    val transferWithinAStation: IconToken,
    /** Token wc; puede sustituirse al configurar la categoría del tema. */
    val wc: IconToken,

    ){
    /** Alias histórico de lectura y XML; utiliza el mismo token que accessibleMenu, sin almacenar otro valor.
     * Para personalizaciones con copy utilizar el nombre Library accessibleMenu.
     */
    val accesibleMenu: IconToken get() = accessibleMenu

    companion object{
        /** Valores base de la categoría; fuente única de sus assets. */
        val people = LibraryPeopleIconsTokens(
                    accessibleMenu = IconToken("ic_accessible_menu", resourceName = "ic_accessible_menu"),
            accessibility = IconToken("ic_accessibility", resourceName = "ic_accessibility"),
            accessibilityNew = IconToken("ic_accessibility_new", resourceName = "ic_accessibility_new"),
            accessible = IconToken("ic_accessible", resourceName = "ic_accessible"),
            accessibleForward = IconToken("ic_accessible_forward", resourceName = "ic_accessible_forward"),
            accountChildInvert = IconToken("ic_account_child_invert", resourceName = "ic_account_child_invert"),
            accountCircle = IconToken("ic_account_circle", resourceName = "ic_account_circle"),
            accountCircleOff = IconToken("ic_account_circle_off", resourceName = "ic_account_circle_off"),
            artist = IconToken("ic_artist", resourceName = "ic_artist"),
            assistWalker = IconToken("ic_assist_walker", resourceName = "ic_assist_walker"),
            babyChangingStation = IconToken("ic_baby_changing_station", resourceName = "ic_baby_changing_station"),
            blind = IconToken("ic_blind", resourceName = "ic_blind"),
            bodySystem = IconToken("ic_body_system", resourceName = "ic_body_system"),
            boy = IconToken("ic_boy", resourceName = "ic_boy"),
            coPresent = IconToken("ic_co_present", resourceName = "ic_co_present"),
            communication = IconToken("ic_communication", resourceName = "ic_communication"),
            conditions = IconToken("ic_conditions", resourceName = "ic_conditions"),
            directionsRun = IconToken("ic_directions_run", resourceName = "ic_directions_run"),
            directionsWalk = IconToken("ic_directions_walk", resourceName = "ic_directions_walk"),
            diversity1 = IconToken("ic_diversity_1", resourceName = "ic_diversity_1"),
            diversity2 = IconToken("ic_diversity_2", resourceName = "ic_diversity_2"),
            diversity3 = IconToken("ic_diversity_3", resourceName = "ic_diversity_3"),
            elderly = IconToken("ic_elderly", resourceName = "ic_elderly"),
            elderlyWoman = IconToken("ic_elderly_woman", resourceName = "ic_elderly_woman"),
            emojiPeople = IconToken("ic_emoji_people", resourceName = "ic_emoji_people"),
            falling = IconToken("ic_falling", resourceName = "ic_falling"),
            familyRestroom = IconToken("ic_family_restroom", resourceName = "ic_family_restroom"),
            followTheSigns = IconToken("ic_follow_the_signs", resourceName = "ic_follow_the_signs"),
            group = IconToken("ic_group", resourceName = "ic_group"),
            groupAdd = IconToken("ic_group_add", resourceName = "ic_group_add"),
            groupAuto = IconToken("ic_group_auto", resourceName = "ic_group_auto"),
            groupRemove = IconToken("ic_group_remove", resourceName = "ic_group_remove"),
            groupSearch = IconToken("ic_group_search", resourceName = "ic_group_search"),
            guardian = IconToken("ic_guardian", resourceName = "ic_guardian"),
            hail = IconToken("ic_hail", resourceName = "ic_hail"),
            hiking = IconToken("ic_hiking", resourceName = "ic_hiking"),
            howToReg = IconToken("ic_how_to_reg", resourceName = "ic_how_to_reg"),
            man = IconToken("ic_man", resourceName = "ic_man"),
            man4 = IconToken("ic_man_4", resourceName = "ic_man_4"),
            manageAccounts = IconToken("ic_manage_accounts", resourceName = "ic_manage_accounts"),
            notAccessible = IconToken("ic_not_accessible", resourceName = "ic_not_accessible"),
            passkey = IconToken("ic_passkey", resourceName = "ic_passkey"),
            person = IconToken("ic_person", resourceName = "ic_person"),
            personAdd = IconToken("ic_person_add", resourceName = "ic_person_add"),
            personAlert = IconToken("ic_person_alert", resourceName = "ic_person_alert"),
            personCancel = IconToken("ic_person_cancel", resourceName = "ic_person_cancel"),
            personCelebrate = IconToken("ic_person_celebrate", resourceName = "ic_person_celebrate"),
            personEdit = IconToken("ic_person_edit", resourceName = "ic_person_edit"),
            personHeart = IconToken("ic_person_heart", resourceName = "ic_person_heart"),
            personInjury = IconToken("ic_person_injury", resourceName = "ic_person_injury"),
            personOff = IconToken("ic_person_off", resourceName = "ic_person_off"),
            personRemove = IconToken("ic_person_remove", resourceName = "ic_person_remove"),
            personSearch = IconToken("ic_person_search", resourceName = "ic_person_search"),
            personShield = IconToken("ic_person_shield", resourceName = "ic_person_shield"),
            productivity = IconToken("ic_productivity", resourceName = "ic_productivity"),
            recentPatient = IconToken("ic_recent_patient", resourceName = "ic_recent_patient"),
            reduceCapacity = IconToken("ic_reduce_capacity", resourceName = "ic_reduce_capacity"),
            safetyDivider = IconToken("ic_safety_divider", resourceName = "ic_safety_divider"),
            supervisedUserCircle = IconToken("ic_supervised_user_circle", resourceName = "ic_supervised_user_circle"),
            supervisorAccount = IconToken("ic_supervisor_account", resourceName = "ic_supervisor_account"),
            transferWithinAStation = IconToken("ic_transfer_within_a_station", resourceName = "ic_transfer_within_a_station"),
            wc = IconToken("ic_wc", resourceName = "ic_wc"),
        )
    }
}
