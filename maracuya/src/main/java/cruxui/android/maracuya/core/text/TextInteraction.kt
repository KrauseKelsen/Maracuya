package cruxui.android.maracuya.core.text

data class TextInteractionState(val enabled: Boolean = true, val error: Boolean = false)

enum class TextVisualState { Normal, Error, Disabled }

/** Small reusable policies, independent of Android and Compose. */
object TextInteraction {
    fun visualState(state: TextInteractionState): TextVisualState = when {
        !state.enabled -> TextVisualState.Disabled
        state.error -> TextVisualState.Error
        else -> TextVisualState.Normal
    }

    fun canActivate(state: TextInteractionState, permitted: Boolean): Boolean =
        state.enabled && permitted
}
