package com.example.myfirstapplication.lookandfeel

import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokensOverride
import cruxui.android.maracuya.wrappers.components.buttons.button.ButtonTokensOverrideRegistry

/**
 * Example of how a View/XML host can expose a Kotlin-built ButtonTokensOverride to XML.
 *
 * Call `ButtonMrcyXmlOverrideExample.register()` before inflating the XML layout that uses:
 *
 * ```xml
 * app:buttonTokensOverride="mytokensoverride"
 * ```
 */
object ButtonMrcyXmlOverrideExample {

    const val MY_TOKENS_OVERRIDE = "mytokensoverride"

    fun register() {
        ButtonTokensOverrideRegistry.register(MY_TOKENS_OVERRIDE) {
            val colors = LocalLibraryColorTokens.current

            ButtonTokensOverride(
                containerColor = colors.bgBase,
                contentColor = colors.fgDefault,
                contentPressColor = colors.fgOnInverse,
                hoverContainerColor = colors.fgDefault,
                borderContainerColor = colors.fgDefault,
            )
        }
    }
}
