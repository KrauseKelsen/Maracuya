package cruxui.android.lib.tokens.spacings

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * InteractionSpacings define la dimensión mínima de las zonas interactivas del DS en dp.
 *
 * Justificación:
 * Compose y Views necesitan el mismo contrato sin depender de las unidades de la otra plataforma.
 * TextViewLayoutTokens y las capacidades de drawables nativos consumen este valor.
 *
 * Uso:
 * API pública de diseño: convertir MinimumTargetSizeDp a dp en Compose o a píxeles con la densidad Android.
 * El componente decide dónde reservar el espacio; el tamaño visual del icono sigue en IconToken.
 *
 * Objetivos:
 * - Compartir las medidas interactivas entre componentes y plataformas.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
object InteractionSpacings {
    /** Lado mínimo de una zona interactiva; independiente del tamaño visual del asset. */
    const val MinimumTargetSizeDp = 48f
}
