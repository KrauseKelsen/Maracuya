package cruxui.android.lib.core.components.buttons.tokens

import cruxui.android.lib.atoms.icons.LibraryGeneralIconsTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonLoadingTokens selecciona el único icono permitido en loading.
 *
 * Justificación: La carga no es un slot del host; reemplaza visualmente texto y ambos iconos
 * laterales.
 *
 * Se comunica con: LibraryGeneralIconsTokens contiene el asset; behavior aporta rotación y
 * tokensrender aporta el tinte.
 *
 * Uso:
 * ```kotlin
 * val icon = ButtonLoadingTokens.Icon
 * val resourceName = icon.resourceName
 * val sizeDp = icon.iconSize
 * check(resourceName == "ic_loading")
 * ```
 *
 * Objetivos:
 * - Mantener el indicador idéntico en XML y Compose.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal object ButtonLoadingTokens {
    /** Asset canónico del catálogo; las personalizaciones de iconStart/iconEnd no lo sustituyen. */
    val Icon = LibraryGeneralIconsTokens.general.loading
}
