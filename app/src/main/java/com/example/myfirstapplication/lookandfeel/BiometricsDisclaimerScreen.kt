package com.example.myfirstapplication.lookandfeel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.R
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.theme.MyLibraryTheme
import cruxui.android.maracuya.ui.components.buttons.button.ButtonMrcy
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokensOverride
import cruxui.android.maracuya.ui.components.buttons.icon.ButtonIconMrcy
import cruxui.android.maracuya.ui.components.buttons.icon.ButtonIconTokenGroup
import cruxui.android.maracuya.ui.components.buttons.icon.ButtonIconTokensOverride
import cruxui.android.maracuya.ui.components.labels.LabelMrcy
import cruxui.android.maracuya.ui.components.labels.LabelTokensOverride
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.IconComposeAdapter

/**
 * Valores visuales centralizados para mantener consistencia y facilitar mantenimiento del layout.
 */
private object BiometricsDisclaimerLayoutDefaults {
    val sectionSpacing = 20.dp
    val dividerHorizontalPadding = 20.dp
    val cardCornerRadius = 16.dp
    val cardInnerPadding = 16.dp
    val cardContentSpacing = 8.dp
    val cardIconColumnEndPadding = 12.dp
    val cardIconTopPadding = 2.dp
}

@Composable
fun BiometricsDisclaimerScreen (){
    MyLibraryTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopContentNavBiometrics(
                    modifier = Modifier
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
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
                    .padding(horizontal = 40.dp)
            )
        }
    }
}

@Composable
private fun TopContentNavBiometrics(modifier: Modifier = Modifier) {
    NavigationToolbar(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 60.dp,
                start = 40.dp,
                end = 20.dp
            )
    )
}

@Composable
private fun NavigationToolbar(modifier: Modifier = Modifier) {
    val icons = LocalLibraryIcons.current
    val colors = LocalLibraryColorTokens.current
    val typography = LocalLibraryTypography.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LabelMrcy(
            modifier = Modifier.weight(1f),
            text = "¡Bienvenidx, Davicliente!",
            labelTokensOverride = LabelTokensOverride(
                foregroundDefault = colors.fgDefault,
                labelTypography = typography.headline24,
            ),
            limitMaxLabel = false
        )

        ButtonIconMrcy(
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
    val colors = LocalLibraryColorTokens.current
    val icons = LocalLibraryIcons.current
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
                .padding(top = BiometricsDisclaimerLayoutDefaults.sectionSpacing)
        )

        /**
         * Ilustración principal del disclaimer de biometría.
         * Se ubica inmediatamente debajo del primer divisor, respetando el espaciado vertical definido.
         */
        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.biometricsdisclamerscreen_imagepage),
            contentDescription = "Ilustración de disclaimer biométrico",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = BiometricsDisclaimerLayoutDefaults.sectionSpacing)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BiometricsDisclaimerLayoutDefaults.dividerHorizontalPadding),
            color = ColorComposeAdapter.toComposeColor(colors.borderSubtle)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = BiometricsDisclaimerLayoutDefaults.sectionSpacing)
        )

        BiometricBenefitsCard(
            title = "Olvidese de las contraseñas",
            description = "Active el ingreso con biometria para entrar a su app de forma rapida y segura.",
            icon = {
                IconComposeAdapter.Render(icons.general.fingerprint)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Tarjeta informativa para beneficios biométricos.
 * Estructura en dos columnas:
 * 1) Columna angosta izquierda con icono alineado arriba.
 * 2) Columna derecha expandida con título y descripción.
 */
@Composable
private fun BiometricBenefitsCard(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalLibraryColorTokens.current
    val typography = LocalLibraryTypography.current

    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = ColorComposeAdapter.toComposeColor(colors.borderSubtle),
                shape = RoundedCornerShape(BiometricsDisclaimerLayoutDefaults.cardCornerRadius)
            ),
        shape = RoundedCornerShape(BiometricsDisclaimerLayoutDefaults.cardCornerRadius)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BiometricsDisclaimerLayoutDefaults.cardInnerPadding),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.padding(end = BiometricsDisclaimerLayoutDefaults.cardIconColumnEndPadding),
                verticalArrangement = Arrangement.Top
            ) {
                icon()
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(BiometricsDisclaimerLayoutDefaults.cardContentSpacing)
            ) {
                LabelMrcy(
                    text = title,
                    labelTokensOverride = LabelTokensOverride(
                        labelTypography = typography.subtitle2
                    ),
                    limitMaxLabel = false
                )
                LabelMrcy(
                    text = description,
                    labelTokensOverride = LabelTokensOverride(
                        labelTypography = typography.subtitle3
                    ),
                    limitMaxLabel = false
                )
            }
        }
    }
}


@Composable
private fun BottomContentBiometrics(modifier: Modifier = Modifier) {
    val colors = LocalLibraryColorTokens.current
    val typography = LocalLibraryTypography.current

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 50.dp)
    ) {
        ButtonMrcy(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            text = "Activar biometría",
            showProgressOnPress = false
        )

        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        )
        ButtonMrcy(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            text = "Quizá más tarde",
            buttonTokensOverride = ButtonTokensOverride(
                contentColor = colors.fgMuted,
                textTypography = typography.subtitle2,
                contentPressColor = colors.fgMuted,
                borderContainerColor = colors.transparent,
                hoverContainerColor = colors.transparent,
            )
        )

        Spacer(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        )
    }
}
