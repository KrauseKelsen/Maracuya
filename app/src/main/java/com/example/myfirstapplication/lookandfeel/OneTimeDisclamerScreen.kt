package com.example.myfirstapplication.lookandfeel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mylibrary.R
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.theme.MyLibraryTheme
import com.example.mylibrary.ui.components.buttons.primary.PrimaryButtonMrcy
import com.example.mylibrary.ui.components.checkboxes.CheckBoxMrcy
import com.example.mylibrary.ui.components.labels.LabelMrcy
import com.example.mylibrary.ui.components.labels.LabelTokensOverride
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter

@Composable
fun OneTimeDisclamerScreen() {
    MyLibraryTheme {
        var acceptedPolicy by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopContentOneTimeDisclamer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp, vertical = 40.dp)
                )

                CentralContentOneTimeDisclamer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 40.dp),
                    checked = acceptedPolicy,
                    onCheckedChange = { acceptedPolicy = it }
                )
            }

            BottomContentOneTimeDisclamer(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 90.dp),
                buttonEnabled = acceptedPolicy
            )
        }
    }
}

@Composable
private fun TopContentOneTimeDisclamer(modifier: Modifier = Modifier) {
    val colors = LocalLibraryColorTokens.current
    val typography = LocalLibraryTypography.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        LabelMrcy(
            text = "Su seguridad es nuestra prioridad.",
            labelTokensOverride = LabelTokensOverride(
                foregroundDefault = colors.fgDefault,
                labelTypography = typography.headline21
            ),
            limitMaxLabel = false
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .height(4.dp)
                .background(ColorComposeAdapter.toComposeColor(colors.brandPrimary))
        )
    }
}

@Composable
private fun CentralContentOneTimeDisclamer(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val colors = LocalLibraryColorTokens.current
    val typography = LocalLibraryTypography.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.onetimedisclamerscreen_imagepage),
            contentDescription = "Ilustración de seguridad",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        LabelMrcy(
            text = "Para proteger su dinero, nuestra app analiza las características técnicas de este celular y verifica que no existan aplicaciones maliciosas que puedan robar su información. Si detectamos un riesgo o un ingreso inusual, pausaremos la transacción y le pediremos verificar su identidad para asegurar que es usted.",
            labelTokensOverride = LabelTokensOverride(
                foregroundDefault = colors.fgDefault,
                labelTypography = typography.headline16
            ),
            limitMaxLabel = false
        )

        CheckBoxMrcy(
            checked = checked,
            onCheckedChange = onCheckedChange,
            checkBoxText = "Estoy de acuerdo con las políticas de seguridad.",
            optionalText = false,
            showLabelIcon = false,
            showInputBorder = false,
        )
    }
}

@Composable
private fun BottomContentOneTimeDisclamer(
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean
) {
    Column(
        modifier = modifier
    ) {
        PrimaryButtonMrcy(
            modifier = Modifier.fillMaxWidth(),
            onClick = { },
            text = "Continuar",
            enabled = buttonEnabled,
            showProgressOnPress = false
        )
    }
}
