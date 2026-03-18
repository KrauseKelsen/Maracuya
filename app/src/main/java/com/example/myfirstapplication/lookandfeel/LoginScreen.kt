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
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.theme.MyLibraryTheme
import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconMrcy
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconTokenGroup
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconTokensOverride
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconVariant
import com.example.mylibrary.ui.components.buttons.primary.PrimaryButtonMrcy
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.ui.components.labels.LabelTokensOverride
import com.example.mylibrary.ui.components.textfields.TextFieldMrcy
import com.example.mylibrary.ui.components.textfields.TextFieldVariant
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter

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
        ButtonIconMrcy(
            modifier = Modifier.align(Alignment.CenterStart),
            onTrailingClick = { },
            group = ButtonIconTokenGroup.SECONDARY,
            buttonIconTokensOverride = ButtonIconTokensOverride(
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

        ButtonIconMrcy(
            modifier = Modifier.align(Alignment.CenterEnd),
            onTrailingClick = { },
            group = ButtonIconTokenGroup.SECONDARY,
            buttonIconTokensOverride = ButtonIconTokensOverride(
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

        PrimaryButtonMrcy(
            modifier = Modifier.fillMaxWidth(),
            text = "Continuar",
            onClick = {},
        )
    }
}

@Composable
private fun BottomContent(modifier: Modifier = Modifier) {
    ButtonIconMrcy(
        modifier = modifier.padding(bottom = 30.dp),
        label = "Ingresa con GAUDI",
        variant = ButtonIconVariant.IconLeft,
        onTrailingClick = {},
        buttonIconTokensOverride = ButtonIconTokensOverride(
            containerColor = ColorToken(0xFF003764),
            iconToken = LocalLibraryIcons.current.general.cardioLoad
        )
    )
}