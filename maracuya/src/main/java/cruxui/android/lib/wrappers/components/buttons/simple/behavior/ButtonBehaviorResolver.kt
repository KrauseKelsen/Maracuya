package cruxui.android.lib.wrappers.components.buttons.simple.behavior

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import cruxui.android.lib.core.components.buttons.behavior.ButtonBehaviorResolver as CoreResolver
import cruxui.android.lib.core.components.buttons.behavior.ButtonBehaviorState

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonBehaviorResolver adapta estados y rotación al ciclo de vida Android Views.
 *
 * Justificación: Un único animator por instancia gira el indicador; se cancela al ocultar,
 * desmontar o abandonar loading.
 *
 * Se comunica con: CoreResolver comparte prioridad y período con Compose; Button comunica estado y
 * visibilidad.
 *
 * Uso:
 * ```kotlin
 * // API del host:
 * button.isLoading = true
 * // Al recibir éxito, error o cancelación:
 * button.isLoading = false
 * ```
 *
 * La animación nunca decide que terminó la operación.
 *
 * Objetivos:
 * - Evitar animaciones huérfanas y bloquear callbacks por una política común.
 *
 * Flujo: Entrada del host y gesto → prioridad compartida → bloqueo de acciones y motor de
 * animación.
 *
 * @param owner Botón cuya visibilidad y attachment controlan el animator.
 * @param indicator View del icono de carga; no recibe eventos ni decide estados.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal class ButtonBehaviorResolver(private val owner: View, private val indicator: View) {
    private var animator: ObjectAnimator? = null

    /**
     * @param behavior Flags solicitados por el host.
     * @return Estado efectivo combinando enabled y pressed de Android.
     */
    fun resolve(behavior: ButtonBehavior): ButtonBehaviorState =
        CoreResolver.resolve(behavior, owner.isEnabled, owner.isPressed)

    /**
     * Sincroniza el reloj sin reiniciarlo durante renders repetidos.
     *
     * @param state Estado efectivo actual; solo loading visible inicia el motor.
     */
    fun update(state: ButtonBehaviorState) {
        if (
            !state.isLoading ||
                !owner.isAttachedToWindow ||
                !owner.isShown ||
                owner.windowVisibility != View.VISIBLE ||
                owner.isInEditMode
        ) {
            stop()
            return
        }
        if (animator != null) return
        animator =
            ObjectAnimator.ofFloat(indicator, View.ROTATION, 0f, CoreResolver.RotationDegrees)
                .apply {
                    duration = CoreResolver.RotationDurationMillis.toLong()
                    repeatCount = ValueAnimator.INFINITE
                    interpolator = LinearInterpolator()
                    start()
                }
    }

    /** Cancela el motor y restablece el ángulo; invocar al desmontar o esconder el botón. */
    fun stop() {
        animator?.cancel()
        animator = null
        indicator.rotation = 0f
    }
}
