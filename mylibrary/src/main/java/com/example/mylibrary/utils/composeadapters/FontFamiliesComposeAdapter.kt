package com.example.mylibrary.utils.composeadapters

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.mylibrary.R
import com.example.mylibrary.tokens.base.FontFamilyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Define las **familias tipográficas atómicas** del Design System a partir de los
 * archivos localizados en `res/font/`.
 *
 * Este archivo constituye la **única fuente de verdad** para las fuentes físicas
 * utilizadas por el sistema, previo a su asignación semántica en:
 *
 *  - `fonts/FontFamilies` (tokens → tamaño, peso y estilo)
 *  - `atoms/LibraryTypographyTokens` (construcción semántica)
 *  - `semantics/CorporateTypography` (inyección final al tema)
 *
 * Las familias aquí definidas **NO deben ser accedidas directamente por el host**
 * ni por componentes. Su propósito es ser consumidas únicamente por los tokens
 * de tipografía.
 *
 * Uso:
 * ```
 * // Uso interno dentro de LibraryTypographyTokens
 * val fontFamily: FontFamily = FontFamilies.Mulish
 * ```
 *
 * @author Krause Kelsen
 * @since 10-31-2025
 * @version 1.5.4
 *
 * Familias:
 * @param Mulish      Fuente principal para textos del sistema (regular, medium, bold).
 * @param Davivienda  Fuente digital de Davivienda (regular/bold) (en mantenimiento).
 *
 * @see com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
 */


object FontFamiliesComposeAdapter {

    val Mulish = FontFamily(
        Font(
            resId = R.font.mulish_regular,
            weight = FontWeight.Companion.Normal,
            style = FontStyle.Companion.Normal
        ),
        Font(
            resId = R.font.mulish_medium,
            weight = FontWeight.Companion.Medium,
            style = FontStyle.Companion.Normal
        ),
        Font(
            resId = R.font.mulish_bold,
            weight = FontWeight.Companion.Bold,
            style = FontStyle.Companion.Normal
        ),
    )

    val Davivienda = FontFamily(
        Font(
            resId = R.font.davivienda_regular,
            weight = FontWeight.Companion.Normal,
            style = FontStyle.Companion.Normal
        ),
        Font(
            resId = R.font.davivienda_bold,
            weight = FontWeight.Companion.Bold,
            style = FontStyle.Companion.Normal
        ),
    )

    fun toCompose(token: FontFamilyToken): FontFamily =
        when (token) {
            FontFamilyToken.MULISH -> FontFamiliesComposeAdapter.Mulish
            FontFamilyToken.DAVIVIENDA -> FontFamiliesComposeAdapter.Davivienda
        }
}