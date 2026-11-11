package cruxui.android.lib.core.components.buttons.behavior

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonBehaviorResolver normaliza prioridades y el contrato temporal de rotación.
 *
 * Justificación: Compartir disabled > loading > pressed evita que las plataformas publiquen estados
 * o acciones divergentes.
 *
 * Se comunica con: Los behavior resolvers Compose y XML aportan el gesto real y ejecutan su reloj
 * de animación.
 *
 * Uso:
 * ```kotlin
 * val state = ButtonBehaviorResolver.resolve(ButtonBehavior(isLoading = true))
 * check(state.isLoading && !state.canClick)
 * ```
 *
 * Resolver no inicia ni finaliza operaciones: solo responde al estado recibido.
 *
 * Objetivos:
 * - Unificar prioridad, bloqueo de callbacks y velocidad de carga.
 *
 * Flujo: Entrada del host y gesto → prioridad compartida → bloqueo de acciones y motor de
 * animación.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
object ButtonBehaviorResolver {
    /** Duración de una vuelta; nunca es la duración de la operación del host. */
    const val RotationDurationMillis = 1000
    /** Ángulo de una vuelta completa consumido por ambos motores. */
    const val RotationDegrees = 360f

    /**
     * Deriva el estado efectivo; no modifica la solicitud del host.
     *
     * @param behavior Flags solicitados por la pantalla.
     * @param enabled Habilitación estándar; false equivale a disabled.
     * @param pressed Pulsación física/teclado que observa la plataforma.
     * @return Estado mutuamente excluyente; disabled suspende visualmente loading sin borrarlo.
     */
    fun resolve(
        behavior: ButtonBehavior = ButtonBehavior.Default,
        enabled: Boolean = true,
        pressed: Boolean = false,
    ): ButtonBehaviorState {
        val disabled = !enabled || behavior.isDisabled
        val loading = !disabled && behavior.isLoading
        return ButtonBehaviorState(
            disabled,
            loading,
            !disabled && !loading && (pressed || behavior.isPressed),
        )
    }
}

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonBehaviorState contiene los flags efectivos tras aplicar prioridades.
 *
 * Justificación: El render y los eventos consumen el mismo resultado normalizado.
 *
 * Se comunica con: ButtonBehaviorResolver lo produce para tokensrender y los adaptadores de UI.
 *
 * Uso:
 * ```kotlin
 * val state = ButtonBehaviorResolver.resolve(enabled = false)
 * check(state.isDisabled && !state.canClick)
 * ```
 *
 * Objetivos:
 * - Separar la petición del host del estado efectivo.
 *
 * Flujo: Entrada del host y gesto → prioridad compartida → bloqueo de acciones y motor de
 * animación.
 *
 * @param isDisabled Deshabilitado efectivo.
 * @param isLoading Carga visible; false si disabled tiene prioridad.
 * @param isPressed Pulsación visible; false si disabled o loading tienen prioridad.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
@ConsistentCopyVisibility
data class ButtonBehaviorState
internal constructor(val isDisabled: Boolean, val isLoading: Boolean, val isPressed: Boolean) {
    /** Permite activar click y pulsación larga solamente en reposo o pressed. */
    val canClick: Boolean
        get() = !isDisabled && !isLoading
}
