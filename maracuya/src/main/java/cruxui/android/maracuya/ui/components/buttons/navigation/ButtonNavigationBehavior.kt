package cruxui.android.maracuya.ui.components.buttons.navigation

import cruxui.android.maracuya.ui.components.utils.IconPosition

/**
 * Define los comportamientos opcionales del ButtonNavigation sin modificar su variante visual.
 *
 * isLoading permite renderizar el estado loading desde fuera. startsLoadingOnClick inicia
 * loading al presionar el boton. Para detenerlo, llama stopLoading() desde onTrailingClick.
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

/**
 * Scope entregado al onTrailingClick para controlar comportamientos internos del boton.
 */
class ButtonNavigationLoadingScope internal constructor(
    private val stopLoadingAction: () -> Unit,
) {
    /**
     * Detiene el estado loading iniciado por ButtonNavigationBehavior.Loading.
     */
    fun stopLoading() {
        stopLoadingAction()
    }
}

/**
 * Resultado interno con el comportamiento listo para renderizar.
 */
internal data class ButtonNavigationResolvedBehavior(
    val iconPosition: IconPosition,
    val isLoading: Boolean,
    val loadingIconRotationDegrees: Float,
    val canClick: Boolean,
    val onClick: () -> Unit,
)
