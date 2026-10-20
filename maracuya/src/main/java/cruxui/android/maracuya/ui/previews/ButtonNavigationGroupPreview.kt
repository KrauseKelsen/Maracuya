package cruxui.android.maracuya.ui.previews

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import cruxui.android.maracuya.R
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.ui.components.buttons.navigation.group.ButtonNavigationGroupMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.core.ButtonNavigationChild
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.tokens.ButtonNavigationChildTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.group.child.variant.ButtonNavigationChildVariant
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant
import cruxui.android.maracuya.wrappers.components.buttons.navigation.simple.ButtonNavigationWrp
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper

@Preview(name = "ButtonNavigationGroup - Fixed variants", showBackground = true, heightDp = 800)
@Composable
fun ButtonNavigationGroup_FixedVariantsPreview() {
    PreviewWrapper(style = Mode.current) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            ButtonNavigationGroupMrcy(
                buttonSpacing = 12.dp,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ButtonNavigationMrcy(
                    label = "Pagar servicios",
                    onClick = {},
                    variant = ButtonNavigationVariant.PRIMARYSQUARE,
                    buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                        iconToken = LocalLibraryIcons.current.general.bolt.copy(iconSize = 24),
                    ),
                )
                ButtonNavigationMrcy(
                    label = "Sinpe\nmóvil",
                    onClick = {},
                    variant = ButtonNavigationVariant.SECONDARYSQUARE,
                    buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                        iconToken = LocalLibraryIcons.current.general.contract.copy(iconSize = 24),
                    ),
                )
                ButtonNavigationMrcy(
                    label = "Seguridad",
                    onClick = {},
                    variant = ButtonNavigationVariant.PRIMARYSQUARE,
                    buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                        iconToken = LocalLibraryIcons.current.general.fingerprint.copy(iconSize = 24),
                    ),
                )
                ButtonNavigationMrcy(
                    label = "Comercios",
                    onClick = {},
                    variant = ButtonNavigationVariant.SECONDARYSQUARE,
                    buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                        iconToken = LocalLibraryIcons.current.general.store.copy(iconSize = 24),
                    ),
                )
            }

            ButtonNavigationGroupMrcy(
                variant = ButtonNavigationGroupVariant.BACK_CONTINUE,
                onLeadingButtonClick = {},
                onTrailingButtonClick = {},
            )

            ButtonNavigationGroupMrcy(
                variant = ButtonNavigationGroupVariant.BACK_SUBMIT,
                onLeadingButtonClick = {},
                onTrailingButtonClick = {},
            )

            ButtonNavigationGroupMrcy(
                variant = ButtonNavigationGroupVariant.BACK_CONTINUE,
                buttonNavigationGroupBehavior = ButtonNavigationGroupBehavior.Loading,
                onLeadingButtonClick = {},
                onTrailingButtonClick = {},
            )

            ButtonNavigationGroupMrcy(
                variant = ButtonNavigationGroupVariant.SKIP_CONTINUE,
                onLeadingButtonClick = {},
                onTrailingButtonClick = {},
            )

            ButtonNavigationGroupMrcy(
                variant = ButtonNavigationGroupVariant.SKIP_SUBMIT,
                onLeadingButtonClick = {},
                onTrailingButtonClick = {},
            )

            ButtonNavigationGroupMrcy(
                variant = ButtonNavigationGroupVariant.SKIP_CONTINUE,
                buttonNavigationGroupBehavior = ButtonNavigationGroupBehavior.Loading,
                onLeadingButtonClick = {},
                onTrailingButtonClick = {},
            )

            ButtonNavigationGroupMrcy(
                variant = ButtonNavigationGroupVariant.BACK_CONTINUE,
                leadingLabel = "Volver",
                trailingLabel = "Siguiente",
                leadingButtonWidth = 144.dp,
                trailingButtonWidth = 184.dp,
                buttonSpacing = 12.dp,
                onLeadingButtonClick = {},
                onTrailingButtonClick = {},
            )

            ButtonNavigationGroupMrcy(
                children = listOf(
                    ButtonNavigationChild(
                        variant = ButtonNavigationChildVariant.SECONDARY,
                        onClick = {},
                    ),
                    ButtonNavigationChild(
                        variant = ButtonNavigationChildVariant.PRIMARY,
                        onClick = {},
                    ),
                    ButtonNavigationChild(
                        variant = ButtonNavigationChildVariant.SECONDARY,
                        onClick = {},
                        buttonNavigationChildTokensOverride = ButtonNavigationChildTokensOverride(
                            iconToken = LocalLibraryIcons.current.alerts.info,
                            iconColor = LocalLibraryColorTokens.current.fgMuted,
                            containerColor = LocalLibraryColorTokens.current.transparent,
                            borderContainerColor = LocalLibraryColorTokens.current.transparent,
                        )
                    ),
                    ButtonNavigationChild(
                        variant = ButtonNavigationChildVariant.PRIMARY,
                        onClick = {},
                    ),
                ),
            )


            ButtonNavigationGroupMrcy(
                buttonSpacing = 10.dp,
                children = listOf(
                    ButtonNavigationChild(
                        variant = ButtonNavigationChildVariant.SECONDARY,
                        onClick = {},
                        buttonNavigationChildTokensOverride = ButtonNavigationChildTokensOverride(
                            iconToken = LocalLibraryIcons.current.general.cardioLoad,
                        )
                    ),
                    ButtonNavigationChild(
                        variant = ButtonNavigationChildVariant.PRIMARY,
                        onClick = {},
                        buttonNavigationChildTokensOverride = ButtonNavigationChildTokensOverride(
                            iconToken = LocalLibraryIcons.current.alerts.info,
                        )
                    ),
                    ButtonNavigationChild(
                        variant = ButtonNavigationChildVariant.PRIMARY,
                        onClick = {},
                        buttonNavigationChildTokensOverride = ButtonNavigationChildTokensOverride(
                            iconToken = LocalLibraryIcons.current.general.fingerprint,
                        )
                    ),
                ),
            )

            ButtonNavigationGroupMrcy(
                children = listOf(
                    ButtonNavigationChild(
                        label = "Omitir",
                        variant = ButtonNavigationChildVariant.SECONDARY,
                        showIcon = false,
                        onClick = {},
                    ),
                    ButtonNavigationChild(
                        label = "Enviar",
                        variant = ButtonNavigationChildVariant.PRIMARY,
                        onClick = {},
                    ),
                ),
                buttonSpacing = 12.dp,
            )
        }
    }
}

