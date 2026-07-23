package cruxui.android.maracuya.ui.previews

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import cruxui.android.maracuya.R
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupButton
import cruxui.android.maracuya.ui.components.buttons.navigation.group.ButtonNavigationGroupMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.behavior.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper

@Preview(name = "ButtonNavigationGroup - Fixed variants", showBackground = true)
@Composable
fun ButtonNavigationGroup_FixedVariantsPreview() {
    PreviewWrapper(style = Mode.current) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
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
                buttons = listOf(
                    ButtonNavigationGroupButton(
                        variant = ButtonNavigationVariant.SECONDARY,
                        onClick = {},
                    ),
                    ButtonNavigationGroupButton(
                        variant = ButtonNavigationVariant.PRIMARY,
                        onClick = {},
                    ),
                    ButtonNavigationGroupButton(
                        variant = ButtonNavigationVariant.PRIMARY,
                        onClick = {},
                        buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                            iconToken = LocalLibraryIcons.current.alerts.info,
                            iconColor = LocalLibraryColorTokens.current.fgMuted,
                            containerColor = LocalLibraryColorTokens.current.transparent,
                            borderContainerColor = LocalLibraryColorTokens.current.transparent,
                        )
                    ),
                    ButtonNavigationGroupButton(
                        variant = ButtonNavigationVariant.PRIMARY,
                        buttonNavigationBehavior = ButtonNavigationBehavior.Default,
                        onClick = {},
                    ),
                ),
            )


            ButtonNavigationGroupMrcy(
                buttonSpacing = 10.dp,
                buttons = listOf(
                    ButtonNavigationGroupButton(
                        variant = ButtonNavigationVariant.SECONDARY,
                        onClick = {},
                        buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                            iconToken = LocalLibraryIcons.current.general.cardioLoad,
                        )
                    ),
                    ButtonNavigationGroupButton(
                        variant = ButtonNavigationVariant.PRIMARY,
                        onClick = {},
                        buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                            iconToken = LocalLibraryIcons.current.alerts.info,
                        )
                    ),
                    ButtonNavigationGroupButton(
                        variant = ButtonNavigationVariant.PRIMARY,
                        buttonNavigationBehavior = ButtonNavigationBehavior.Default,
                        onClick = {},
                        buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                            iconToken = LocalLibraryIcons.current.general.fingerprint,
                        )
                    ),
                ),
            )

            ButtonNavigationGroupMrcy(
                buttons = listOf(
                    ButtonNavigationGroupButton(
                        label = "Omitir",
                        variant = ButtonNavigationVariant.SECONDARY,
                        buttonNavigationBehavior = ButtonNavigationBehavior(showIcon = false),
                        onClick = {},
                    ),
                    ButtonNavigationGroupButton(
                        label = "Enviar",
                        variant = ButtonNavigationVariant.PRIMARY,
                        onClick = {},
                    ),
                ),
                buttonSpacing = 12.dp,
            )
        }
    }
}

@Preview(name = "ButtonNavigationGroupWrp XML - Fixed variants", showBackground = true)
@Composable
fun ButtonNavigationGroupWrp_XmlFixedVariantsPreview() {
    PreviewWrapper(style = Mode.current) {
        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.button_navigation_group_wrp_preview, null)
            },
        )
    }
}
