package com.example.mylibrary.ui.components.textfields

import androidx.compose.runtime.Composable
import com.example.mylibrary.atoms.LibraryColorTokens
import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.base.FontFamilyToken
import com.example.mylibrary.tokens.base.IconToken
import com.example.mylibrary.tokens.base.TypographyToken

//Component token layer del TextField por defecto

data class TextFieldTokens (
    //Texto inferior
    val fontFamilyToken: FontFamilyToken, // Fuente de letra
    val bottomTextErrorColor: ColorToken, // Rojo para texto error
    val bottomTextTypography: TypographyToken, // Tipografia del texto inferior
    val bottomTextColor: ColorToken, // Color del texto inferior
    val bottomTextIcon: IconToken, // Icono del texto inferior

)
