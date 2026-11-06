package cruxui.android.lib.atoms.icons

import cruxui.android.lib.tokens.base.IconToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryAlertsIconsTokens declara los IconToken de una categoría Library.
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
data class LibraryAlertsIconsTokens (

    /** Token addCircle; puede sustituirse al configurar la categoría del tema. */
    val addCircle: IconToken,
    /** Token addTask; puede sustituirse al configurar la categoría del tema. */
    val addTask: IconToken,
    /** Token adminModerator; puede sustituirse al configurar la categoría del tema. */
    val adminModerator: IconToken,
    /** Token adminPanelSettings; puede sustituirse al configurar la categoría del tema. */
    val adminPanelSettings: IconToken,
    /** Token armingCountdown; puede sustituirse al configurar la categoría del tema. */
    val armingCountdown: IconToken,
    /** Token awardStar; puede sustituirse al configurar la categoría del tema. */
    val awardStar: IconToken,
    /** Token block; puede sustituirse al configurar la categoría del tema. */
    val block: IconToken,
    /** Token cancel; puede sustituirse al configurar la categoría del tema. */
    val cancel: IconToken,
    /** Token checkCircle; puede sustituirse al configurar la categoría del tema. */
    val checkCircle: IconToken,
    /** Token checkCircleUnread; puede sustituirse al configurar la categoría del tema. */
    val checkCircleUnread: IconToken,
    /** Token contactSupport; puede sustituirse al configurar la categoría del tema. */
    val contactSupport: IconToken,
    /** Token crisisAlert; puede sustituirse al configurar la categoría del tema. */
    val crisisAlert: IconToken,
    /** Token emergencyHome; puede sustituirse al configurar la categoría del tema. */
    val emergencyHome: IconToken,
    /** Token encrypted; puede sustituirse al configurar la categoría del tema. */
    val encrypted: IconToken,
    /** Token encryptedAddCircle; puede sustituirse al configurar la categoría del tema. */
    val encryptedAddCircle: IconToken,
    /** Token encryptedMinusCircle; puede sustituirse al configurar la categoría del tema. */
    val encryptedMinusCircle: IconToken,
    /** Token encryptedOff; puede sustituirse al configurar la categoría del tema. */
    val encryptedOff: IconToken,
    /** Token error; puede sustituirse al configurar la categoría del tema. */
    val error: IconToken,
    /** Token gppBad; puede sustituirse al configurar la categoría del tema. */
    val gppBad: IconToken,
    /** Token gppMaybe; puede sustituirse al configurar la categoría del tema. */
    val gppMaybe: IconToken,
    /** Token healthAndSafety; puede sustituirse al configurar la categoría del tema. */
    val healthAndSafety: IconToken,
    /** Token help; puede sustituirse al configurar la categoría del tema. */
    val help: IconToken,
    /** Token highlightKeyboardFocus; puede sustituirse al configurar la categoría del tema. */
    val highlightKeyboardFocus: IconToken,
    /** Token info; puede sustituirse al configurar la categoría del tema. */
    val info: IconToken,
    /** Token lockReset; puede sustituirse al configurar la categoría del tema. */
    val lockReset: IconToken,
    /** Token newReleases; puede sustituirse al configurar la categoría del tema. */
    val newReleases: IconToken,
    /** Token pending; puede sustituirse al configurar la categoría del tema. */
    val pending: IconToken,
    /** Token privacyTip; puede sustituirse al configurar la categoría del tema. */
    val privacyTip: IconToken,
    /** Token settingsAlert; puede sustituirse al configurar la categoría del tema. */
    val settingsAlert: IconToken,
    /** Token starShine; puede sustituirse al configurar la categoría del tema. */
    val starShine: IconToken,
    /** Token warning; puede sustituirse al configurar la categoría del tema. */
    val warning: IconToken,
){
    companion object{
        /** Valores base de la categoría; fuente única de sus assets. */
        val alerts = LibraryAlertsIconsTokens(


            addCircle = IconToken("ic_add_circle", resourceName = "ic_add_circle"),
            addTask = IconToken("ic_add_task", resourceName = "ic_add_task"),
            adminModerator = IconToken("ic_admin_moderator", resourceName = "ic_admin_moderator"),
            adminPanelSettings = IconToken("ic_admin_panel_settings", resourceName = "ic_admin_panel_settings"),
            armingCountdown = IconToken("ic_arming_countdown", resourceName = "ic_arming_countdown"),
            awardStar = IconToken("ic_award_star", resourceName = "ic_award_star"),
            block = IconToken("ic_block", resourceName = "ic_block"),
            cancel = IconToken("ic_cancel", resourceName = "ic_cancel"),
            checkCircle = IconToken("ic_check_circle", resourceName = "ic_check_circle"),
            checkCircleUnread = IconToken("ic_check_circle_unread", resourceName = "ic_check_circle_unread"),
            contactSupport = IconToken("ic_contact_support", resourceName = "ic_contact_support"),
            crisisAlert = IconToken("ic_crisis_alert", resourceName = "ic_crisis_alert"),
            emergencyHome = IconToken("ic_emergency_home", resourceName = "ic_emergency_home"),
            encrypted = IconToken("ic_encrypted", resourceName = "ic_encrypted"),
            encryptedAddCircle = IconToken("ic_encrypted_add_circle", resourceName = "ic_encrypted_add_circle"),
            encryptedMinusCircle = IconToken("ic_encrypted_minus_circle", resourceName = "ic_encrypted_minus_circle"),
            encryptedOff = IconToken("ic_encrypted_off", resourceName = "ic_encrypted_off"),
            error = IconToken("ic_error", resourceName = "ic_error"),
            gppBad = IconToken("ic_gpp_bad", resourceName = "ic_gpp_bad"),
            gppMaybe = IconToken("ic_gpp_maybe", resourceName = "ic_gpp_maybe"),
            healthAndSafety = IconToken("ic_health_and_safety", resourceName = "ic_health_and_safety"),
            help = IconToken("ic_help", resourceName = "ic_help"),
            highlightKeyboardFocus = IconToken("ic_highlight_keyboard_focus", resourceName = "ic_highlight_keyboard_focus"),
            info = IconToken("ic_info", resourceName = "ic_info"),
            lockReset = IconToken("ic_lock_reset", resourceName = "ic_info"),
            newReleases = IconToken("ic_new_releases", resourceName = "ic_new_releases"),
            pending = IconToken("ic_pending", resourceName = "ic_pending"),
            privacyTip = IconToken("ic_privacy_tip", resourceName = "ic_privacy_tip"),
            settingsAlert = IconToken("ic_settings_alert", resourceName = "ic_settings_alert"),
            starShine = IconToken("ic_star_shine", resourceName = "ic_star_shine"),
            warning = IconToken("ic_warning", resourceName = "ic_warning"),
        )

    }
}
