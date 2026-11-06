package cruxui.android.lib.core.components.textviews.tokens

import cruxui.android.lib.tokens.spacings.InteractionSpacings

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextViewLayoutTokens declara las medidas base comunes de los TextView Compose y XML.
 *
 * Justificación:
 * Conserva la disposición Compose como referencia: sin padding exterior, separación tras cada icono
 * presente y una celda mayor para los iconos con acciones. No mezcla comportamiento con valores de marca.
 * TextViewContent y los adaptadores nativos consumen estos mismos números, sin duplicar defaults.
 *
 * Uso:
 * API pública del DS y del host. IconSpacingDp se interpreta en dp; MinimumInteractiveIconSizeDp
 * reserva una celda que centra el asset, conservando IconToken.iconSize. Un icono decorativo ocupa
 * solamente su tamaño. Deshabilitar una acción conserva su celda; retirar el icono elimina su espacio.
 * En XML, app:iconPadding o android:drawablePadding sustituyen la separación. android:padding y los
 * setters nativos siguen siendo ajustes explícitos del host. No añadir aquí márgenes de previews.
 *
 * Objetivos:
 * - Mantener coherencia de medidas sin depender de Android ni Compose.
 * - Separar el tamaño del asset, la celda interactiva y el padding del componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
object TextViewLayoutTokens {
    /** Distancia entre el texto y cada celda de icono presente. */
    const val IconSpacingDp = 8f
    /** Mínimo de una celda con callbacks, incluso cuando el componente está deshabilitado. */
    const val MinimumInteractiveIconSizeDp = InteractionSpacings.MinimumTargetSizeDp
    /** Compose no añade padding de fuente al texto; XML aplica el mismo valor por defecto. */
    const val IncludeFontPadding = false
    /** Número mínimo de líneas del componente. */
    const val MinLines = 1
    /** Sin límite práctico de líneas salvo configuración explícita del host. */
    const val MaxLines = Int.MAX_VALUE
}
