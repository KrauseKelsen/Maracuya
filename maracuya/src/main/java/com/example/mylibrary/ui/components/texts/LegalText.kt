package com.example.mylibrary.ui.components.texts

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
import com.example.mylibrary.utils.composeadapters.TypographyComposeAdapter

/**
 * LegalText consume su Component Token Layer (LegalTextTokens).
 *
 * Prioridad de decisión del TextStyle final:
 * 1) tokens.textStyle (provisto por fromLibrary/fromMaterial/custom)
 *
 */
@Composable
fun LegalText(
    text: String,
    modifier: Modifier = Modifier,
    textTokens: LegalTextTokens? = null,
) {
    val fontFamily = LocalFontFamily.current
    val colors = LocalLibraryColorTokens.current

    val resolvedTokens: LegalTextTokens = when {
        textTokens != null -> textTokens
        colors != null -> LegalTextTokens.fromCorporate()
        else -> LegalTextTokens.fromMaterial()
    }

    val resolvedStyle = TypographyComposeAdapter.toTextStyle(
        token = resolvedTokens.typographyToken,
        fontFamily = FontFamiliesComposeAdapter.toCompose(fontFamily)
    ).copy(
        color = ColorComposeAdapter.toComposeColor(resolvedTokens.colorToken)
    )

    Box(modifier = modifier) {
        Text(text = text, style = resolvedStyle)
    }
}