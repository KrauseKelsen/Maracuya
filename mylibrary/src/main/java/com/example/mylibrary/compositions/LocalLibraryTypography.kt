package com.example.mylibrary.compositions

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.mylibrary.semantics.CorporateTypography

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `LocalLibraryTypography` es un CompositionLocal que expone la tipografía corporativa
 * activa del tema. Funciona de manera similar a `LocalLibraryColorTokens`, pero en este
 * caso su valor **no es nullable**, ya que siempre debe existir una tipografía válida
 * para que los componentes puedan renderizar texto sin degradación ni inconsistencias.
 *
 * El valor por defecto asigna una instancia de `CorporateTypography()`, la cual NO debe
 * invocar funciones @Composable, cumpliendo así con las reglas de seguridad de
 * `staticCompositionLocalOf`.
 *
 * Importancia:
 * - Evita crashes al asegurar que siempre existe un objeto tipográfico base.
 * - Garantiza consistencia incluso si un host utiliza un componente fuera del scope
 *   de `MyLibraryTheme`.
 * - Permite que resolvers como:
 *      - `LibraryTypographyTokens`
 *      - `SemanticsTypography`
 *      - `CorporateTypography`
 *   operen de forma segura sin validaciones extra.
 *
 * Uso:
 * ```
 * No debe ser accedida directamente por el host.
 *
 * El acceso seguro debe darse únicamente a través del sistema semántico,
 * por ejemplo:
 *
 *     val typography = LocalLibraryTypography.current
 *
 * Los componentes NO deben leer esta fuente manualmente, sino a través de tokens,
 * derivaciones o resolvers propios del Design System.
 * ```
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.3
 * @see com.example.mylibrary.compositions.LocalLibraryTypography
 */

//TODO Determinar si debe consumirse desde el host cuando se hace un customTokensLayer
val LocalLibraryTypography = staticCompositionLocalOf {
    CorporateTypography() // non-composable safe default
}