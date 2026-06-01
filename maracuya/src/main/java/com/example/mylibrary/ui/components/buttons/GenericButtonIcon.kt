package com.example.mylibrary.ui.components.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.utils.composeadapters.IconComposeAdapter
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter
import com.example.mylibrary.atoms.LibraryIconTokens
import com.example.mylibrary.tokens.base.TypographyToken
import com.example.mylibrary.ui.components.utils.IconPosition
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter

//TODO deberia poderse enviar por parametro la posicion del icono
@Composable
fun GenericButtonIcon(
    text: String,
    iconPosition : IconPosition,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonIconTokens: ButtonIconTokens? = null
) {
    // Resuelve tokens (prioridad: explícito -> semántica -> fallback)
    val libraryColors = LocalLibraryColorTokens.current!!
    val libraryIcons = LocalLibraryIcons.current
    val fontFamily = LocalFontFamily.current
    val typographyTokens = LocalLibraryTypography.current

    val resolvedTokens = when {
        buttonIconTokens != null -> buttonIconTokens
        libraryIcons != null -> {
            ButtonIconTokens(
                icon = libraryIcons.general.modeHeat,
                iconTint = libraryColors.fgOnInverse,
                textTypography = typographyTokens.buttons,
                textColor = libraryColors.fgOnInverse,
                iconPosition = iconPosition
            )
        }
        else -> {
            ButtonIconTokens(
                icon = libraryIcons.general.modeHeat,
                iconTint = libraryColors.fgOnInverse,
                textTypography = TypographyToken(
                    fontSize = 16f,
                    lineHeight = 20f,
                    letterSpacing = 0f,
                    weight = 700,
                    italic = false
                ),
                textColor = libraryColors.fgOnInverse,
                iconPosition = iconPosition
            )
        }
    }

    // Convertir tokens a Compose
    val iconTintCompose = ColorComposeAdapter.toComposeColor(resolvedTokens.iconTint)
    val textColorCompose = ColorComposeAdapter.toComposeColor(resolvedTokens.textColor)

    val textStyle = TypographyComposeAdapter.toTextStyle(
        token = resolvedTokens.textTypography,
        fontFamily = FontFamiliesComposeAdapter.toCompose(fontFamily)
    ).copy(color = textColorCompose)

    Button(onClick = onClick, modifier = modifier) {
        Row {
            if (resolvedTokens.iconPosition == IconPosition.START) {
                IconComposeAdapter.Render(
                    icon = resolvedTokens.icon,
                    fillColor = iconTintCompose,
                    contentDescription = null,
                    size = 18.dp
                )
                Spacer(Modifier.width(8.dp))
            }

            Text(text = text, style = textStyle)

            if (resolvedTokens.iconPosition == IconPosition.END) {
                Spacer(Modifier.width(8.dp))
                IconComposeAdapter.Render(
                    icon = resolvedTokens.icon,
                    fillColor = iconTintCompose,
                    contentDescription = null
                )
            }
        }
    }
}
