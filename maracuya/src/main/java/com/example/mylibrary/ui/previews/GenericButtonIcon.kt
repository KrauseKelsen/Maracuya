package com.example.mylibrary.ui.previews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconMrcy
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconTokenGroup
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconTokensOverride
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconTokensResolver
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconVariant
import com.example.mylibrary.ui.previews.core.Mode
import com.example.mylibrary.ui.previews.core.PreviewWrapper

@Preview(name = "ButtonIcon - Gallery", showBackground = true)
@Composable
fun ButtonIcon_GalleryPreview() {
    PreviewWrapper(style = Mode.current) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ButtonIconMrcy(
                label = "Continuar",
                group = ButtonIconTokenGroup.PRIMARY,
                variant = ButtonIconVariant.IconRight,
                onTrailingClick = {},
            )

            ButtonIconMrcy(
                label = "Submit",
                group = ButtonIconTokenGroup.PRIMARY,
                variant = ButtonIconVariant.IconRight,
                onTrailingClick = {},
                buttonIconTokensOverride = ButtonIconTokensOverride(
                    iconToken = LocalLibraryIcons.current.general.check
                )
            )


            ButtonIconMrcy(
                label = "Back",
                group = ButtonIconTokenGroup.SECONDARY,
                variant = ButtonIconVariant.IconLeft,
                onTrailingClick = {},
                buttonIconTokensOverride = ButtonIconTokensOverride(
                    iconToken = LocalLibraryIcons.current.arrows.keyboardBackspaceLeft
                )
            )

            ButtonIconMrcy(
                label = "Skip",
                group = ButtonIconTokenGroup.SECONDARY,
                onTrailingClick = {},
            )


            ButtonIconMrcy(
                label = "Loading",
                group = ButtonIconTokenGroup.PRIMARY,
                variant = ButtonIconVariant.IconRight,
                onTrailingClick = {},
            )


            ButtonIconMrcy(
                label = null,
                group = ButtonIconTokenGroup.PRIMARY,
                variant = ButtonIconVariant.IconRight,
                onTrailingClick = {},
            )

            ButtonIconMrcy(
                label = "No clickeable",
                group = ButtonIconTokenGroup.SECONDARY,
                variant = ButtonIconVariant.IconRight,
                onTrailingClick = null,
            )
        }
    }
}
