package cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior

import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior

/**
 * Resuelve la configuracion publica del grupo a behavior nativo del boton.
 *
 * No crea tokens intermedios: traduce directamente a `ButtonNavigationBehavior`.
 */
internal object ButtonNavigationGroupBehaviorResolver {

    /** Resuelve el behavior del boton izquierdo. */
    fun resolveLeadingButtonBehavior(
        showIcon: Boolean,
    ): ButtonNavigationBehavior =
        ButtonNavigationBehavior(
            showIcon = showIcon,
        )

    /** Resuelve el behavior del boton derecho. */
    fun resolveTrailingButtonBehavior(
        behavior: ButtonNavigationGroupBehavior,
        showIcon: Boolean,
    ): ButtonNavigationBehavior =
        ButtonNavigationBehavior(
            isLoading = behavior.isTrailingLoading,
            startsLoadingOnClick = behavior.startsTrailingLoadingOnClick,
            showIcon = showIcon,
        )

    /** Resuelve el label derecho cuando loading fuerza el texto fallback. */
    fun resolveTrailingLabel(
        behavior: ButtonNavigationGroupBehavior,
        explicitLabel: String?,
        defaultLabel: String,
    ): String =
        explicitLabel ?: if (behavior.isTrailingLoading) {
            LoadingLabel
        } else {
            defaultLabel
        }

    private const val LoadingLabel = "Loading"
}
