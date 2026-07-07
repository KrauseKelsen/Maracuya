package cruxui.android.maracuya.ui.components.buttons.navigation.simple

/**
 * Expone acciones para controlar el loading interno iniciado por el boton.
 */
class ButtonNavigationLoadingScope internal constructor(
    private val stopLoadingAction: () -> Unit,
) {
    /**
     * Finaliza el estado loading local cuando la carga asociada termina.
     */
    fun stopLoading() {
        stopLoadingAction()
    }
}
