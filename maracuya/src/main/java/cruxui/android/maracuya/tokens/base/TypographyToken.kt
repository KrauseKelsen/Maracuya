package cruxui.android.maracuya.tokens.base

import cruxui.android.maracuya.tokens.fonts.FontStyleTokens

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Token tipográfico atómico/semántico independiente de Compose.
 * Esta clase representa los valores mínimos necesarios para construir
 * un estilo tipográfico en cualquier plataforma (fontSize, lineHeight, tracking,
 * weight como entero, y estilo normal/italic).
 *
 * Importante: NO usar Compose aquí. El adaptador en `theme` (TypographyAdapter)
 * transformará estos tokens a `androidx.compose.ui.text.TextStyle` cuando sea requerido.
 *
 * @author Krause Kelsen
 * @since 2025-11-18
 * @version 1.5.4
 */
data class TypographyToken(
    /** Tamaño de fuente en `sp` representado como Float (p. ej. 16f). */
    val fontSize: Float,

    /** Line-height en `sp` como Float (ya resuelto) */
    val lineHeight: Float,

    /** Letter spacing (tracking) en `sp` como Float */
    val letterSpacing: Float = 0f,

    /** Peso tipográfico representado como entero (400, 500, 700, 800) */
    val weight: Int = 400,

    /** Si aplica estilo italic (true) o normal (false) */
    val italic: Boolean = false,

    /** Nuevo sistema unificado de estilo */
    val style: Int = FontStyleTokens.Normal
)