package cruxui.android.maracuya.ui.components.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryColorTokens
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.compositions.LocalLibraryTypography
import cruxui.android.maracuya.utils.composeadapters.IconComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.ColorComposeAdapter
import cruxui.android.maracuya.utils.composeadapters.TypographyComposeAdapter
import cruxui.android.maracuya.atoms.LibraryIconTokens
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.ui.components.utils.IconPosition
import cruxui.android.maracuya.utils.composeadapters.FontFamiliesComposeAdapter

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
