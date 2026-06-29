package cruxui.android.maracuya.wrappers.components.labels

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.ui.components.labels.LabelTokensOverride

object LabelXmlOverrides {

    @Composable
    fun register(nameTokensOverride: String) {
        val colors = LocalLibraryColorTokens.current

        LabelTokensOverrideRegistry.register(nameTokensOverride,
            LabelTokensOverride(
                foregroundDefault = colors.fgError,
                foregroundError = colors.fgDefault,
            )
        )
    }

    fun unregister(nameTokensOverride: String) {
        LabelTokensOverrideRegistry.unregister(nameTokensOverride)
    }
}
