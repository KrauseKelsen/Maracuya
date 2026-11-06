package cruxui.android.lib.ui.components.textviews.variant

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewVariantResolver` aplica la decoración de texto específica de una variante en Compose.
 *
 * Justificación:
 * Mantiene las decisiones de TextStyle fuera del núcleo puro y del contenido que dibuja el componente.
 *
 * Se comunica con:
 * TextView le entrega un TextStyle convertido por TypographyComposeAdapter; el resultado llega a
 * TextViewContent.
 *
 * Uso:
 * Objeto interno del DS. El host selecciona variant en TextView. Internamente resolveTextStyle conserva DEFAULT
 * y copia el estilo con subrayado para HIPERTEXT.
 *
 * Objetivos:
 * - Adaptar la variante a TextStyle.
 * - Mantener el resto de la tipografía resuelta.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal object TextViewVariantResolver {

    /**
     * Aplica la decoración propia de la variante al estilo adaptado.
     *
     * @param variant DEFAULT o HIPERTEXT.
     * @param textStyle Estilo construido desde los tokens de tipografía y fuente.
     * @return El mismo estilo para DEFAULT; una copia con textDecoration Underline para HIPERTEXT.
     */
    fun resolveTextStyle(
        variant: TextViewVariant,
        textStyle: TextStyle,
    ): TextStyle =
        when (variant) {
            TextViewVariant.DEFAULT -> textStyle
            TextViewVariant.HIPERTEXT -> textStyle.copy(
                textDecoration = TextDecoration.Underline,
            )
        }
}
