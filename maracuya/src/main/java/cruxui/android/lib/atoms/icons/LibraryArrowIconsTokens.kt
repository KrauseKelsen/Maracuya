package cruxui.android.lib.atoms.icons

import cruxui.android.lib.tokens.base.IconToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryArrowIconsTokens declara los IconToken de una categoría Library.
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
data class LibraryArrowIconsTokens (

    /** Token adsClick; puede sustituirse al configurar la categoría del tema. */
    val adsClick: IconToken,
    /** Token arrowCircleDown; puede sustituirse al configurar la categoría del tema. */
    val arrowCircleDown: IconToken,
    /** Token arrowCircleLeft; puede sustituirse al configurar la categoría del tema. */
    val arrowCircleLeft: IconToken,
    /** Token arrowCircleRight; puede sustituirse al configurar la categoría del tema. */
    val arrowCircleRight: IconToken,
    /** Token arrowCircleUp; puede sustituirse al configurar la categoría del tema. */
    val arrowCircleUp: IconToken,
    /** Token arrowDropDown; puede sustituirse al configurar la categoría del tema. */
    val arrowDropDown: IconToken,
    /** Token arrowDropUp; puede sustituirse al configurar la categoría del tema. */
    val arrowDropUp: IconToken,
    /** Token arrowLeft; puede sustituirse al configurar la categoría del tema. */
    val arrowLeft: IconToken,
    /** Token arrowOrEdge; puede sustituirse al configurar la categoría del tema. */
    val arrowOrEdge: IconToken,
    /** Token arrowRangeHoriz; puede sustituirse al configurar la categoría del tema. */
    val arrowRangeHoriz: IconToken,
    /** Token arrowRangeVert; puede sustituirse al configurar la categoría del tema. */
    val arrowRangeVert: IconToken,
    /** Token arrowRight; puede sustituirse al configurar la categoría del tema. */
    val arrowRight: IconToken,
    /** Token arrowSelectorTool; puede sustituirse al configurar la categoría del tema. */
    val arrowSelectorTool: IconToken,
    /** Token arrowSplit; puede sustituirse al configurar la categoría del tema. */
    val arrowSplit: IconToken,
    /** Token arrowsInput; puede sustituirse al configurar la categoría del tema. */
    val arrowsInput: IconToken,
    /** Token arrowsMoreDownLeft; puede sustituirse al configurar la categoría del tema. */
    val arrowsMoreDownLeft: IconToken,
    /** Token arrowsMoreDownRight; puede sustituirse al configurar la categoría del tema. */
    val arrowsMoreDownRight: IconToken,
    /** Token arrowsMoreUpLeft; puede sustituirse al configurar la categoría del tema. */
    val arrowsMoreUpLeft: IconToken,
    /** Token arrowsMoreUpRight; puede sustituirse al configurar la categoría del tema. */
    val arrowsMoreUpRight: IconToken,
    /** Token arrowsOutput; puede sustituirse al configurar la categoría del tema. */
    val arrowsOutput: IconToken,
    /** Token arrowsOutwardHoriz; puede sustituirse al configurar la categoría del tema. */
    val arrowsOutwardHoriz: IconToken,
    /** Token arrowsOutwardVert; puede sustituirse al configurar la categoría del tema. */
    val arrowsOutwardVert: IconToken,
    /** Token closeFullscreen; puede sustituirse al configurar la categoría del tema. */
    val closeFullscreen: IconToken,
    /** Token code; puede sustituirse al configurar la categoría del tema. */
    val code: IconToken,
    /** Token download; puede sustituirse al configurar la categoría del tema. */
    val download: IconToken,
    /** Token dragClick; puede sustituirse al configurar la categoría del tema. */
    val dragClick: IconToken,
    /** Token expandAll; puede sustituirse al configurar la categoría del tema. */
    val expandAll: IconToken,
    /** Token expandCircleDown; puede sustituirse al configurar la categoría del tema. */
    val expandCircleDown: IconToken,
    /** Token expandCircleLeft; puede sustituirse al configurar la categoría del tema. */
    val expandCircleLeft: IconToken,
    /** Token expandCircleRight; puede sustituirse al configurar la categoría del tema. */
    val expandCircleRight: IconToken,
    /** Token expandCircleUp; puede sustituirse al configurar la categoría del tema. */
    val expandCircleUp: IconToken,
    /** Token expandLess; puede sustituirse al configurar la categoría del tema. */
    val expandLess: IconToken,
    /** Token expandMore; puede sustituirse al configurar la categoría del tema. */
    val expandMore: IconToken,
    /** Token forwardMedia; puede sustituirse al configurar la categoría del tema. */
    val forwardMedia: IconToken,
    /** Token input; puede sustituirse al configurar la categoría del tema. */
    val input: IconToken,
    /** Token keyboardArrowLeft; puede sustituirse al configurar la categoría del tema. */
    val keyboardArrowLeft: IconToken,
    /** Token keyboardArrowRight; puede sustituirse al configurar la categoría del tema. */
    val keyboardArrowRight: IconToken,
    /** Token keyboardBackspaceDown; puede sustituirse al configurar la categoría del tema. */
    val keyboardBackspaceDown: IconToken,
    /** Token keyboardBackspaceLeft; puede sustituirse al configurar la categoría del tema. */
    val keyboardBackspaceLeft: IconToken,
    /** Token keyboardBackspaceRight; puede sustituirse al configurar la categoría del tema. */
    val keyboardBackspaceRight: IconToken,
    /** Token keyboardBackspaceUp; puede sustituirse al configurar la categoría del tema. */
    val keyboardBackspaceUp: IconToken,
    /** Token keyboardDoubleArrowDown; puede sustituirse al configurar la categoría del tema. */
    val keyboardDoubleArrowDown: IconToken,
    /** Token keyboardDoubleArrowUp; puede sustituirse al configurar la categoría del tema. */
    val keyboardDoubleArrowUp: IconToken,
    /** Token keyboardTabDown; puede sustituirse al configurar la categoría del tema. */
    val keyboardTabDown: IconToken,
    /** Token keyboardTabLeft; puede sustituirse al configurar la categoría del tema. */
    val keyboardTabLeft: IconToken,
    /** Token keyboardTabRight; puede sustituirse al configurar la categoría del tema. */
    val keyboardTabRight: IconToken,
    /** Token keyboardTabUp; puede sustituirse al configurar la categoría del tema. */
    val keyboardTabUp: IconToken,
    /** Token login; puede sustituirse al configurar la categoría del tema. */
    val login: IconToken,
    /** Token logout; puede sustituirse al configurar la categoría del tema. */
    val logout: IconToken,
    /** Token navigateBefore; puede sustituirse al configurar la categoría del tema. */
    val navigateBefore: IconToken,
    /** Token navigateNext; puede sustituirse al configurar la categoría del tema. */
    val navigateNext: IconToken,
    /** Token openInFull; puede sustituirse al configurar la categoría del tema. */
    val openInFull: IconToken,
    /** Token openInNew; puede sustituirse al configurar la categoría del tema. */
    val openInNew: IconToken,
    /** Token openInNewDown; puede sustituirse al configurar la categoría del tema. */
    val openInNewDown: IconToken,
    /** Token openInNewOff; puede sustituirse al configurar la categoría del tema. */
    val openInNewOff: IconToken,
    /** Token openRun; puede sustituirse al configurar la categoría del tema. */
    val openRun: IconToken,
    /** Token openWith; puede sustituirse al configurar la categoría del tema. */
    val openWith: IconToken,
    /** Token output; puede sustituirse al configurar la categoría del tema. */
    val output: IconToken,
    /** Token repeat; puede sustituirse al configurar la categoría del tema. */
    val repeat: IconToken,
    /** Token swapHoriz; puede sustituirse al configurar la categoría del tema. */
    val swapHoriz: IconToken,
    /** Token swapVert; puede sustituirse al configurar la categoría del tema. */
    val swapVert: IconToken,
    /** Token sync; puede sustituirse al configurar la categoría del tema. */
    val sync: IconToken,
    /** Token syncDisabled; puede sustituirse al configurar la categoría del tema. */
    val syncDisabled: IconToken,
    /** Token trendingUp; puede sustituirse al configurar la categoría del tema. */
    val trendingUp: IconToken,
    /** Token upload; puede sustituirse al configurar la categoría del tema. */
    val upload: IconToken,
){
    companion object{
        /** Valores base de la categoría; fuente única de sus assets. */
        val arrows = LibraryArrowIconsTokens(
            adsClick = IconToken("ic_ads_click", resourceName = "ic_ads_click"),
            arrowCircleDown = IconToken("ic_arrow_circle_down", resourceName = "ic_arrow_circle_down"),
            arrowCircleLeft = IconToken("ic_arrow_circle_left", resourceName = "ic_arrow_circle_left"),
            arrowCircleRight = IconToken("ic_arrow_circle_right", resourceName = "ic_arrow_circle_right"),
            arrowCircleUp = IconToken("ic_arrow_circle_up", resourceName = "ic_arrow_circle_up"),
            arrowDropDown = IconToken("ic_arrow_drop_down", resourceName = "ic_arrow_drop_down"),
            arrowDropUp = IconToken("ic_arrow_drop_up", resourceName = "ic_arrow_drop_up"),
            arrowLeft = IconToken("ic_arrow_left", resourceName = "ic_arrow_left"),
            arrowOrEdge = IconToken("ic_arrow_or_edge", resourceName = "ic_arrow_or_edge"),
            arrowRangeHoriz = IconToken("ic_arrow_range_horiz", resourceName = "ic_arrow_range_horiz"),
            arrowRangeVert = IconToken("ic_arrow_range_vert", resourceName = "ic_arrow_range_vert"),
            arrowRight = IconToken("ic_arrow_right", resourceName = "ic_arrow_right"),
            arrowSelectorTool = IconToken("ic_arrow_selector_tool", resourceName = "ic_arrow_selector_tool"),
            arrowSplit = IconToken("ic_arrow_split", resourceName = "ic_arrow_split"),
            arrowsInput = IconToken("ic_arrows_input", resourceName = "ic_arrows_input"),
            arrowsMoreDownLeft = IconToken("ic_arrows_more_down_left", resourceName = "ic_arrows_more_down_left"),
            arrowsMoreDownRight = IconToken("ic_arrows_more_down_right", resourceName = "ic_arrows_more_down_right"),
            arrowsMoreUpLeft = IconToken("ic_arrows_more_up_left", resourceName = "ic_arrows_more_up_left"),
            arrowsMoreUpRight = IconToken("ic_arrows_more_up_right", resourceName = "ic_arrows_more_up_right"),
            arrowsOutput = IconToken("ic_arrows_output", resourceName = "ic_arrows_output"),
            arrowsOutwardHoriz = IconToken("ic_arrows_outward_horiz", resourceName = "ic_arrows_outward_horiz"),
            arrowsOutwardVert = IconToken("ic_arrows_outward_vert", resourceName = "ic_arrows_outward_vert"),
            closeFullscreen = IconToken("ic_close_fullscreen", resourceName = "ic_close_fullscreen"),
            code = IconToken("ic_code", resourceName = "ic_code"),
            download = IconToken("ic_download", resourceName = "ic_download"),
            dragClick = IconToken("ic_drag_click", resourceName = "ic_drag_click"),
            expandAll = IconToken("ic_expand_all", resourceName = "ic_expand_all"),
            expandCircleDown = IconToken("ic_expand_circle_down", resourceName = "ic_expand_circle_down"),
            expandCircleLeft = IconToken("ic_expand_circle_left", resourceName = "ic_expand_circle_left"),
            expandCircleRight = IconToken("ic_expand_circle_right", resourceName = "ic_expand_circle_right"),
            expandCircleUp = IconToken("ic_expand_circle_up", resourceName = "ic_expand_circle_up"),
            expandLess = IconToken("ic_expand_less", resourceName = "ic_expand_less"),
            expandMore = IconToken("ic_expand_more", resourceName = "ic_expand_more"),
            forwardMedia = IconToken("ic_forward_media", resourceName = "ic_forward_media"),
            input = IconToken("ic_input", resourceName = "ic_input"),
            keyboardArrowLeft = IconToken("ic_keyboard_arrow_left", resourceName = "ic_keyboard_arrow_left"),
            keyboardArrowRight = IconToken("ic_keyboard_arrow_right", resourceName = "ic_keyboard_arrow_right"),
            keyboardBackspaceDown = IconToken("ic_keyboard_backspace_down", resourceName = "ic_keyboard_backspace_down"),
            keyboardBackspaceLeft = IconToken("ic_keyboard_backspace_left", resourceName = "ic_keyboard_backspace_left"),
            keyboardBackspaceRight = IconToken("ic_keyboard_backspace_right", resourceName = "ic_keyboard_backspace_right"),
            keyboardBackspaceUp = IconToken("ic_keyboard_backspace_up", resourceName = "ic_keyboard_backspace_up"),
            keyboardDoubleArrowDown = IconToken("ic_keyboard_double_arrow_down", resourceName = "ic_keyboard_double_arrow_down"),
            keyboardDoubleArrowUp = IconToken("ic_keyboard_double_arrow_up", resourceName = "ic_keyboard_double_arrow_up"),
            keyboardTabDown = IconToken("ic_keyboard_tab_down", resourceName = "ic_keyboard_tab_down"),
            keyboardTabLeft = IconToken("ic_keyboard_tab_left", resourceName = "ic_keyboard_tab_left"),
            keyboardTabRight = IconToken("ic_keyboard_tab_right", resourceName = "ic_keyboard_tab_right"),
            keyboardTabUp = IconToken("ic_keyboard_tab_up", resourceName = "ic_keyboard_tab_up"),
            login = IconToken("ic_login", resourceName = "ic_login"),
            logout = IconToken("ic_logout", resourceName = "ic_logout"),
            navigateBefore = IconToken("ic_navigate_before", resourceName = "ic_navigate_before"),
            navigateNext = IconToken("ic_navigate_next", resourceName = "ic_navigate_next"),
            openInFull = IconToken("ic_open_in_full", resourceName = "ic_open_in_full"),
            openInNew = IconToken("ic_open_in_new", resourceName = "ic_open_in_new"),
            openInNewDown = IconToken("ic_open_in_new_down", resourceName = "ic_open_in_new_down"),
            openInNewOff = IconToken("ic_open_in_new_off", resourceName = "ic_open_in_new_off"),
            openRun = IconToken("ic_open_run", resourceName = "ic_open_run"),
            openWith = IconToken("ic_open_with", resourceName = "ic_open_with"),
            output = IconToken("ic_output", resourceName = "ic_output"),
            repeat = IconToken("ic_repeat", resourceName = "ic_repeat"),
            swapHoriz = IconToken("ic_swap_horiz", resourceName = "ic_swap_horiz"),
            swapVert = IconToken("ic_swap_vert", resourceName = "ic_swap_vert"),
            sync = IconToken("ic_sync", resourceName = "ic_sync"),
            syncDisabled = IconToken("ic_sync_disabled", resourceName = "ic_sync_disabled"),
            trendingUp = IconToken("ic_trending_up", resourceName = "ic_trending_up"),
            upload = IconToken("ic_upload", resourceName = "ic_upload"),

        )

    }
}
