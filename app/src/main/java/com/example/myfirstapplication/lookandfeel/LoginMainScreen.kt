package com.example.myfirstapplication.lookandfeel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mylibrary.R
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.theme.MyLibraryTheme
import com.example.mylibrary.ui.components.buttons.primary.PrimaryButtonMrcy
import com.example.mylibrary.ui.components.buttons.secondary.SecondaryButtonMrcy
import com.example.mylibrary.ui.components.buttons.secondary.SecondaryButtonTokensOverride
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.ui.components.labels.LabelTokensOverride
import com.example.mylibrary.ui.components.layouts.LayoutPilotWellkommen

@Composable
fun LoginMainScreen () {
    MyLibraryTheme {
        LayoutPilotWellkommen {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                TopContent(
                    modifier = Modifier.align(Alignment.TopStart)
                )

                BottonContent(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun TopContent(modifier: Modifier = Modifier) {

    val colors = LocalLibraryColorTokens.current

    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 90.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo_davibank_house_empty),
            contentDescription = "Davivienda Logo",
            modifier = Modifier.size(64.dp)
        )

        LabelMrcy(
            text = "¡Hola! Bienvenido a su nuevo Davivienda",
            labelTokensOverride = LabelTokensOverride(
                foregroundDefault = colors.fgOnInverse,
            )
        )

        LabelMrcy(
            text = "Acceda con la seguridad de siempre.",
            labelTokensOverride = LabelTokensOverride(
                foregroundDefault = colors.fgOnInverse
            )
        )
    }
}

@Composable
fun BottonContent(modifier: Modifier = Modifier) {
    val colors = LocalLibraryColorTokens.current

    Column(
        modifier = modifier.padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PrimaryButtonMrcy(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            text = "Soy cliente",
            onClick = {},
        )

        Spacer(Modifier.height(2.dp))

        SecondaryButtonMrcy(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            text = "Soy nuevo en Davivienda",
            onClick = {},
            secondaryButtonTokensOverride = SecondaryButtonTokensOverride(
                contentColor = colors.fgDefault,
                borderContainerColor = colors.fgDefault,
                hoverContainerColor = colors.fgDefault
            )
        )

        Spacer(Modifier.height(10.dp))

        LabelMrcy(
            text = "¿Necesita ayuda?",
            labelTokensOverride = LabelTokensOverride(
                foregroundDefault = colors.borderFocus
            ),
            onClick = { println("hola que tal") }
        )
    }
}
