package cruxui.android.lib.core.components.textviews.behavior

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewBehavior` declara si el texto admite selección y copia.
 *
 * Justificación:
 * Comparte una capacidad funcional entre plataformas sin acoplarla a listeners Android o lambdas Compose. Los
 * callbacks se configuran en la API de cada componente.
 *
 * Se comunica con:
 * Los resolutores de comportamiento Compose y XML interpretan `textSelectionEnabled`; sus controladores reservan
 * los gestos de selección.
 *
 * Uso:
 * API pública del host. En Compose: `TextView(text = "Copiar", behavior = TextViewBehavior.Selectable)`.
 * En Views: `view.setBehavior(TextViewBehavior.Selectable)` o `view.setTextIsSelectable(true)`.
 * `Default` no habilita selección. También puede crearse `TextViewBehavior(textSelectionEnabled = true)`.
 * La selección reserva la pulsación larga y el doble toque del texto; los iconos conservan sus acciones
 * independientes. Un componente deshabilitado no permite activar sus callbacks.
 *
 * Objetivos:
 * - Describir capacidades, no eventos.
 * - Evitar conflictos entre selección y gestos personalizados.
 * - Reutilizar el mismo contrato en Compose y Views.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param textSelectionEnabled Solicita selección de texto; false por defecto.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
data class TextViewBehavior(
    val textSelectionEnabled: Boolean = false,
) {
    /**
     * s4121779 - Krause Kelsen
     *
     * Descripción:
     * El objeto compañero publica configuraciones habituales de `TextViewBehavior`.
     *
     * Justificación:
     * Evita repetir instancias y nombres de opciones en las pantallas.
     *
     * Se comunica con:
     * Entrega `Default` y `Selectable` a ambos TextView.
     *
     * Uso:
     * El host utiliza `TextViewBehavior.Default` o `TextViewBehavior.Selectable`; son valores inmutables. Para
     * otra configuración crear o copiar `TextViewBehavior`.
     *
     * Objetivos:
     * - Dar nombres claros a las capacidades disponibles.
     *
     * @author Krause Kelsen
     * @since 07-16-2026
     * @version 0.0.13
     * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
     */
    companion object {
        /**
         * Configuración pública sin selección de texto; los callbacks se declaran en el componente.
         */
        val Default = TextViewBehavior()
        /**
         * Configuración pública con selección y copia; reserva pulsación larga y doble toque del texto.
         */
        val Selectable = TextViewBehavior(textSelectionEnabled = true)
    }
}
