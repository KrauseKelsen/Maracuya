package cruxui.android.lib.ui.components.buttons.simple.behavior

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import cruxui.android.lib.core.components.buttons.behavior.ButtonBehaviorResolver as CoreResolver
import cruxui.android.lib.core.components.buttons.behavior.ButtonBehaviorState

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonBehaviorResolver conecta gestos, callbacks y rotación con Compose.
 *
 * Justificación: La animación existe solo durante loading; salir de composición cancela su reloj.
 * No simula una respuesta de red.
 *
 * Se comunica con: CollectIsPressedAsState observa el interactionSource; CoreResolver decide los
 * flags efectivos.
 *
 * Uso:
 * ```kotlin
 * // El host conserva la operación en su propio estado:
 * Button(text = "Guardar", onClick = onSave, behavior = ButtonBehavior(isLoading = saving))
 * ```
 *
 * El resolver es interno. saving cambia cuando finaliza la operación real, incluso si falla.
 *
 * Objetivos:
 * - Adaptar la política común al ciclo de composición; bloquear activaciones durante carga.
 *
 * Flujo: Entrada del host y gesto → prioridad compartida → bloqueo de acciones y motor de
 * animación.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal object ButtonBehaviorResolver {
    /**
     * @param behavior Estado solicitado por el host.
     * @param enabled Habilitación estándar.
     * @param interactionSource Fuente de gestos compartida con el contenedor.
     * @param onClick Acción vigente; nunca se ejecuta en disabled/loading.
     * @param onLongClick Acción opcional de pulsación larga, con el mismo bloqueo.
     * @return Estado, ángulo y callbacks seguros para el render.
     */
    @Composable
    fun resolve(
        behavior: ButtonBehavior,
        enabled: Boolean,
        interactionSource: MutableInteractionSource,
        onClick: () -> Unit,
        onLongClick: (() -> Unit)?,
    ): ButtonComposeBehavior {
        val pressed by interactionSource.collectIsPressedAsState()
        val state = CoreResolver.resolve(behavior, enabled, pressed)
        val latestState by rememberUpdatedState(state)
        val click by rememberUpdatedState(onClick)
        val longClick by rememberUpdatedState(onLongClick)
        return ButtonComposeBehavior(
            state,
            rotation(state.isLoading),
            { if (latestState.canClick) click() },
            onLongClick?.let {
                {
                    if (latestState.canClick) longClick?.invoke()
                    Unit
                }
            },
        )
    }

    /**
     * El motor solo existe mientras loading es visible; su período no finaliza la carga del host.
     */
    @Composable
    private fun rotation(loading: Boolean): Float {
        if (!loading) return 0f
        val transition = rememberInfiniteTransition(label = "Button loading")
        val degrees by
            transition.animateFloat(
                0f,
                CoreResolver.RotationDegrees,
                infiniteRepeatable(
                    tween(CoreResolver.RotationDurationMillis, easing = LinearEasing),
                    RepeatMode.Restart,
                ),
                label = "Button rotation",
            )
        return degrees
    }
}

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonComposeBehavior transporta la adaptación funcional de Compose.
 *
 * Justificación: Separa el reloj de animación del dibujo.
 *
 * Se comunica con: ButtonRenderer consume flags, rotación y acciones producidos por
 * ButtonBehaviorResolver.
 *
 * Uso:
 * ```kotlin
 * fun indicatorModifier(behavior: ButtonComposeBehavior): androidx.compose.ui.Modifier =
 *     androidx.compose.ui.Modifier.graphicsLayer { rotationZ = behavior.rotation }
 * // Requiere import androidx.compose.ui.graphics.graphicsLayer.
 * ```
 *
 * Objetivos:
 * - Mantener el render sin lógica temporal.
 *
 * Flujo: Entrada del host y gesto → prioridad compartida → bloqueo de acciones y motor de
 * animación.
 *
 * @param state Estado efectivo compartido.
 * @param rotation Ángulo en grados para el icono de carga.
 * @param onClick Acción protegida por el estado vigente.
 * @param onLongClick Acción larga opcional protegida por el mismo estado.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal data class ButtonComposeBehavior(
    val state: ButtonBehaviorState,
    val rotation: Float,
    val onClick: () -> Unit,
    val onLongClick: (() -> Unit)?,
)