@Preview(name = "ButtonNavigationGroupWrp XML - Fixed variants", showBackground = true, heightDp = 800)
@Composable
fun ButtonNavigationGroupWrp_XmlFixedVariantsPreview() {
    PreviewWrapper(style = Mode.current) {
        val icons = LocalLibraryIcons.current.general
        val servicesTokens = mapOf(
            R.id.buttonNavigationServicesOneXml to ButtonNavigationTokensOverride(
                iconToken = icons.bolt.copy(iconSize = 24),
            ),
            R.id.buttonNavigationServicesTwoXml to ButtonNavigationTokensOverride(
                iconToken = icons.contract.copy(iconSize = 24),
            ),
            R.id.buttonNavigationServicesThreeXml to ButtonNavigationTokensOverride(
                iconToken = icons.fingerprint.copy(iconSize = 24),
            ),
            R.id.buttonNavigationServicesFourXml to ButtonNavigationTokensOverride(
                iconToken = icons.store.copy(iconSize = 24),
            ),
        )
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.button_navigation_group_wrp_preview, null)
            },
            update = { view ->
                // XML declara los botones; el token del ícono se obtiene del tema de la preview.
                servicesTokens.forEach { (id, tokens) ->
                    view.findViewById<ButtonNavigationWrp>(id).buttonNavigationTokensOverride = tokens
                }
            },
        )
    }
}
