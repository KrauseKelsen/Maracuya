package com.example.mylibrary.theme

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Shapes
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mylibrary.compositions.LocalFontFamily
import com.example.mylibrary.compositions.LocalLibraryColorTokens
import com.example.mylibrary.compositions.LocalLibraryIcons
import com.example.mylibrary.compositions.LocalLibraryTypography
import com.example.mylibrary.semantics.CorporateIcons
import com.example.mylibrary.semantics.CorporateTypography
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter
import com.example.mylibrary.utils.materialadapters.ColorMaterialAdapter
import com.example.mylibrary.utils.LibraryThemeManager
import com.example.mylibrary.utils.composeadapters.FontFamiliesComposeAdapter
import com.example.mylibrary.utils.materialadapters.TypographyMaterialAdapter

/**
 * Define los estilos del tema: Light, Dark o Auto (según el sistema).
 */
enum class ThemeStyle { LIGHT, DARK, AUTO }

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `MyLibraryTheme` es el entrypoint de theming del Design System. Envuelve
 * contenido Compose con la semántica de marca (tokens) y opcionalmente con
 * un `MaterialTheme` derivado desde esos tokens.
 *
 * Qué hace:
 * - Resuelve el modo (light / dark / auto) a partir de `style` o del sistema.
 * - Selecciona los `LibraryColorTokens` apropiados (lightTokens o darkTokens)
 *   del `LibraryThemeSet` provisto.
 * - Resuelve la `FontFamily` usando `FontResources.forTheme(theme.name)` y la
 *   provee en `LocalFontFamily`.
 * - Proporciona los `LibraryColorTokens` mediante `LocalLibraryColorTokens`,
 *   asegurando que todos los componentes del DS puedan acceder a la semántica
 *   cromática dentro de la jerarquía Compose.
 * - Ejecuta un `SideEffect` que sincroniza las barras del sistema (`LibraryThemeManager`).
 * - Si `useMaterial == true`, construye y provee un `MaterialTheme` utilizando
 *   `tokens.defaultToMaterialColorScheme(isDark)` (recordado por `remember`) y
 *   wrappers `Typography()` y `Shapes()` por defecto.
 * - Si `useMaterial == false`, no toca `MaterialTheme` y solamente provee los
 *   tokens de la librería; esto permite usar la librería sin acoplarse a Material.
 *
 * Uso (para el host):
 * ```
 * // El host SÍ debe usar esta función para aplicar el tema del DS alrededor de vistas/activities.
 * MyLibraryTheme(
 *     theme = LibraryThemes.Maracuya,
 *     style = ThemeStyle.AUTO,
 *     useMaterial = true // -> si se quiere el puente a Material3 (ColorScheme)
 * ) {
 *     // Aquí va el contenido que necesita el tema
 *     Button(
 *         onClick = {},
 *         colors = ButtonDefaults.buttonColors(
 *             containerColor = MaterialTheme.colorScheme.secondary // disponible solo si useMaterial==true
 *         )
 *     ) {
 *         Text("MD3 de Maracuya")
 *     }
 * }
 * ```
 *
 * Notas importantes y recomendaciones:
 * - `LocalLibraryColorTokens` y `LocalFontFamily` son provistos dentro de esta función:
 *   dentro del `content` su lectura será segura (no-null para tokens provistos).
 *   Fuera de este scope `LocalLibraryColorTokens.current` puede ser `null` (lectura segura
 *   requerida).
 * - `useMaterial = true` crea un puente semántico hacia Material3: los componentes
 *   que dependan de `MaterialTheme.colorScheme` verán colores derivados desde los
 *   `LibraryColorTokens`. Usar este flag solo cuando el host quiera exponer MD3.
 * - `remember(tokens, isDark)` asegura que la conversión a `ColorScheme` solo se
 *   realice cuando cambien los tokens o el modo; evita recomputaciones innecesarias.
 * - `SideEffect` se utiliza para sincronizar barras del sistema; no ejecutar
 *   lógica pesada aquí. `LibraryThemeManager.syncSystemBars` debe ser idempotente.
 * - La `FontFamily` se resuelve mediante `FontResources.forTheme(theme.name)`;
 *   si necesitás soportar más marcas, agregalas en `FontResources`.
 * - Si el host necesita acceder a tokens concretos (por ejemplo para crear
 *   componentes fuera del scope), debe:
 *     1) pasar `LibraryThemeSet` correcto a `MyLibraryTheme`, o
 *     2) consumir `LocalLibraryColorTokens`/`LocalFontFamily` dentro del mismo scope.
 *
 * Efectos visibles:
 * - Se aplica un `Box` con `Modifier.background(bgColor)` que garantiza que el
 *   fondo de la jerarquía use `bgBase` del token activo.
 * - Si `useMaterial` es true, MaterialTheme queda inyectado y componentes que
 *   dependan de MD3 funcionarán con los colores derivados.
 *
 * @param theme      `LibraryThemeSet` que contiene `name`, `lightTokens` y `darkTokens`.
 * @param style      `ThemeStyle` (LIGHT | DARK | AUTO) para resolver `isDark`.
 * @param useMaterial Si true, inyecta también `MaterialTheme` con `ColorScheme` derivado.
 * @param content    Lambda composable que será envuelta por el tema.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @see com.example.mylibrary.theme.MyLibraryTheme
 */
@Composable
fun MyLibraryTheme(
    theme: LibraryThemeSet,
    style: ThemeStyle = ThemeStyle.AUTO,
    useMaterial: Boolean = false,
    content: @Composable () -> Unit
) {
    val isDark = when (style) {
        ThemeStyle.DARK -> true
        ThemeStyle.LIGHT -> false
        ThemeStyle.AUTO -> isSystemInDarkTheme()
    }

    val tokens = if (isDark) theme.darkTokens else theme.lightTokens
    // Determina la familia de fuente según el tema
    val fontFamilyToken = FontResources.forTheme(theme.name)
    val composeFontFamily =
        FontFamiliesComposeAdapter.toCompose(fontFamilyToken)

    val context = LocalContext.current
    val bgColor = ColorComposeAdapter.toComposeColor(if (isDark) theme.darkTokens.bgBase else theme.lightTokens.bgBase)

    // Crear tipografía semántica (no Compose)
    val semanticTypography = remember { CorporateTypography() }

    // dentro de MyLibraryTheme, donde ya tienes `theme: LibraryThemeSet` y resolved tokens:
    val providedIcons = theme.iconAtoms
    val semanticIcons = CorporateIcons.fromAtoms(providedIcons) // deriva semántica

    SideEffect {
        LibraryThemeManager.syncSystemBars(context, theme, isDark)
    }



    // Proveer tokens semánticos en la jerarquía Compose
    CompositionLocalProvider(
        LocalLibraryColorTokens provides tokens,
        LocalFontFamily provides fontFamilyToken,
        LocalLibraryTypography provides semanticTypography,
        LocalLibraryIcons provides semanticIcons
    ) {
        Box(modifier = Modifier.background(bgColor)) {
            if (useMaterial) {
                val colorScheme = remember(tokens, isDark) {
                    ColorMaterialAdapter.toMaterialColorScheme(tokens, isDark)
                }

                val adaptedTypography = TypographyMaterialAdapter.adaptedTypography(semanticTypography, composeFontFamily)

                MaterialTheme(
                    colorScheme = colorScheme,
                    typography = adaptedTypography,
                    shapes = Shapes(),
                    content = content
                )
            } else {
                // No toca MaterialTheme; solamente proveemos tokens de la librería
                // Esto garantiza independencia total de Material por defecto.
                content()
            }
        }

    }


}