package com.example.myfirstapplication.lookandfeel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.theme.MyLibraryTheme
import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.ui.components.buttons.simple.ButtonMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.ButtonNavigationMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.tokens.ButtonNavigationTokensOverride
import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant
import cruxui.android.maracuya.ui.components.labels.LabelMrcy
import cruxui.android.maracuya.ui.components.labels.LabelTokensOverride
import cruxui.android.maracuya.ui.components.textfields.TextFieldMrcy
import cruxui.android.maracuya.ui.components.textfields.TextFieldVariant
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.IconComposeAdapter

@Composable
fun LoginScreen() {
    MyLibraryTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopContentNav()

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                )

                CentralContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 40.dp)
                )
            }

            BottomContent(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 24.dp)
            )
        }
    }
}

@Composable
private fun TopContentNav(modifier: Modifier = Modifier) {
    NavigationToolbar(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 60.dp,
                start = TopNavigationDefaults.HorizontalScreenPadding,
                end = TopNavigationDefaults.HorizontalScreenPadding,
            )
    )
}

@Composable
private fun NavigationToolbar(modifier: Modifier = Modifier) {
    val icons = LocalLibraryIcons.current

    Box(modifier = modifier) {
        ButtonNavigationMrcy(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { },
            variant = ButtonNavigationVariant.SECONDARY,
            buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                iconToken = icons.arrows.navigateBefore,
                iconColor = LocalLibraryColorTokens.current.fgMuted
            ),
        )

        IconComposeAdapter.Render(
            icon = icons.extension.lgDavibankHouseEmpty.copy(
                iconColor = LocalLibraryColorTokens.current.brandPrimary
            ),
            size = 32.dp,
            contentDescription = "LogoDavibank",
            modifier = Modifier.align(Alignment.Center)
        )

        ButtonNavigationMrcy(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { },
            variant = ButtonNavigationVariant.SECONDARY,
            buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
                iconToken = icons.alerts.help,
            ),
        )
    }
}

@Composable
private fun CentralContent(modifier: Modifier = Modifier) {
    val colors = LocalLibraryColorTokens.current
    val typography = LocalLibraryTypography.current

    var usernameTextValue by remember { mutableStateOf("") }
    var passwordTextValue by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxHeight(0.005f)
                .width(80.dp)
                .background(color = ColorComposeAdapter.toComposeColor(colors.brandPrimary))
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )

        LabelMrcy(
            text = "Ingrese con su usuario y contraseña de siempre de Davibank o Davivienda.",
            labelTokensOverride = LabelTokensOverride(
                labelTypography = typography.subtitle3,
            ),
            limitMaxLabel = false
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )

        TextFieldMrcy(
            modifier = Modifier.fillMaxWidth(),
            value = usernameTextValue,
            label = "Usuario",
            onValueChange = {
                usernameTextValue = it
            },
            placeholder = "Daviuser1234",
            textFieldVariant = TextFieldVariant.DEFAULT,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )

        TextFieldMrcy(
            modifier = Modifier.fillMaxWidth(),
            value = passwordTextValue,
            label = "Contraseña o clave virual",
            onValueChange = {
                passwordTextValue = it
            },
            placeholder = "***********",
            textFieldVariant = TextFieldVariant.PASSWORD,
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            LabelMrcy(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = "¿Olvidó sus datos?",
                labelTokensOverride = LabelTokensOverride(
                    foregroundDefault = colors.borderFocus
                ),
                onClick = { println("hola que tal") }
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )

        ButtonMrcy(
            modifier = Modifier.fillMaxWidth(),
            text = "Continuar",
            onClick = {},
        )
    }
}

@Composable
private fun BottomContent(modifier: Modifier = Modifier) {
    ButtonNavigationMrcy(
        modifier = modifier.padding(bottom = 30.dp),
        label = "Ingresa con GAUDI",
        onClick = {},
        variant = ButtonNavigationVariant.SECONDARY,
        buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
            containerColor = ColorToken(0xFF003764),
            contentColor = LocalLibraryColorTokens.current.fgOnInverse,
            contentPressColor = LocalLibraryColorTokens.current.fgOnInverse,
            hoverContainerColor = ColorToken(0xFF003764),
            borderContainerColor = ColorToken(0xFF003764),
            iconColor = LocalLibraryColorTokens.current.fgOnInverse,
            iconToken = LocalLibraryIcons.current.general.cardioLoad
        )
    )
}
