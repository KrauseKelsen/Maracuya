package cruxui.android.lib.atoms

import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.colors.DefaultColors

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryColorTokens define todos los roles de color del Design System y sus valores por modo.
 *
 * Justificación:
 * La propia data class es la colección configurable del tema; no necesita otro contenedor Corporate.
 * LibraryThemeSet, LocalLibraryColorTokens, los adaptadores y token-exporter consumen estas propiedades.
 * El build genera consultas, roles y recursos desde sus getters ColorToken, sin una lista duplicada.
 *
 * Uso:
 * API pública del host. En Compose usar LibraryTheme.current.colors.brandPrimary dentro de MyLibraryTheme.
 * En un register usar TextViewTokensOverride(textColor = library.colors.brandPrimary).
 * Para personalizar una marca: base.copy(lightTokens = base.lightTokens.copy(brandPrimary = nuevoColor)).
 * En XML declarar android:theme="@style/ThemeOverlay.Maracuya" en el ámbito y
 * android:textColor="?attr/libraryColorBrandPrimary" en el componente. El atributo sigue el modo del overlay.
 * También se exponen LibraryXmlReferences.colors.brandPrimary y LibraryColorRole.BrandPrimary;
 * para consultar por rol, importar theme.catalog.get y usar library.colors[rol].
 * Para ampliar el DS agregar un val ColorToken y definirlo en DefaultLight, DefaultDark y las demás
 * paletas que se construyan explícitamente. Recompilar genera el rol, attr, color y enlace de cada tema.
 * No editar archivos build/generated ni resolutores de wrappers. Los copy realizados en ejecución
 * se aplican mediante el proveedor/receta; no modifican los recursos XML compilados.
 *
 * Objetivos:
 * - Compartir una paleta entre Compose, XML y recetas.
 * - Incorporar colores nuevos automáticamente, conservando las diferencias entre modos.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */

