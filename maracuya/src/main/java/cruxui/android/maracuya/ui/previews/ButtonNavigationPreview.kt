package cruxui.android.maracuya.ui.previews

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.R
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper
import cruxui.android.maracuya.wrappers.components.buttons.navigation.simple.ButtonNavigationTokensOverrideRegistry

@Preview(name = "ButtonNavigation - Gallery", showBackground = true)
@Composable
fun ButtonNavigation_GalleryPreview() {
    PreviewWrapper(style = Mode.current) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ButtonNavigationMrcy(
                label = "Continuar",
                onClick = {},
                variant = ButtonNavigationVariant.PRIMARY,
            )

            ButtonNavigationMrcy(
                label = "Submit",
                onClick = {},
                variant = ButtonNavigationVariant.PRIMARY,
                buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                    iconToken = LocalLibraryIcons.current.general.check
                )
            )

            ButtonNavigationMrcy(
                label = "Loading",
                onClick = {},
                variant = ButtonNavigationVariant.PRIMARY,
                buttonNavigationBehavior = ButtonNavigationBehavior(
                    isLoading = true,
                ),
                onLoadingClick = {
                },
            )

            ButtonNavigationMrcy(
                label = "Back",
                onClick = {},
                variant = ButtonNavigationVariant.SECONDARY,
                buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                    iconToken = LocalLibraryIcons.current.arrows.keyboardBackspaceLeft
                )
            )

            ButtonNavigationMrcy(
                label = "Skip",
                onClick = {},
                variant = ButtonNavigationVariant.SECONDARY,
                buttonNavigationBehavior = ButtonNavigationBehavior(showIcon = false),
            )

            ButtonNavigationMrcy(
                label = "Pagar servicios",
                onClick = {},
                variant = ButtonNavigationVariant.PRIMARYSQUARE,
                buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                    iconToken = LocalLibraryIcons.current.general.contract.copy(
                        iconSize = 24,
                    ),
                ),
            )


            ButtonNavigationMrcy(
                label = "Sinpe\nmóvil",
                onClick = {},
                variant = ButtonNavigationVariant.SECONDARYSQUARE,
                buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                    iconToken = LocalLibraryIcons.current.general.contract.copy(
                        iconSize = 24,
                    ),
                ),
            )


            ButtonNavigationMrcy(
                modifier = Modifier.width(60.dp),
                label = null,
                onClick = {},
                buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                    iconToken = LocalLibraryIcons.current.arrows.navigateBefore,
                    iconColor = LocalLibraryColorTokens.current.fgMuted,
                    containerColor = LocalLibraryColorTokens.current.transparent,
                    borderContainerColor = LocalLibraryColorTokens.current.transparent,
                )
            )

            ButtonNavigationMrcy(
                label = "No clickeable",
                onClick = {},
                enabled = false,
                variant = ButtonNavigationVariant.PRIMARY,
                buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                    iconToken = LocalLibraryIcons.current.arrows.keyboardBackspaceLeft
                )
            )

            ButtonNavigationMrcy(
                label = "No clickeable",
                onClick = {},
                enabled = false,
                variant = ButtonNavigationVariant.SECONDARY,
                buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                    iconToken = LocalLibraryIcons.current.arrows.keyboardBackspaceRight
                )
            )
        }
    }
}

private const val XML_PREVIEW_TOKENS_OVERRIDE = "mytokensoverride"

private const val XML_PREVIEW_TOKENS_OVERRIDE_SQUARE = "mytokensoverridesquare"

private const val XML_PREVIEW_TOKENS_OVERRIDE_ICON = "mytokensoverride_icon"

@Preview(name = "ButtonNavigationWrp XML - Tokens override registry", showBackground = true)
@Composable
fun ButtonNavigationWrp_XmlTokensOverridePreview() {
    PreviewWrapper(style = Mode.current) {
        ButtonNavigationTokensOverrideRegistry.register(
            XML_PREVIEW_TOKENS_OVERRIDE,
        ) { colors, _, icons, _ ->
            ButtonNavigationTokensOverride(
                containerColor = colors.transparent,
                contentColor = colors.fgDefault,
                contentPressColor = colors.fgOnInverse,
                hoverContainerColor = colors.fgDefault,
                borderContainerColor = colors.fgDefault,
                iconColor = colors.fgDefault,
                iconToken = icons.general.check,
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                ButtonNavigationTokensOverrideRegistry.unregister(XML_PREVIEW_TOKENS_OVERRIDE)
            }
        }

        ButtonNavigationTokensOverrideRegistry.register(
            XML_PREVIEW_TOKENS_OVERRIDE_SQUARE,
        ) { colors, _, icons, _ ->
            ButtonNavigationTokensOverride(
                iconToken = icons.general.contract.copy(
                    iconSize = 24,
                ),
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                ButtonNavigationTokensOverrideRegistry.unregister(XML_PREVIEW_TOKENS_OVERRIDE_SQUARE)
            }
        }

        ButtonNavigationTokensOverrideRegistry.register(
            XML_PREVIEW_TOKENS_OVERRIDE_ICON,
        ) { colors, _, icons, _ ->
            ButtonNavigationTokensOverride(
                iconToken = icons.arrows.navigateBefore,
                iconColor = colors.fgMuted,
                containerColor = colors.transparent,
                borderContainerColor = colors.transparent,
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                ButtonNavigationTokensOverrideRegistry.unregister(XML_PREVIEW_TOKENS_OVERRIDE_ICON)
            }
        }

        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.button_navigation_wrp_preview, null)
            },
        )
    }
}
