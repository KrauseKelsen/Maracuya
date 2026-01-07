package com.example.mylibrary.tokens.base

/**
 * Representa el asset atómico de un icono en el DS.
 *
 * Usamos dos variantes por icono:
 *  - filledResId  -> recurso vector drawable con forma rellena
 *
 * Mantener ambos permite componer modos visuales simples (borde+relleno).
 */
data class IconToken(
    val name: String,      // identificador lógico legible (ej: "check")
    val filledResId: Int,  // R.drawable.ic_check_filled
    //val outlineResId: Int  // R.drawable.ic_check_outline
)
