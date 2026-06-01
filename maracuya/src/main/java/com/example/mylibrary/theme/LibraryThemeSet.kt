package com.example.mylibrary.theme

import com.example.mylibrary.atoms.LibraryColorTokens
import com.example.mylibrary.atoms.LibraryIconTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LibraryThemeSet` define la estructura fundamental de un tema dentro del Design System.
 * Representa un conjunto temático completo (por ejemplo: *Default*, *Davivienda*, *Navidad*),
 * donde cada tema está asociado a una identidad visual propia.
 *
 * Su propósito es agrupar las versiones **Light** y **Dark** del mismo tema, permitiendo
 * que el motor del DS (principalmente `MyLibraryTheme`) pueda resolver cuál conjunto de
 * tokens debe utilizar dependiendo del estilo de visualización definido por el host o
 * por el sistema operativo (AUTO, LIGHT, DARK).
 *
 * Función:
 * - Centralizar los tokens semánticos de color de cada tema.
 * - Asegurar simetría entre los modos *light* y *dark*.
 * - Facilitar la exportación y registro de temas dentro del módulo `ThemeExports`.
 *
 * Relación con otras clases:
 * - `MyLibraryTheme` selecciona entre `lightTokens` o `darkTokens` según el modo.
 * - `ThemeExports` utiliza esta clase para registrar los temas disponibles para el host.
 * - `LibraryColorTokens` actúa como semántica cromática de cada tema.
 *
 * Uso:
 * ```
 * // Ejemplo desde ThemeExports
 * val MaracuyaTheme = LibraryThemeSet(
 *     name = "Maracuya",
 *     lightTokens = LibraryColorTokens.DefaultLight,
 *     darkTokens = LibraryColorTokens.DefaultDark
 * )
 *
 * // Dentro de MyLibraryTheme
 * val tokens = if (isDark) theme.darkTokens else theme.lightTokens
 * ```
 *
 * No debe ser instanciada dinámicamente por el host.
 * Debe gestionarse únicamente desde `ThemeExports`, donde se declaran los temas oficiales
 * soportados por la biblioteca.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.3
 *
 * @param name Nombre del tema (ej: “default”, “maracuya”, “forest”). Usado también para
 *             resolver la familia tipográfica mediante `FontResources`.
 * @param lightTokens Conjunto de tokens semánticos para modo claro.
 * @param darkTokens Conjunto de tokens semánticos para modo oscuro.
 *
 * @see com.example.mylibrary.theme.LibraryThemeSet
 */
data class LibraryThemeSet(
    val name: String,
    val lightTokens: LibraryColorTokens,
    val darkTokens: LibraryColorTokens,
    val iconAtoms: LibraryIconTokens
)