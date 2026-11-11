package cruxui.android.lib.core.components.buttons.behavior

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonBehavior expresa el estado solicitado por el host, sin temporizadores ni
 * trabajo de red.
 *
 * Justificación: La operación pertenece a la pantalla. El botón permanece cargando hasta que el
 * host cambia isLoading.
 *
 * Se comunica con: ButtonBehaviorResolver combina estos flags con enabled y la pulsación nativa de
 * cada plataforma.
 *
 * Uso:
 * ```kotlin
 * val busy = ButtonBehavior(isLoading = true)
 * val finished = busy.copy(isLoading = false)
 * ```
 *
 * En Compose pasar behavior = busy; en Views button.behavior = busy. isPressed permite estados
 * controlados y previews; los gestos reales se combinan en el resolver.
 *
 * Objetivos:
 * - Mantener datos inmutables y una política de estado compartida.
 *
 * Flujo: Entrada del host y gesto → prioridad compartida → bloqueo de acciones y motor de
 * animación.
 *
 * @param isLoading Solicita carga hasta que el host la retire; no se activa automáticamente al
 *   pulsar.
 * @param isDisabled Bloquea interacción y precede visualmente a loading y pressed.
 * @param isPressed Solicita pressed; se ignora mientras carga o está deshabilitado.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
data class ButtonBehavior(
    val isLoading: Boolean = false,
    val isDisabled: Boolean = false,
    val isPressed: Boolean = false,
) {
    /**
     * Configuraciones inmutables reutilizables; el host también puede construir o copiar el
     * contrato.
     */
    companion object {
        /** Botón disponible, sin carga ni pulsación forzada. */
        val Default = ButtonBehavior()
    }
}
