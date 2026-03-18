package com.example.myfirstapplication.lookandfeel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.theme.MyLibraryTheme
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconMrcy
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconTokenGroup
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconTokensOverride
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.ui.components.labels.LabelTokensOverride

@Composable
fun BiometricsDisclaimerScreen (){
    MyLibraryTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopContentNavBiometrics()

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                )

                CentralContentBiometrics(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 40.dp)
                )
            }

            BottomContentBiometrics(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 24.dp)
            )
        }
    }
}

@Composable
private fun TopContentNavBiometrics(modifier: Modifier = Modifier) {
    NavigationToolbar(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
        //.background(color = Color.Blue)
    )
}

@Composable
private fun NavigationToolbar(modifier: Modifier = Modifier) {
    val icons = LocalLibraryIcons.current
    val colors = LocalLibraryColorTokens.current
    val typography = LocalLibraryTypography.current

    Box(modifier = modifier) {
        LabelMrcy(
            text = "¡Bienvenidx, DaviCliente!",
            labelTokensOverride = LabelTokensOverride(
                foregroundDefault = colors.fgDefault,
                labelTypography = typography.headline24,
            )
        )

        ButtonIconMrcy(
            modifier = Modifier.align(Alignment.CenterEnd),
            onTrailingClick = { },
            group = ButtonIconTokenGroup.SECONDARY,
            buttonIconTokensOverride = ButtonIconTokensOverride(
                iconToken = icons.alerts.help.copy(iconSize = 24),
            ),
        )
    }
}


@Composable
private fun CentralContentBiometrics(modifier: Modifier = Modifier) {

}


@Composable
private fun BottomContentBiometrics(modifier: Modifier = Modifier) {

}
