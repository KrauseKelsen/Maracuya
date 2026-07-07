package cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior

/**
 * Declara los comportamientos configurables de ButtonNavigation.
 *
 * No resuelve estados ni callbacks: solo describe si el boton debe cargar, iniciar
 * loading al presionarse y mostrar su icono.
 */
data class ButtonNavigationBehavior(
    val isLoading: Boolean = false,
    val startsLoadingOnClick: Boolean = false,
    val showIcon: Boolean = true,
) {
    companion object {
        val Default = ButtonNavigationBehavior()
        val Loading = ButtonNavigationBehavior(startsLoadingOnClick = true)
    }
}
