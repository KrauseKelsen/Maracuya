package cruxui.android.maracuya.ui.previews

import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.ui.components.buttons.primary.PrimaryButtonMrcy
import cruxui.android.maracuya.ui.components.buttons.primary.PrimaryButtonTokens
import cruxui.android.maracuya.ui.components.buttons.primary.PrimaryButtonTokensResolver
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper
import kotlinx.coroutines.delay

/**
 * PrimaryButtonMrcy – Preview (simplified)
 *
 * Integration modes:
 * 1) Corporate: primaryButtonTokens = null -> DS provides corporate semantics
 * 2) Custom: primaryButtonTokens = PrimaryButtonTokens(...) -> host overrides tokens
 * 3) Material: primaryButtonTokens = PrimaryButtonTokensResolver.fromMaterial()
 *
 * Visual state:
 * - Mobile: pressed == hover (brandPrimaryHover)
 * - Previews are not interactive; we "force" pressed via interactionSource.
 */
private object PrimaryButtonPreviewFactory {

    enum class VisualState { Rest, Pressed } // Pressed == Hovered on mobile

    @Composable
    fun rememberForcedInteractionSource(state: VisualState): MutableInteractionSource {
        val source = remember { MutableInteractionSource() }

        LaunchedEffect(state) {
            delay(50)
            when (state) {
                VisualState.Rest -> Unit
                VisualState.Pressed -> {
                    // Keep pressed visuals by emitting Press and not releasing.
                    source.emit(PressInteraction.Press(pressPosition = Offset.Zero))

                    // Optional: some implementations use hover tokens for press visuals.
                    source.emit(HoverInteraction.Enter())
                }
            }
        }
        return source
    }

    @Composable
    fun customTokens(): PrimaryButtonTokens {
        val colors = LocalLibraryColorTokens.current
        return PrimaryButtonTokens(
            containerColor = colors.brandPrimary,
            contentColor = colors.fgOnInverse,
            hoverContainerColor = colors.brandPrimaryHover,
            disabledContainerColor = colors.bgMuted,
            disabledContentColor = colors.fgMuted,
            borderContainerColor = colors.brandPrimary,
            borderDisabledColor = colors.borderDefault,
            textTypography = LocalLibraryTypography.current.buttons,
            fontFamilyToken = LocalFontFamily.current
        )
    }

    @Composable
    fun Section(
        title: String,
        contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        content: @Composable () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            androidx.compose.material3.Text(text = title)
            content()
        }
    }
}

@Preview(name = "PrimaryButton – Gallery", showBackground = true)
@Composable
fun PrimaryButton_Gallery() {
    PreviewWrapper(style = Mode.current) {
        PrimaryButton_GalleryContent()
    }
}

@Composable
private fun PrimaryButton_GalleryContent() {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {

        PrimaryButtonPreviewFactory.Section("1) Corporate semantics (tokens = null)") {
            PrimaryButtonMrcy(
                text = "Enabled • Rest",
                onClick = {},
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = PrimaryButtonPreviewFactory.rememberForcedInteractionSource(
                    PrimaryButtonPreviewFactory.VisualState.Rest
                ),
            )

            PrimaryButtonMrcy(
                text = "Enabled • Pressed",
                onClick = {},
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = PrimaryButtonPreviewFactory.rememberForcedInteractionSource(
                    PrimaryButtonPreviewFactory.VisualState.Pressed
                ),
            )

            PrimaryButtonMrcy(
                text = "Disabled",
                onClick = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = PrimaryButtonPreviewFactory.rememberForcedInteractionSource(
                    PrimaryButtonPreviewFactory.VisualState.Rest
                ),
            )
        }

        Spacer(Modifier.height(6.dp))

        PrimaryButtonPreviewFactory.Section("2) Custom semantics (PrimaryButtonTokens)") {
            val tokens = PrimaryButtonPreviewFactory.customTokens()

            PrimaryButtonMrcy(
                text = "Enabled • Rest",
                onClick = {},
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = PrimaryButtonPreviewFactory.rememberForcedInteractionSource(
                    PrimaryButtonPreviewFactory.VisualState.Rest
                ),
                primaryButtonTokens = tokens
            )

            PrimaryButtonMrcy(
                text = "Enabled • Pressed",
                onClick = {},
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = PrimaryButtonPreviewFactory.rememberForcedInteractionSource(
                    PrimaryButtonPreviewFactory.VisualState.Pressed
                ),
                primaryButtonTokens = tokens
            )

            PrimaryButtonMrcy(
                text = "Disabled",
                onClick = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = PrimaryButtonPreviewFactory.rememberForcedInteractionSource(
                    PrimaryButtonPreviewFactory.VisualState.Rest
                ),
                primaryButtonTokens = tokens
            )
        }

        Spacer(Modifier.height(6.dp))

        PrimaryButtonPreviewFactory.Section("3) Material bridge (Resolver.fromMaterial())") {
            val materialTokens = PrimaryButtonTokensResolver.fromMaterial()

            PrimaryButtonMrcy(
                text = "Enabled • Rest",
                onClick = {},
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = PrimaryButtonPreviewFactory.rememberForcedInteractionSource(
                    PrimaryButtonPreviewFactory.VisualState.Rest
                ),
                primaryButtonTokens = materialTokens
            )

            PrimaryButtonMrcy(
                text = "Enabled • Pressed",
                onClick = {},
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = PrimaryButtonPreviewFactory.rememberForcedInteractionSource(
                    PrimaryButtonPreviewFactory.VisualState.Pressed
                ),
                primaryButtonTokens = materialTokens
            )

            PrimaryButtonMrcy(
                text = "Disabled",
                onClick = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                interactionSource = PrimaryButtonPreviewFactory.rememberForcedInteractionSource(
                    PrimaryButtonPreviewFactory.VisualState.Rest
                ),
                primaryButtonTokens = materialTokens
            )
        }
    }
}
