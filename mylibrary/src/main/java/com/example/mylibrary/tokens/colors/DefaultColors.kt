package com.example.mylibrary.tokens.colors

import com.example.mylibrary.tokens.base.ColorToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Define la **paleta atómica de colores** del Design System.
 * Este archivo representa el nivel más básico dentro de la jerarquía de tokens:
 *
 *  - **Nivel atómico (raw colors):** colores puros, sin significado semántico.
 *  - **Sin dependencias de semántica.**
 *  - **Se mantiene 100% sincronizado con Figma**
 *    (Maracuyá → Foundations → Colors).
 *
 * Cada grupo corresponde a una categoría visual aprobada por diseño, y cada
 * sub-nivel (`c600`, `c500`, `c300`, etc.) sigue la escala de intensidades del
 * sistema corporativo (similar a Material Design, pero personalizada).
 *
 * Estos tokens NO deben usarse directamente desde el host. Son consumidos por
 * los tokens semánticos (`LibraryColorTokens`) y, posteriormente, por el tema
 * final (`MyLibraryTheme`).
 *
 * Uso:
 * ```
 * DefaultColors.Brand.Red.c500
 * DefaultColors.Ink.c900
 * DefaultColors.Error.c300
 * ```
 *
 * @author Krause Kelsen
 * @since 10-23-2025
 * @version 1.5.4
 *
 * Grupos:
 * @param Brand     Paleta primaria de marca (rojos). Usada para acciones principales.
 * @param Support   Paleta de soporte (azules). Usada para acciones secundarias, links e info.
 * @param Teal      Por definir en semántica (tono verde-azulado).
 * @param Purple    Por definir en semántica (tonos púrpura).
 * @param Green     Colores de éxito (Success).
 * @param Yellow    Advertencias (Warnings).
 * @param Orange    Por definir en semántica.
 * @param Ink       Grises base para backgrounds, bordes y contenido.
 * @param Core      Colores neutrales esenciales (blanco y negro).
 * @param Error     Estados de error.
 * @param DarkMode  Colores exclusivos para modo oscuro (superficies, bordes, overlays, etc.).
 *
 * @see com.example.mylibrary.tokens.colors.DefaultColors
 */
object DefaultColors {

    object Brand {
        object Red {
            val c600 = ColorToken.fromHex(0xFFB5161F) // Brand: énfasis fuerte (dark)
            val c500 = ColorToken.fromHex(0xFFED1C27) // Brand principal
            val c300 = ColorToken.fromHex(0xFFFFA5A5) // Brand claro (soft)
        }
    }

    object Support {
        object Blue {
            val c700 = ColorToken.fromHex(0xFF4B5860) // Azul apagado (utilidades)
            val c600 = ColorToken.fromHex(0xFF0067A1) // Azul fuerte
            val c500 = ColorToken.fromHex(0xFF0082C4) // Azul principal
            val c300 = ColorToken.fromHex(0xFFA0D8F5) // Azul claro
            val c100 = ColorToken.fromHex(0xFFE3F3FC) // Azul extra claro (backgrounds)
            val cBG  = ColorToken.fromHex(0xFFF1F5F7) // Fondo suave vinculado al sistema
        }
    }

    object Teal {
        val c500 = ColorToken.fromHex(0xFF00BFA5)
        val c300 = ColorToken.fromHex(0xFF99F0E5)
        val c100 = ColorToken.fromHex(0xFFE0FBF7)
    }

    object Purple {
        val c500 = ColorToken.fromHex(0xFF9C27B0)
        val c300 = ColorToken.fromHex(0xFFE1BEE7)
        val c100 = ColorToken.fromHex(0xFFF6EBF8)
    }

    object Green {
        val c500 = ColorToken.fromHex(0xFF4CAF50) // Success principal
        val c300 = ColorToken.fromHex(0xFFA5D6A7) // Success claro
        val c100 = ColorToken.fromHex(0xFFE4F3E5) // Background success
    }

    object Yellow {
        val c500 = ColorToken.fromHex(0xFFFFE11B) // Warning principal
        val c300 = ColorToken.fromHex(0xFFFFF5A1) // Warning claro
        val c100 = ColorToken.fromHex(0xFFFFFCE3) // Background warning
        val cBG  = ColorToken.fromHex(0xFFF7F5F1) // Fondo auxiliar
    }

    object Orange {
        val c500 = ColorToken.fromHex(0xFFF8991D)
        val c300 = ColorToken.fromHex(0xFFFFF5A1)
        val c100 = ColorToken.fromHex(0xFFFFFCE3)
    }

    object Ink {
        val c900 = ColorToken.fromHex(0xFF231F20) // Texto alto contraste
        val c500 = ColorToken.fromHex(0xFF575756) // Texto medio
        val c400 = ColorToken.fromHex(0xFF9E9E9E) // Texto desactivado
        val c300 = ColorToken.fromHex(0xFFC6C6C6) // Bordes claros
        val c200 = ColorToken.fromHex(0xFFE5E5E5) // Dividers
        val c100 = ColorToken.fromHex(0xFFF5F5F5) // Background neutro
    }

    object Core {
        val black = ColorToken.fromHex(0xFF000000)
        val white = ColorToken.fromHex(0xFFFFFFFF)
    }

    object Error {
        val c700 = ColorToken.fromHex(0xFF8F1D26)
        val c600 = ColorToken.fromHex(0xFFC62828)
        val c500 = ColorToken.fromHex(0xFFE53935)
        val c300 = ColorToken.fromHex(0xFFF8B4B4)
        val c100 = ColorToken.fromHex(0xFFFDECEC)
    }

    object DarkMode {

        object Blue {
            val cHover = ColorToken.fromHex(0xFF4BADE6) // Hover states en dark
        }

        object Error {
            val cRojo = ColorToken.fromHex(0xFFFF6B6B) // Error vibrante en dark
        }

        object Bg {
            val cBase = ColorToken.fromHex(0xFF0F1417) // Fondo principal en dark
        }

        object Surface {
            val c200 = ColorToken.fromHex(0xFF141A1E)
            val c100 = ColorToken.fromHex(0xFF12181C)
        }

        object Overlay {
            val c200 = ColorToken.fromHex(0xFF182329)
        }

        object Border {
            val cSubtle  = ColorToken.fromHex(0xFF26313A)
            val cDefault = ColorToken.fromHex(0xFF2E3A44)
        }
    }
}
