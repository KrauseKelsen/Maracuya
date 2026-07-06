package cruxui.android.maracuya.ui.components.buttons.navigation.simple

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
import cruxui.android.maracuya.ui.components.utils.IconPosition

/**
 * Resuelve la lógica interactiva del ButtonNavigation.
 *
 * Mantiene el loading local del componente, rota el icono mientras carga y compone
 * el onClick real del boton con el callback opcional que controla el loading.
 */
internal object ButtonNavigationBehaviorResolver {

    @Composable
    fun resolve(
        variant: ButtonNavigationVariant,
        behavior: ButtonNavigationBehavior,
        enabled: Boolean,
        onClick: () -> Unit,
        onTrailingClick: (ButtonNavigationLoadingScope.() -> Unit)?,
    ): ButtonNavigationResolvedBehavior {
        var isLoading by remember { mutableStateOf(behavior.isLoading) }
        val currentOnClick by rememberUpdatedState(onClick)
        val currentOnTrailingClick by rememberUpdatedState(onTrailingClick)

        LaunchedEffect(behavior.isLoading) {
            isLoading = behavior.isLoading
        }

        LaunchedEffect(enabled) {
            if (!enabled) {
                isLoading = false
            }
        }

        val loadingScope = remember {
            ButtonNavigationLoadingScope {
                isLoading = false
            }
        }

        val iconPosition = when (variant) {
            ButtonNavigationVariant.PRIMARY -> IconPosition.END
            ButtonNavigationVariant.SECONDARY -> IconPosition.START
        }

        val loadingActive = isLoading || behavior.isLoading
        val canClick = enabled && !loadingActive
        val rotationDegrees = resolveLoadingRotationDegrees(
            isLoading = loadingActive,
        )

        return ButtonNavigationResolvedBehavior(
            iconPosition = iconPosition,
            isLoading = loadingActive,
            loadingIconRotationDegrees = rotationDegrees,
            canClick = canClick,
            onClick = {
                if (canClick) {
                    if (behavior.startsLoadingOnClick) {
                        isLoading = true
                    }
                    currentOnClick()
                    currentOnTrailingClick?.invoke(loadingScope)
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
