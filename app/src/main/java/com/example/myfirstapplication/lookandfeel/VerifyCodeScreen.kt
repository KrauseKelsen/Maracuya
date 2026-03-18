package com.example.myfirstapplication.lookandfeel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconMrcy
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconTokenGroup
import com.example.mylibrary.ui.components.buttons.icon.ButtonIconTokensOverride
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.ui.components.labels.LabelTokensOverride
import com.example.mylibrary.ui.components.textfields.TextFieldMrcy
import com.example.mylibrary.ui.components.textfields.TextFieldVariant
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter
import kotlinx.coroutines.delay

@Composable
fun VerifyCodeScreen() {
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
                        .padding(top = 60.dp)
                )

                CentralContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@Composable
private fun TopContentNav(modifier: Modifier = Modifier) {
    NavigationToolbar(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
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
            icon = icons.extension.lgDavibankHouseEmpty
                .copy(
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
                iconToken = icons.alerts.help
            ),
        )
    }
}

@Composable
private fun SecurityCode(modifier: Modifier = Modifier) {
    val typography = LocalLibraryTypography.current

    var pinValues by remember { mutableStateOf(List(4) { "" }) }

    LabelMrcy(
        modifier = modifier,
        text = "Ingrese el código de 6 dígitos que acabamos de enviar al **** 3145",
        labelTokensOverride = LabelTokensOverride(
            labelTypography = typography.subtitle3,
        ),
        limitMaxLabel = false
    )

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    )

    TextFieldMrcy(
        modifier = modifier,
        value = pinValues,
        onValueChange = { pinValues = it },
        label = "Código de seguridad",
        length = 6,
        textFieldVariant = TextFieldVariant.PIN
    )
}

@Composable
private fun CentralContent(modifier: Modifier = Modifier) {
    var timeLeftInSecond by remember { mutableIntStateOf(59) }
    var isRunning by remember { mutableStateOf(false) }

    val colors = LocalLibraryColorTokens.current

    LaunchedEffect(isRunning, timeLeftInSecond) {
        if (isRunning && timeLeftInSecond > 0) {
            delay(1000)
            timeLeftInSecond--
        } else if (timeLeftInSecond == 0) {
            isRunning = false
        }
    }

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

        SecurityCode()

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            LabelMrcy(
                modifier = Modifier.align(Alignment.Center),
                text = "¿No recibió su código?",
                labelTokensOverride = LabelTokensOverride(
                    foregroundDefault = colors.borderFocus
                ),
                onClick = {
                    timeLeftInSecond = 59
                    isRunning = true
                }
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        )

        ResendTimerScreen(timeLeftInSecond)
    }
}

@Composable
private fun ResendTimerScreen(
    timeLeftInSecond: Int,
) {
    val colors = LocalLibraryColorTokens.current
    val typography = LocalLibraryTypography.current

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            LabelMrcy(
                modifier = Modifier,
                text = "Reenviar en ",
                labelTokensOverride = LabelTokensOverride(
                    foregroundDefault = colors.fgMuted,
                    labelTypography = typography.headline16
                ),
            )

            LabelMrcy(
                modifier = Modifier,
                text = formatTime(timeLeftInSecond),
                labelTokensOverride = LabelTokensOverride(
                    foregroundDefault = colors.fgMuted,
                    labelTypography = LocalLibraryTypography.current.headline16.copy(
                        weight = 800
                    )
                ),
            )
        }

    }
}

private fun formatTime(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
