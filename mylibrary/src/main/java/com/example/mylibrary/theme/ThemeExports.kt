package com.example.mylibrary.theme

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Variables para modos disponibles
 *
 * Uso:
 * ```
 * Configuración de estilos de tema por app hosting
 * ```
 *
 * @author Krause Kelsen
 * @since 10-27-2025
 * @version 1.5.3
 *
 * @param
 * @return
 * @throws
 * @see com.example.mylibrary.theme.LibraryThemeStyles
 */
object LibraryThemeStyles {
    val LIGHT = ThemeStyle.LIGHT
    val DARK = ThemeStyle.DARK
    val AUTO = ThemeStyle.AUTO
}



/**
 * no cambia la tipografia entre temas, sigue siendo la misma de davivienda siempre hasta el fin de los tiempos
 * aunque se debe implementar la opcion de variar al menos una fuente, si se cambia la fuente, diseño va buscar
 * que tengna los mismos pesos y tamaños para que si cambian de fuente no afecte 
 */