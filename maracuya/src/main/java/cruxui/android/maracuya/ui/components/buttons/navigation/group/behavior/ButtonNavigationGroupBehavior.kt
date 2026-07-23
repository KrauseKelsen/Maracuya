package cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior

/**
 * Define los comportamientos opcionales del ButtonNavigationGroup.
 *
 * Esta clase es API publica: expresa que puede pedir el developer sin definir como
 * se traduce a los botones hijos.
 */
data class ButtonNavigationGroupBehavior(
    val isTrailingLoading: Boolean = false,
    val startsTrailingLoadingOnClick: Boolean = false,
) {
    companion object {
        val Default = ButtonNavigationGroupBehavior()
        val Loading = ButtonNavigationGroupBehavior(
            isTrailingLoading = true,
        )
        val StartsLoadingOnClick = ButtonNavigationGroupBehavior(
            startsTrailingLoadingOnClick = true,
        )
    }
}
