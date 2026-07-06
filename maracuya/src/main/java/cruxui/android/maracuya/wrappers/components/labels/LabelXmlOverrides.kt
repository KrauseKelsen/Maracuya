package cruxui.android.maracuya.wrappers.components.labels

import cruxui.android.maracuya.ui.components.labels.LabelTokensOverride

object LabelXmlOverrides {

    fun register(nameTokensOverride: String) {
        LabelTokensOverrideRegistry.registerFromTheme(nameTokensOverride) { colors, _, _, _ ->
            LabelTokensOverride(
                foregroundDefault = colors.fgError,
                foregroundError = colors.fgDefault,
            )
        }
    }

    fun unregister(nameTokensOverride: String) {
        LabelTokensOverrideRegistry.unregister(nameTokensOverride)
    }
}
