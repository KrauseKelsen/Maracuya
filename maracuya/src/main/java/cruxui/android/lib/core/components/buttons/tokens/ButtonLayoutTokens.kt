package cruxui.android.lib.core.components.buttons.tokens

import cruxui.android.lib.tokens.spacings.InteractionSpacings

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonLayoutTokens define las medidas estándar del botón en dp.
 *
 * Justificación: Un único tamaño base evita convertir altura, padding o forma en variantes
 * semánticas. La altura puede crecer con la fuente.
 *
 * Se comunica con: Los renders Compose y XML convierten estos valores; TextView conserva su propia
 * separación de iconos.
 *
 * Uso:
 * ```kotlin
 * val minimumHeightDp = ButtonLayoutTokens.MinHeightDp
 * ```
 *
 * El host puede ampliar el ancho con Modifier.fillMaxWidth() o android:layout_width="match_parent".
 * El radio estándar produce la cápsula de las referencias; cambiarlo no altera la variante.
 *
 * Objetivos:
 * - Centralizar el contrato de medidas y permitir fuentes grandes.
 *
 * Flujo: Tema y overrides → receta visual compartida → resolución posterior del estado.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
object ButtonLayoutTokens {
    /** Altura mínima táctil; el texto puede exigir más altura. */
    const val MinHeightDp = InteractionSpacings.MinimumTargetSizeDp
    /** Ancho mínimo; conserva la proporción de las referencias para el texto Button. */
    const val MinWidthDp = 112f
    /** Espacio horizontal de cada extremo de la superficie. */
    const val HorizontalPaddingDp = 24f
    /** Espacio vertical por encima y debajo del contenido. */
    const val VerticalPaddingDp = 12f
    /** Radio estándar heredado del ButtonMrcy. */
    const val CornerRadiusDp = 100f
    /** Grosor de borde compartido por todas las variantes. */
    const val BorderWidthDp = 1f
}
