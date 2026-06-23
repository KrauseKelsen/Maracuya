package cruxui.android.maracuya.ui.previews

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.R
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.ui.components.buttons.button.ButtonMrcy
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokensOverride
import cruxui.android.maracuya.ui.components.buttons.button.ButtonVariant
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper
import cruxui.android.maracuya.wrappers.components.buttons.button.ButtonTokensOverrideRegistry

@Preview(name = "ButtonMrcy - Variants and overrides", showBackground = true)
@Composable
fun ButtonMrcy_VariantsAndOverridesPreview() {
    PreviewWrapper(style = Mode.current) {
        val colors = LocalLibraryColorTokens.current
        val mytokensoverride = ButtonTokensOverride(
            containerColor = colors.bgBase,
            contentColor = colors.fgDefault,
            contentPressColor = colors.fgOnInverse,
            hoverContainerColor = colors.fgDefault,
            borderContainerColor = colors.fgDefault,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ButtonMrcy(
                modifier = Modifier.fillMaxWidth(),
                text = "Primary default",
                onClick = {},
                buttonVariant = ButtonVariant.PRIMARY,
            )

            ButtonMrcy(
                modifier = Modifier.fillMaxWidth(),
                text = "Secondary default",
                onClick = {},
                buttonVariant = ButtonVariant.SECONDARY,
            )

            ButtonMrcy(
                modifier = Modifier.fillMaxWidth(),
                text = "Secondary with tokens override",
                onClick = {},
                buttonVariant = ButtonVariant.SECONDARY,
                buttonTokensOverride = mytokensoverride,
            )
        }
    }
}

private const val XML_PREVIEW_TOKENS_OVERRIDE = "mytokensoverride"

@Preview(name = "ButtonWrp XML - Tokens override registry", showBackground = true)
@Composable
fun ButtonWrp_XmlTokensOverridePreview() {
    PreviewWrapper(style = Mode.current) {
        ButtonTokensOverrideRegistry.register(XML_PREVIEW_TOKENS_OVERRIDE) {
            val colors = LocalLibraryColorTokens.current

            ButtonTokensOverride(
                containerColor = colors.bgBase,
                contentColor = colors.fgDefault,
                contentPressColor = colors.fgOnInverse,
                hoverContainerColor = colors.fgDefault,
                borderContainerColor = colors.fgDefault,
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                ButtonTokensOverrideRegistry.unregister(XML_PREVIEW_TOKENS_OVERRIDE)
            }
        }

        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.button_wrp_preview, null)
            },
        )
    }
}
