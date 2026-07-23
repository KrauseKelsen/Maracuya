package cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.core.ButtonNavigationLoadingScope

/**
 * Resuelve la lógica interactiva del ButtonNavigation.
 *
 * Maneja el loading local, bloquea el click mientras carga y entrega el callback
 * final que debe usar el componente renderizado.
 */
internal object ButtonNavigationBehaviorResolver {

    @Composable
    fun resolve(
        behavior: ButtonNavigationBehavior,
        enabled: Boolean,
        onClick: () -> Unit,
        onLoadingClick: (ButtonNavigationLoadingScope.() -> Unit)?,
    ): ButtonNavigationBehaviorState {
        var internalLoading by remember { mutableStateOf(false) }
        val currentOnClick by rememberUpdatedState(onClick)
        val currentOnLoadingClick by rememberUpdatedState(onLoadingClick)

        LaunchedEffect(behavior.isLoading) {
            if (!behavior.isLoading) {
                internalLoading = false
            }
        }

        LaunchedEffect(enabled) {
            if (!enabled) {
                internalLoading = false
            }
        }

        val loadingScope = remember {
            ButtonNavigationLoadingScope {
                internalLoading = false
            }
        }

        val loadingActive = behavior.isLoading || internalLoading
        val canClick = enabled && !loadingActive
        val rotationDegrees = resolveLoadingRotationDegrees(
            isLoading = loadingActive,
        )

        return ButtonNavigationBehaviorState(
            isLoading = loadingActive,
            loadingIconRotationDegrees = rotationDegrees,
            canClick = canClick,
            showIcon = behavior.showIcon,
            onClick = {
                if (canClick) {
                    if (behavior.startsLoadingOnClick) {
                        internalLoading = true
                    }
                    currentOnClick()
                    currentOnLoadingClick?.invoke(loadingScope)
                }
            },
        )
    }

    @Composable
    private fun resolveLoadingRotationDegrees(isLoading: Boolean): Float {
        if (!isLoading) return 0f

        val infiniteTransition = rememberInfiniteTransition(
            label = "ButtonNavigationLoadingTransition",
        )
        val rotationDegrees by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 900,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "ButtonNavigationLoadingRotation",
        )

        return rotationDegrees
    }
}

/**
 * Estado de comportamiento listo para que ButtonNavigationMrcy lo renderice.
 */
internal data class ButtonNavigationBehaviorState(
    val isLoading: Boolean,
    val loadingIconRotationDegrees: Float,
    val canClick: Boolean,
    val showIcon: Boolean,
    val onClick: () -> Unit,
)
