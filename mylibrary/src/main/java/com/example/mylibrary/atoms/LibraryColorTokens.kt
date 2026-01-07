package com.example.mylibrary.atoms

import com.example.mylibrary.tokens.base.ColorToken
import com.example.mylibrary.tokens.colors.DefaultColors

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LibraryColorTokens` define la paleta de colores semánticos de la librería
 * utilizando los mismos roles corporativos establecidos en Figma.
 * Proporciona una estructura tipada que separa claramente los colores por función:
 *
 *  - Roles de foreground (texto, íconos)
 *  - Roles de background / surface
 *  - Bordes y divisores
 *  - Estados (error, success, warning, info, new)
 *  - Roles de marca y soporte
 *
 * Además, sirve como puente entre:
 *
 *  - La semántica del Design System (LibraryColorTokens)
 *  - La semántica de Material Design 3 (via `defaultToMaterialColorScheme`)
 *
 * También agrupa en un `companion object` los temas predeterminados de la librería
 * (`DefaultLight`, `DefaultDark`), con su semántica en ambos modos.
 *
 * Uso:
 * ```
 * No debe ser accedida directamente por el host.
 * El acceso debe darse solo a través de:
 *    - LocalLibraryColorTokens (scope controlado)
 *
 *     val providedLibraryColorTokens = LocalLibraryColorTokens.current
 *     val resolvedTokens: ButtonTokens = when {
 *         // 1) Si el host pasó ButtonTokens personalizados
 *         buttonTokens != null -> buttonTokens
 *
 *         // 2) Si existe libraryTokens (pasado o en CompositionLocal)
 *         //    -> derivar desde la semántica corporativa
 *         providedLibraryColorTokens != null ->
 *             ButtonTokens.fromLibraryTokens(providedLibraryColorTokens)
 *
 *         // 3) Fallback: derivar desde MaterialTheme
 *         else -> ButtonTokens.fromMaterialTheme()
 *     }
 * ```
 *
 * Esta clase constituye la base de color para la construcción de:
 *
 *  - `atoms/LibraryColorTokens`  (tokens internos por componente)
 *  - `semantics/CorporateColorScheme` (puente semántico corporativo → DS)
 *  - `adapter/defaultToMaterialColorScheme` (convertidor MD3)
 *
 * @author Krause Kelsen
 * @since 10-24-2025
 * @version 1.5.3
 *
 * @param DefaultLight Semántica corporativa para modo light
 * @param DefaultDark Semántica corporativa para modo dark
 * @param defaultToMaterialColorScheme Adaptador hacia MD3 (ColorScheme)
 *
 * @see com.example.mylibrary.atoms.LibraryColorTokens
 */

data class LibraryColorTokens(
    // Text (foreground)
    val fgDefault: ColorToken,
    val fgMuted: ColorToken,
    val fgSubtle: ColorToken,
    val fgOnPrimary: ColorToken,
    val fgOnInverse: ColorToken,
    val fgError: ColorToken,

    // Background/Surface
    val bgBase: ColorToken,
    val bgSubtle: ColorToken,
    val bgMuted: ColorToken,

    val surfaceDefault: ColorToken,
    val surfaceRaised: ColorToken,
    val surfaceSubtle: ColorToken,

    // Borders/Div
    val borderDefault: ColorToken,
    val borderSubtle: ColorToken,
    val borderEmphasis: ColorToken,
    val borderFocus: ColorToken,

    // Brand/Primary
    val brandPrimary: ColorToken,
    val brandPrimaryHover: ColorToken,

    // Support/Actions
    val supportDefault: ColorToken,
    val supportHover: ColorToken,
    val supportFocus: ColorToken,

    // Links
    val linkDefault: ColorToken,
    val linkHover: ColorToken,


    // Error
    val errorFillDefault: ColorToken,
    val errorFillSoft: ColorToken,
    val errorBorder: ColorToken,
    val errorIcon: ColorToken,
    val errorText: ColorToken,


    // Success states
    val successFillDefault: ColorToken,
    val successFillSoft: ColorToken,
    val successBorder: ColorToken,
    val successIcon: ColorToken,
    val successText: ColorToken,

    // Warning states
    val warningFillDefault: ColorToken,
    val warningFillSoft: ColorToken,
    val warningBorder: ColorToken,
    val warningIcon: ColorToken,
    val warningText: ColorToken,

    // Info states
    val infoFillDefault: ColorToken,
    val infoFillSoft: ColorToken,
    val infoBorder: ColorToken,
    val infoIcon: ColorToken,
    val infoText: ColorToken,

    // New states
    val newFillDefault: ColorToken,
    val newFillSoft: ColorToken,
    val newBorder: ColorToken,
    val newIcon: ColorToken,
    val newText: ColorToken


) {
    companion object {
        // Default tokens de la semántica de Figma (tema: light)
        val DefaultLight = LibraryColorTokens(
            // Foregrounds
            fgDefault = DefaultColors.Ink.c900,
            fgMuted = DefaultColors.Ink.c500,
            fgSubtle = DefaultColors.Ink.c400,
            fgOnPrimary = DefaultColors.Ink.c900,
            fgOnInverse = DefaultColors.Core.white,
            fgError = DefaultColors.Error.c600,

            // Backgrounds
            bgBase = DefaultColors.Core.white,
            bgSubtle = DefaultColors.Ink.c100,
            bgMuted = DefaultColors.Ink.c200,

            // Surfaces
            surfaceDefault = DefaultColors.Core.white,
            surfaceRaised = DefaultColors.Core.white,
            surfaceSubtle = DefaultColors.Ink.c100,

            // Borders
            borderDefault = DefaultColors.Ink.c300,
            borderSubtle = DefaultColors.Ink.c200,
            borderEmphasis = DefaultColors.Ink.c400,
            borderFocus = DefaultColors.Support.Blue.c600,

            // Actions
            brandPrimary = DefaultColors.Brand.Red.c500,
            brandPrimaryHover = DefaultColors.Brand.Red.c600,

            // Support
            supportDefault = DefaultColors.Support.Blue.c500,
            supportHover = DefaultColors.Support.Blue.c600,
            supportFocus = DefaultColors.Support.Blue.c700,

            // Links
            linkDefault = DefaultColors.Support.Blue.c600,
            linkHover = DefaultColors.Support.Blue.c500,

            // Error States
            errorFillDefault = DefaultColors.Error.c600,
            errorFillSoft = DefaultColors.Error.c100,
            errorBorder = DefaultColors.Error.c600,
            errorIcon = DefaultColors.Error.c600,
            errorText = DefaultColors.Core.white,

            // Success states
            successFillDefault = DefaultColors.Green.c500,
            successFillSoft = DefaultColors.Green.c300,
            successBorder = DefaultColors.Green.c500,
            successIcon = DefaultColors.Green.c500,
            successText = DefaultColors.Core.white,

            // Warning states
            warningFillDefault = DefaultColors.Yellow.c500,
            warningFillSoft = DefaultColors.Yellow.c300,
            warningBorder = DefaultColors.Yellow.c500,
            warningIcon = DefaultColors.Yellow.c500,
            warningText = DefaultColors.Ink.c900,

            // Info states
            infoFillDefault = DefaultColors.Support.Blue.c500,
            infoFillSoft = DefaultColors.Support.Blue.c300,
            infoBorder = DefaultColors.Support.Blue.c500,
            infoIcon = DefaultColors.Support.Blue.c500,
            infoText = DefaultColors.Core.white,

            // New states
            newFillDefault = DefaultColors.Support.Blue.c500,
            newFillSoft = DefaultColors.Support.Blue.c300,
            newBorder = DefaultColors.Support.Blue.c500,
            newIcon = DefaultColors.Support.Blue.c500,
            newText = DefaultColors.Core.white

        )
        // Default tokens de la semántica de Figma (tema: dark)
        val DefaultDark = LibraryColorTokens(
            // Foregrounds
            fgDefault = DefaultColors.Core.white,
            fgMuted = DefaultColors.Ink.c400,
            fgSubtle = DefaultColors.Ink.c300,
            fgOnPrimary = DefaultColors.Core.white,
            fgOnInverse = DefaultColors.Core.white,
            fgError = DefaultColors.Error.c300,

            // Backgrounds
            bgBase = DefaultColors.DarkMode.Bg.cBase,
            bgSubtle = DefaultColors.DarkMode.Surface.c100,
            bgMuted = DefaultColors.DarkMode.Surface.c200,

            // Surfaces
            surfaceDefault = DefaultColors.DarkMode.Border.cDefault,
            surfaceRaised = DefaultColors.DarkMode.Surface.c100,
            surfaceSubtle = DefaultColors.DarkMode.Border.cSubtle,

            // Borders
            borderDefault = DefaultColors.DarkMode.Border.cDefault,
            borderSubtle = DefaultColors.DarkMode.Border.cSubtle,
            borderEmphasis = DefaultColors.Support.Blue.c700,
            borderFocus = DefaultColors.Support.Blue.c600,

            // Actions
            brandPrimary = DefaultColors.Brand.Red.c500,
            brandPrimaryHover = DefaultColors.Brand.Red.c600,

            // Support
            supportDefault = DefaultColors.Support.Blue.c500,
            supportHover = DefaultColors.Support.Blue.c600,
            supportFocus = DefaultColors.Support.Blue.c700,

            // Links
            linkDefault = DefaultColors.Support.Blue.c300,
            linkHover = DefaultColors.Support.Blue.c500,

            // Error States
            errorFillDefault = DefaultColors.Error.c600,
            errorFillSoft = DefaultColors.Error.c300,
            errorBorder = DefaultColors.Error.c600,
            errorIcon = DefaultColors.Error.c600,
            errorText = DefaultColors.Core.white,

            // Success states
            successFillDefault = DefaultColors.Green.c500,
            successFillSoft = DefaultColors.Green.c300,
            successBorder = DefaultColors.Green.c500,
            successIcon = DefaultColors.Green.c500,
            successText = DefaultColors.Core.white,

            // Warning states
            warningFillDefault = DefaultColors.Yellow.c500,
            warningFillSoft = DefaultColors.Yellow.c300,
            warningBorder = DefaultColors.Yellow.c500,
            warningIcon = DefaultColors.Yellow.c500,
            warningText = DefaultColors.Ink.c900,

            // Info states
            infoFillDefault = DefaultColors.Support.Blue.c500,
            infoFillSoft = DefaultColors.Support.Blue.c300,
            infoBorder = DefaultColors.Support.Blue.c500,
            infoIcon = DefaultColors.Support.Blue.c500,
            infoText = DefaultColors.Core.white,

            // New states
            newFillDefault = DefaultColors.Support.Blue.c500,
            newFillSoft = DefaultColors.Support.Blue.c300,
            newBorder = DefaultColors.Support.Blue.c500,
            newIcon = DefaultColors.Support.Blue.c500,
            newText = DefaultColors.Core.white

        )
        //Otros temas con sus modes light/dark

    }

}