data class LibraryColorTokens(
    /** Color transparente utilizado para fondos invisibles o estados sin color. */
    val transparent: ColorToken,

    // ── Foreground (Texto e Íconos) ──────────────────────────────────────────

    /** Color principal de texto e íconos sobre fondos claros. */
    val fgDefault: ColorToken,
    /** Color de texto secundario/atenuado (placeholders, labels secundarios). */
    val fgMuted: ColorToken,
    /** Color de texto terciario/sutil (hints, metadata de baja prioridad). */
    val fgSubtle: ColorToken,
    /** Color de texto sobre superficies primarias (brand). */
    val fgOnPrimary: ColorToken,
    /** Color de texto sobre superficies inversas (fondos oscuros en light mode y viceversa). */
    val fgOnInverse: ColorToken,
    /** Color auxiliar para contentColor en botones inversos (no definido en Figma). */
    val fgOnInverseAux: ColorToken,
    /** Color de texto para mensajes de error. */
    val fgError: ColorToken,

    // ── Background / Surface ─────────────────────────────────────────────────

    /** Color base del fondo de la aplicación. */
    val bgBase: ColorToken,
    /** Color de fondo sutil para secciones diferenciadas. */
    val bgSubtle: ColorToken,
    /** Color de fondo atenuado para áreas de menor jerarquía. */
    val bgMuted: ColorToken,

    /** Color de superficie por defecto para tarjetas y contenedores. */
    val surfaceDefault: ColorToken,
    /** Color de superficie elevada (cards con sombra, modales). */
    val surfaceRaised: ColorToken,
    /** Color de superficie sutil (inputs deshabilitados, chips). */
    val surfaceSubtle: ColorToken,
    //nuevo
    val surfaceCardBase: ColorToken,

    // ── Borders / Dividers ───────────────────────────────────────────────────

    /** Color de borde por defecto para inputs y contenedores. */
    val borderDefault: ColorToken,
    /** Color de borde sutil para separadores y divisores ligeros. */
    val borderSubtle: ColorToken,
    /** Color de borde con énfasis para estados activos o seleccionados. */
    val borderEmphasis: ColorToken,
    /** Color de borde para el estado de foco (accesibilidad). */
    val borderFocus: ColorToken,

    // ── Brand / Primary ──────────────────────────────────────────────────────

    /** Color primario de marca (CTA principal, acciones destacadas). */
    val brandPrimary: ColorToken,
    /** Color primario de marca en estado hover/pressed. */
    val brandPrimaryHover: ColorToken,

    // ── Support / Actions ────────────────────────────────────────────────────

    /** Color de soporte por defecto para acciones secundarias. */
    val supportDefault: ColorToken,
    /** Color de soporte en estado hover. */
    val supportHover: ColorToken,
    /** Color de soporte en estado foco. */
    val supportFocus: ColorToken,

    // ── Links ────────────────────────────────────────────────────────────────

    /** Color de enlace por defecto. */
    val linkDefault: ColorToken,
    /** Color de enlace en estado hover. */
    val linkHover: ColorToken,

    // ── Error States ─────────────────────────────────────────────────────────

    /** Color de relleno principal para estados de error. */
    val errorFillDefault: ColorToken,
    /** Color de relleno suave para fondos de error (banners, badges). */
    val errorFillSoft: ColorToken,
    /** Color de borde para indicadores de error. */
    val errorBorder: ColorToken,
    /** Color de ícono para estados de error. */
    val errorIcon: ColorToken,
    /** Color de texto sobre superficies de error. */
    val errorText: ColorToken,

    // ── Success States ───────────────────────────────────────────────────────

    /** Color de relleno principal para estados de éxito. */
    val successFillDefault: ColorToken,
    /** Color de relleno suave para fondos de éxito. */
    val successFillSoft: ColorToken,
    /** Color de borde para indicadores de éxito. */
    val successBorder: ColorToken,
    /** Color de ícono para estados de éxito. */
    val successIcon: ColorToken,
    /** Color de texto sobre superficies de éxito. */
    val successText: ColorToken,

    // ── Warning States ───────────────────────────────────────────────────────

    /** Color de relleno principal para estados de advertencia. */
    val warningFillDefault: ColorToken,
    /** Color de relleno suave para fondos de advertencia. */
    val warningFillSoft: ColorToken,
    /** Color de borde para indicadores de advertencia. */
    val warningBorder: ColorToken,
    /** Color de ícono para estados de advertencia. */
    val warningIcon: ColorToken,
    /** Color de texto sobre superficies de advertencia. */
    val warningText: ColorToken,

    // ── Info States ──────────────────────────────────────────────────────────

    /** Color de relleno principal para estados informativos. */
    val infoFillDefault: ColorToken,
    /** Color de relleno suave para fondos informativos. */
    val infoFillSoft: ColorToken,
    /** Color de borde para indicadores informativos. */
    val infoBorder: ColorToken,
    /** Color de ícono para estados informativos. */
    val infoIcon: ColorToken,
    /** Color de texto sobre superficies informativas. */
    val infoText: ColorToken,

    // ── New States ───────────────────────────────────────────────────────────

    /** Color de relleno principal para indicadores de novedad. */
    val newFillDefault: ColorToken,
    /** Color de relleno suave para fondos de novedad. */
    val newFillSoft: ColorToken,
    /** Color de borde para indicadores de novedad. */
    val newBorder: ColorToken,
    /** Color de ícono para estados de novedad. */
    val newIcon: ColorToken,
    /** Color de texto sobre superficies de novedad. */
    val newText: ColorToken


) {
    companion object {
        /** Paleta base del modo claro; usa los valores primitivos de DefaultColors. */
        val DefaultLight = LibraryColorTokens(
            transparent = DefaultColors.Extra.transparent,
            // Foregrounds
            fgDefault = DefaultColors.Ink.c900,
            fgMuted = DefaultColors.Ink.c500,
            fgSubtle = DefaultColors.Ink.c400,
            fgOnPrimary = DefaultColors.Ink.c900,
            fgOnInverse = DefaultColors.Core.white,
            fgOnInverseAux = DefaultColors.Brand.Red.c500,
            fgError = DefaultColors.Error.c600,

            // Backgrounds
            bgBase = DefaultColors.Core.white,
            bgSubtle = DefaultColors.Ink.c100,
            bgMuted = DefaultColors.Ink.c200,

            // Surfaces
            surfaceDefault = DefaultColors.Core.white,
            surfaceRaised = DefaultColors.Core.white,
            surfaceSubtle = DefaultColors.Ink.c100,
            surfaceCardBase = DefaultColors.Core.white, //nuevo

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
            newFillDefault = DefaultColors.Purple.c500,
            //newFillDefault = DefaultColors.Support.Blue.c500,
            newFillSoft = DefaultColors.Purple.c300,
            //newFillSoft = DefaultColors.Support.Blue.c300,
            newBorder = DefaultColors.Purple.c500,
            //newBorder = DefaultColors.Support.Blue.c500,
            newIcon = DefaultColors.Purple.c500,
            //newIcon = DefaultColors.Support.Blue.c500,
            newText = DefaultColors.Core.white

        )
        /** Paleta base del modo oscuro; conserva los mismos roles con valores propios. */
        val DefaultDark = LibraryColorTokens(
            transparent = DefaultColors.Extra.transparent,
            // Foregrounds
            fgDefault = DefaultColors.Core.white,
            fgMuted = DefaultColors.Ink.c300,
            //fgMuted = DefaultColors.Ink.c400,
            fgSubtle = DefaultColors.Ink.c200,
            //fgSubtle = DefaultColors.Ink.c300,
            fgOnPrimary = DefaultColors.Core.white,
            fgOnInverse = DefaultColors.Core.white,
            fgOnInverseAux = DefaultColors.Core.white,
            fgError = DefaultColors.Error.c300,

            // Backgrounds
            bgBase = DefaultColors.DarkMode.Bg.cBase,
            bgSubtle = DefaultColors.DarkMode.Surface.c100,
            bgMuted = DefaultColors.DarkMode.Surface.c200,

            // Surfaces
            surfaceDefault = DefaultColors.DarkMode.Border.cDefault,
            surfaceRaised = DefaultColors.DarkMode.Surface.c100,
            surfaceSubtle = DefaultColors.DarkMode.Border.cSubtle,
            surfaceCardBase = DefaultColors.DarkMode.Border.cCardBase, //nuevo

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
            errorBorder = DefaultColors.DarkMode.Error.cRojo,
            //errorBorder = DefaultColors.Error.c600,
            errorIcon = DefaultColors.Error.c300,
            //errorIcon = DefaultColors.Error.c600,
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
            newFillDefault = DefaultColors.Purple.c500,
            //newFillDefault = DefaultColors.Support.Blue.c500,
            newFillSoft = DefaultColors.Purple.c300,
            //newFillSoft = DefaultColors.Support.Blue.c300,
            newBorder = DefaultColors.Purple.c500,
            //newBorder = DefaultColors.Support.Blue.c500,
            newIcon = DefaultColors.Purple.c500,
            //newIcon = DefaultColors.Support.Blue.c500,
            newText = DefaultColors.Core.white
        )
        //Otros temas con sus modes light/dark
    }

}