package com.example.mylibrary.utils.composeadapters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.tokens.base.IconToken

/**
 * IconComposeAdapter
 *
 * Renderiza un ícono usando únicamente el asset "filled".
 * El tint es opcional — si `fillColor` es Color.Unspecified, no se aplica ningún tint.
 *
 * Esta clase actúa como puente entre los Token Icons (Kotlin puro)
 * y la capa de Compose.
 */
object IconComposeAdapter {


    @Composable
    fun Render(
        icon: IconToken,
        modifier: Modifier = Modifier,
        size: Dp = Dp.Unspecified,
        fillColor: Color = Color.Unspecified,
        contentDescription: String? = null
    ) {
        val fallbackColor = runCatching {
            ColorComposeAdapter.toComposeColor(LocalLibraryColorTokens.current.brandPrimary)
        }.getOrElse { Color.Unspecified }

        val resolvedFillColor = when {
            fillColor != Color.Unspecified -> fillColor
            icon.iconColor != null -> ColorComposeAdapter.toComposeColor(icon.iconColor)
            else -> fallbackColor
        }

        Box(modifier = modifier.size(size)) {
            Image(
                painter = painterResource(icon.filledResId),
                contentDescription = contentDescription,
                modifier = Modifier.size(size),
                colorFilter =
                    if (resolvedFillColor != Color.Unspecified)
                        ColorFilter.tint(resolvedFillColor)
                    else
                        null
            )
        }
    }
}
