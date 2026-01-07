package com.example.mylibrary.semantics

import com.example.mylibrary.atoms.LibraryTypographyTokens
import com.example.mylibrary.tokens.base.TypographyToken

/**
 * Tipografía semántica corporativa.
 *
 * `CorporateTypography` define la capa semántica tipográfica de la marca,
 * construida directamente sobre los tokens tipográficos base del Design System
 * (`LibraryTypographyTokens`).
 *
 * Función del nivel semántico:
 * - Actúa como puente entre la semántica corporativa y los componentes.
 * - Ofrece un conjunto estable de estilos tipográficos nombrados según
 *   su intención (headline, subtitle, body, caption, legal, etc.).
 * - Permite a los componentes depender de roles semánticos en lugar de
 *   valores tipográficos concretos, facilitando mantenibilidad y escalabilidad.
 *
 * Importancia:
 * - Aísla a los componentes de cambios en pesos, alturas de línea, tamaños
 *   o familias tipográficas.
 * - Establece un contrato corporativo claro y estable.
 * - Centraliza la definición de estilos que representan la identidad visual
 *   de la marca.
 *
 * Uso:
 * ```
 * Esta clase no debe ser instanciada manualmente por el host.
 * Su instancia activa proviene del sistema de theming a través de:
 *
 *     val typography = LocalLibraryTypography.current
 *
 * Todos los componentes deben depender exclusivamente de este punto semántico,
 * nunca de los tokens directamente.
 * ```
 *
 * Relación con otras capas:
 * - **LibraryTypographyTokens** → valores tipográficos base del DS.
 * - **CorporateTypography (esta clase)** → semántica corporativa.
 * - **Componentes** → consumen los roles semánticos.
 *
 * ###
 * ### Headings
 * @param headline48 size=48.sp,  lineHeightRatio=1.1f, tracking=0.sp, weight=Regular
 * @param headline36 size=36.sp,  lineHeightRatio=1.1f, tracking=0.sp, weight=Regular
 * @param headline32 size=32.sp,  lineHeightRatio=1.15f, tracking=0.sp, weight=Regular
 * @param headline28 size=28.sp,  lineHeightRatio=1.15f, tracking=0.sp, weight=Regular
 * @param headline24 size=24.sp,  lineHeightRatio=1.15f, tracking=0.sp, weight=Regular
 * @param headline21 size=21.sp,  lineHeightRatio=1.15f, tracking=0.sp, weight=Regular
 * @param headline18 size=18.sp,  lineHeightRatio=1.15f, tracking=0.sp, weight=Regular
 * @param headline16 size=16.sp,  lineHeightRatio=1.15f, tracking=0.sp, weight=Regular
 *
 * ### Subtítulos
 * @param subtitle1 size=18.sp, lineHeightRatio=1.2f, tracking=0.sp, weight=Regular
 * @param subtitle2 size=16.sp, lineHeightRatio=1.2f, tracking=0.sp, weight=Regular
 * @param subtitle3 size=14.sp, lineHeightRatio=1.2f, tracking=0.sp, weight=Regular
 *
 * ### Texto de cuerpo
 * @param body1        size=18.sp, lineHeightRatio=1.2f, tracking=0.sp, weight=Regular
 * @param body2        size=16.sp, lineHeightRatio=1.2f, tracking=0.sp, weight=Regular
 * @param introduction size=20.sp, lineHeightRatio=1.2f, tracking=0.sp, weight=Regular
 *
 * ### Etiquetas y legales
 * @param caption size=14.sp, lineHeightRatio=1.2f, tracking=0.sp, weight=Regular
 * @param legal   size=12.sp, lineHeightRatio=1.2f, tracking=0.sp, weight=Regular
 *
 * ### Botones
 * @param buttons size=16.sp, lineHeightRatio=1.15f, tracking=0.sp, weight=Headline
 *
 * ### Label
 * @param label size=14.sp, lineHeightRatio=1.5f, tracking=0.sp, weight=Headline
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 * @see com.example.mylibrary.semantics.CorporateTypography
 */

//TODO La semantica no esta bien definida con figma hay que definirla bien en los comentarios tmb
class CorporateTypography {


    val headline48: TypographyToken = LibraryTypographyTokens.headline48
    val headline36: TypographyToken = LibraryTypographyTokens.headline36
    val headline32: TypographyToken = LibraryTypographyTokens.headline32
    val headline28: TypographyToken = LibraryTypographyTokens.headline28
    val headline24: TypographyToken = LibraryTypographyTokens.headline24
    val headline21: TypographyToken = LibraryTypographyTokens.headline21
    val headline18: TypographyToken = LibraryTypographyTokens.headline18
    val headline16: TypographyToken = LibraryTypographyTokens.headline16

    val subtitle1: TypographyToken = LibraryTypographyTokens.subtitle1
    val subtitle2: TypographyToken = LibraryTypographyTokens.subtitle2
    val subtitle3: TypographyToken = LibraryTypographyTokens.subtitle3

    val body1: TypographyToken = LibraryTypographyTokens.body1
    val body2: TypographyToken = LibraryTypographyTokens.body2
    val introduction: TypographyToken = LibraryTypographyTokens.introduction

    val caption: TypographyToken = LibraryTypographyTokens.caption
    val legal: TypographyToken = LibraryTypographyTokens.legal
    val buttons: TypographyToken = LibraryTypographyTokens.buttons

    //Provisional para LabelMrcy - Parecido al subtitle3
    val label: TypographyToken = LibraryTypographyTokens.label

    //Provisional para LabelMrcy - Parecido al subtitle3
    val placeholder: TypographyToken = LibraryTypographyTokens.placeholder

    //Provisional para LabelMrcy - Parecido al subtitle3
    val bottomText: TypographyToken = LibraryTypographyTokens.bottomText
}
