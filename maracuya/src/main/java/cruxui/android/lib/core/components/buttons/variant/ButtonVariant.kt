package cruxui.android.lib.core.components.buttons.variant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonVariant identifica las tres recetas del botón simple.
 *
 * Justificación: El tamaño y los estados son independientes de la variante.
 *
 * Se comunica con: ButtonTokenDefaults selecciona colores; los adaptadores conservan TextView
 * DEFAULT en las tres variantes.
 *
 * Uso:
 * ```kotlin
 * val variant = ButtonVariant.TERCIARY
 * // Compose: Button(text = "Condiciones", onClick = openTerms, variant = variant)
 * ```
 *
 * En XML, app:buttonVariant="terciary" selecciona el borde rojo de marca. Button no tiene variante
 * text ni hipertexto.
 *
 * Objetivos:
 * - Mantener exactamente tres variantes semánticas.
 *
 * Flujo: Variante del botón → superficies de marca/neutras → TextView DEFAULT.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
enum class ButtonVariant {
    /** Fondo de marca y texto inverso. */
    PRIMARY,
    /** Fondo base, texto y borde neutros: blanco con borde negro en modo claro. */
    SECONDARY,
    /** Fondo base, borde de marca y texto adaptado al modo: blanco con borde rojo en modo claro. */
    TERCIARY,
}
