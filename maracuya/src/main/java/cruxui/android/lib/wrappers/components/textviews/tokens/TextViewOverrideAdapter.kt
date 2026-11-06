package cruxui.android.lib.wrappers.components.textviews.tokens

import cruxui.android.lib.wrappers.core.tokens.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `toNativeOverrides` adapta cada campo del override semántico compartido a una entrada del wrapper Android.
 *
 * Justificación:
 * Evita que los setters directos necesiten un contrato semántico distinto al de las recetas y Compose.
 *
 * Se comunica con:
 * Recibe TextViewTokensOverride y crea TextViewNativeOverrides; TextView.setTokensOverride utiliza esta
 * conversión.
 *
 * Uso:
 * Extensión interna del DS. El host llama `view.setTokensOverride(...)`; no necesita invocar este adaptador.
 * textColor se convierte a foregroundDefault para conservar los estados error/disabled. Null sigue representando
 * ausencia; no elimina iconos ni superficies.
 *
 * Objetivos:
 * - Conservar los campos compartidos.
 * - Mantener la semántica de colores por estado.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride
 */
internal fun TextViewTokensOverride.toNativeOverrides() = TextViewNativeOverrides(
    foregroundDefault = textColor?.let(TextColor::Token),
    foregroundError = errorColor?.let(TextColor::Token),
    foregroundDisabled = disabledColor?.let(TextColor::Token),
    background = backgroundColor?.let { ViewSurface.Color(TextColor.Token(it)) },
    foreground = foregroundColor?.let { ViewSurface.Color(TextColor.Token(it)) },
    typography = typography?.let(TextTypography::Value),
    font = fontFamily?.let(TextFont::Family),
    iconStart = iconStart?.let(ViewIcon::Token),
    iconEnd = iconEnd?.let(ViewIcon::Token),
)
