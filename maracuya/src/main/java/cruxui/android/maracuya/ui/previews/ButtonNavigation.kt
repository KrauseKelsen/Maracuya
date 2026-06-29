package cruxui.android.maracuya.ui.previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.ui.components.buttons.navigation.ButtonNavigationBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.ButtonNavigationMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.ButtonNavigationVariant
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper

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
                    startsLoadingOnClick = true,
                ),
                onTrailingClick = {},
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
                label = "Continue",
                onClick = {},
                variant = ButtonNavigationVariant.PRIMARY,
            )


            ButtonNavigationMrcy(
                label = null,
                onClick = {},
                variant = ButtonNavigationVariant.PRIMARY,
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
