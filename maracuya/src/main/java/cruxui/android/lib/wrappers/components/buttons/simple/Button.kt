package cruxui.android.lib.wrappers.components.buttons.simple

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import cruxui.android.lib.R
import cruxui.android.lib.core.components.buttons.tokens.ButtonLayoutTokens
import cruxui.android.lib.core.components.buttons.tokens.ButtonLoadingTokens
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.wrappers.components.buttons.simple.attributes.ButtonAttributeReader
import cruxui.android.lib.wrappers.components.buttons.simple.behavior.ButtonBehavior
import cruxui.android.lib.wrappers.components.buttons.simple.behavior.ButtonBehaviorResolver
import cruxui.android.lib.wrappers.components.buttons.simple.render.ButtonRenderer
import cruxui.android.lib.wrappers.components.buttons.simple.tokens.*
import cruxui.android.lib.wrappers.components.buttons.simple.tokensrender.ButtonTokensRenderResolver
import cruxui.android.lib.wrappers.components.buttons.simple.variant.ButtonVariant
import cruxui.android.lib.wrappers.components.textviews.TextView
import cruxui.android.lib.wrappers.components.textviews.tokens.TextViewNativeOverrides
import cruxui.android.lib.wrappers.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.lib.wrappers.components.textviews.tokens.toNativeOverrides
import cruxui.android.lib.wrappers.core.theme.AndroidThemeAware
import cruxui.android.lib.wrappers.core.theme.AndroidThemeResolver
import cruxui.android.lib.wrappers.core.tokens.ComponentTokenBinding
import cruxui.android.lib.wrappers.core.tokens.TextTypography
import cruxui.android.lib.wrappers.core.tokens.ViewIcon
import kotlin.math.roundToInt

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: Button es un botón Android nativo compuesto por el TextView del DS y un indicador
 * decorativo.
 *
 * Justificación: No necesita ComposeView ni propietarios de composición. La superficie completa
 * conserva las APIs de eventos de View.
 *
 * Se comunica con: Coordina AttributeReader, ComponentTokenBinding, resolutores de
 * behavior/tokensrender y ButtonRenderer.
 *
 * Uso:
 * ```kotlin
 * val button = cruxui.android.lib.wrappers.components.buttons.simple.Button(context)
 * button.text = "Guardar"
 * button.setIconEnd(button.getThemeTokens().icons.alerts.info)
 * button.setOnClickListener {
 *     button.isLoading = true
 *     // Lanzar la operación real desde el controlador de la pantalla.
 * }
 * // Desde el resultado de la operación, siempre en el hilo principal:
 * button.isLoading = false
 * ```
 *
 * También admite android:onClick, setOnLongClickListener, performClick, callOnClick, enabled y
 * pressed. Loading y disabled bloquean todas esas activaciones; los listeners sobreviven a cambios
 * de estado. No conserva operaciones ni callbacks tras recrear la pantalla: el host restaura su
 * estado. Ver ButtonSimpleArchitecture.md para el ejemplo XML y la integración con una función
 * suspendida.
 *
 * Objetivos:
 * - Reutilizar TextView nativo, conservar tamaño en carga y separar ciclo de vida de render.
 *
 * Flujo: Entrada pública → tokens y behavior → tokensrender → representación de plataforma.
 *
 * @param context Context temático para recursos Android.
 * @param attrs Atributos del layout; null para creación programática.
 * @param defStyleAttr Estilo opcional del botón; cero utiliza los defaults del DS.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
open class Button
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr), AndroidThemeAware {
    private var ready = false
    private var rendering = false
    private val initial = ButtonAttributeReader.read(context, attrs, defStyleAttr)
    private val label = TextView(context)
    private val indicator = ImageView(context)
    private val tokenBinding =
        ComponentTokenBinding(ButtonTokensOverrideRegistry) { AndroidThemeResolver.resolve(this) }
    private val renderer = ButtonRenderer(label, indicator)
    private val behaviorResolver = ButtonBehaviorResolver(this, indicator)
    private var runtime: ButtonTokensOverride? = null
    private var nativeText = TextViewNativeOverrides()
    private var tokenBackground = background == null
    private var radius = initial.radius
    private var iconStartDescription = initial.iconStartDescription
    private var iconEndDescription = initial.iconEndDescription

    /** Etiqueta visible y accesible. Conserva spans y permanece disponible durante loading. */
    var text: CharSequence
        get() = label.text
        set(value) {
            label.text = value
            invalidate()
        }

    /** Receta semántica; modificarla conserva listeners, texto y estado del host. */
    var variant: ButtonVariant = initial.variant
        set(value) {
            field = value
            render()
        }

    /** Petición inmutable; disabled precede a loading, y loading a pressed. */
    var behavior: ButtonBehavior = initial.behavior
        set(value) {
            field = value
            render()
        }

    /** Solicitud de carga del host. No se borra al deshabilitar; vuelve a verse al habilitar. */
    var isLoading: Boolean
        get() = behavior.isLoading
        set(value) {
            behavior = behavior.copy(isLoading = value)
        }

    /**
     * Estado disabled efectivo. El setter modifica behavior; android:enabled sigue siendo
     * independiente.
     */
    var isDisabled: Boolean
        get() = !isEnabled || behavior.isDisabled
        set(value) {
            behavior = behavior.copy(isDisabled = value)
        }

    /**
     * Nombre del registro; null vuelve a defaults/XML. Una receta desconocida falla fuera del
     * editor.
     */
    var tokensOverrideName: String? = initial.recipe
        set(value) {
            field = value
            render()
        }

    init {
        isClickable = true
        isFocusable = true
        descendantFocusability = FOCUS_BLOCK_DESCENDANTS
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
        minimumWidth = maxOf(minimumWidth, dp(ButtonLayoutTokens.MinWidthDp))
        minimumHeight = maxOf(minimumHeight, dp(ButtonLayoutTokens.MinHeightDp))
        // View ya leyó padding Android: conservarlo si el host declaró alguno, incluso cero.
        val paddingAttrs =
            context.obtainStyledAttributes(
                attrs,
                intArrayOf(
                    android.R.attr.padding,
                    android.R.attr.paddingHorizontal,
                    android.R.attr.paddingVertical,
                    android.R.attr.paddingLeft,
                    android.R.attr.paddingTop,
                    android.R.attr.paddingRight,
                    android.R.attr.paddingBottom,
                    android.R.attr.paddingStart,
                    android.R.attr.paddingEnd,
                ),
                defStyleAttr,
                0,
            )
        val explicitPadding =
            try {
                (0 until paddingAttrs.indexCount).any {
                    paddingAttrs.hasValue(paddingAttrs.getIndex(it))
                }
            } finally {
                paddingAttrs.recycle()
            }
        if (!explicitPadding)
            setPaddingRelative(
                dp(ButtonLayoutTokens.HorizontalPaddingDp),
                dp(ButtonLayoutTokens.VerticalPaddingDp),
                dp(ButtonLayoutTokens.HorizontalPaddingDp),
                dp(ButtonLayoutTokens.VerticalPaddingDp),
            )
        label.setIconStartContentDescription(iconStartDescription)
        label.setIconEndContentDescription(iconEndDescription)
        label.importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
        label.isFocusable = false
        label.maxLines = initial.maxLines
        label.ellipsize = TextUtils.TruncateAt.END
        label.gravity = Gravity.CENTER
        indicator.importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
        indicator.isFocusable = false
        addView(
            label,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER),
        )
        val size = dp(ButtonLoadingTokens.Icon.iconSize.toFloat())
        addView(indicator, LayoutParams(size, size, Gravity.CENTER))
        measureAllChildren = true
        text = initial.text
        super.setEnabled(initial.enabled)
        ready = true
        render()
    }

    /** @param resource Recurso string localizado; conserva la API de texto habitual de Android. */
    fun setText(@StringRes resource: Int) {
        text = context.getText(resource)
    }

    /** @param tokens Sustituye el parche semántico de ejecución; null restaura receta/XML. */
    fun setTokensOverride(tokens: ButtonTokensOverride?) {
        runtime = tokens
        render()
    }

    /**
     * Retira overrides e iconos de ejecución; restaura XML y receta sin cambiar texto ni listeners.
     */
    fun clearOverrides() {
        runtime = null
        nativeText = TextViewNativeOverrides()
        render()
    }

    /**
     * @param typography Métricas DS explícitas; reemplazan los tamaños tipográficos leídos de XML.
     */
    fun setTypography(typography: TypographyToken) {
        nativeText =
            nativeText.merge(TextViewNativeOverrides(typography = TextTypography.Value(typography)))
        render()
    }

    /** @return Parche actual del host, sin incluir receta ni XML. */
    fun getTokensOverride(): ButtonTokensOverride? = runtime

    /** @param icon Icono decorativo inicial; null retira también el que procede de XML/receta. */
    fun setIconStart(icon: IconToken?) = setIconStart(icon?.let(ViewIcon::Token) ?: ViewIcon.None)

    /**
     * @param icon Fuente semántica del TextView: token, rol, selección de tema o None para
     *   retirarlo.
     */
    fun setIconStart(icon: ViewIcon) {
        nativeText = nativeText.copy(iconStart = icon)
        render()
    }

    /** @param icon Icono decorativo final; null retira también el que procede de XML/receta. */
    fun setIconEnd(icon: IconToken?) = setIconEnd(icon?.let(ViewIcon::Token) ?: ViewIcon.None)

    /** @param icon Fuente del TextView para el extremo final; no instala listeners de icono. */
    fun setIconEnd(icon: ViewIcon) {
        nativeText = nativeText.copy(iconEnd = icon)
        render()
    }

    /** @param description Descripción accesible inicial, entregada al TextView; null la retira. */
    fun setIconStartContentDescription(description: CharSequence?) {
        iconStartDescription = description
        label.setIconStartContentDescription(description)
        sendAccessibilityEvent(
            android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
        )
    }

    /**
     * @param description Descripción accesible final, entregada al TextView; no agrega una acción.
     */
    fun setIconEndContentDescription(description: CharSequence?) {
        iconEndDescription = description
        label.setIconEndContentDescription(description)
        sendAccessibilityEvent(
            android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
        )
    }

    /** @param theme Snapshot por instancia; null restaura la herencia del proveedor XML. */
    fun setThemeTokens(theme: LibraryThemeSnapshot?) {
        tokenBinding.explicitTheme = theme
        refreshTheme()
    }

    /** @return Biblioteca de la instancia para consultar tokens o crear overrides. */
    fun getThemeTokens(): LibraryThemeSnapshot = tokenBinding.library

    /** @param radiusDp Radio finito no negativo; no cambia variante ni medidas de contenido. */
    fun setCornerRadius(radiusDp: Float) {
        require(radiusDp.isFinite() && radiusDp >= 0f)
        radius = radiusDp * resources.displayMetrics.density
        render()
    }

    /**
     * Restaura la superficie tokenizada después de un android:background o setter nativo explícito.
     */
    fun clearBackgroundOverride() {
        tokenBackground = true
        render()
    }

    /** Conserva android:background y los setters de View como overrides explícitos del host. */
    @Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")
    override fun setBackgroundDrawable(background: Drawable?) {
        super.setBackgroundDrawable(background)
        if (ready && !rendering) tokenBackground = false
    }

    /** Habilitación Android estándar; los callbacks se conservan al alternarla. */
    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (ready) render()
    }

    /** Publica pressed real a tokensrender; no inicia carga. */
    override fun setPressed(pressed: Boolean) {
        super.setPressed(pressed)
        if (ready) render()
    }

    /**
     * Toda la superficie pertenece al botón; el texto y sus iconos no capturan eventos
     * independientes.
     */
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean = true

    /**
     * Evita iniciar nuevos gestos durante loading/disabled; performClick también protege eventos ya
     * encolados.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean =
        if (!behaviorResolver.resolve(behavior).canClick) true else super.onTouchEvent(event)

    /** @return false en loading/disabled; de lo contrario ejecuta el listener Android. */
    override fun performClick(): Boolean =
        behaviorResolver.resolve(behavior).canClick && super.performClick()

    /** @return false en loading/disabled, incluso en llamadas programáticas que omiten el gesto. */
    override fun callOnClick(): Boolean =
        behaviorResolver.resolve(behavior).canClick && super.callOnClick()

    /** @return Resultado del listener largo, bloqueado por las mismas reglas que click. */
    override fun performLongClick(): Boolean =
        behaviorResolver.resolve(behavior).canClick && super.performLongClick()

    /** También bloquea la sobrecarga Android con coordenadas utilizada por menús contextuales. */
    override fun performLongClick(x: Float, y: Float): Boolean =
        behaviorResolver.resolve(behavior).canClick && super.performLongClick(x, y)

    /** Publica un único control Button, con etiqueta conservada mientras gira el indicador. */
    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        val state = behaviorResolver.resolve(behavior)
        info.className = android.widget.Button::class.java.name
        info.text = text
        if (
            contentDescription == null &&
                !state.isLoading &&
                (iconStartDescription != null || iconEndDescription != null)
        ) {
            info.contentDescription =
                listOfNotNull(
                        iconStartDescription.takeIf { label.compoundDrawablesRelative[0] != null },
                        text,
                        iconEndDescription.takeIf { label.compoundDrawablesRelative[2] != null },
                    )
                    .joinToString(" ")
        }
        info.isEnabled = state.canClick
        info.isClickable = isClickable && state.canClick
        info.isLongClickable = isLongClickable && state.canClick
        if (!state.canClick) {
            info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK)
            info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK)
        }
    }

    /** Reevalúa recetas cuando cambia el proveedor del ámbito; no reinfla estilos Android. */
    override fun refreshTheme() {
        if (ready) {
            tokenBinding.refresh()
            render()
        }
    }

    /** Recupera tema y arranca el indicador solo si la View ya es visible. */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        refreshTheme()
    }

    /** Cancela el motor de carga al desmontar la View. */
    override fun onDetachedFromWindow() {
        behaviorResolver.stop()
        super.onDetachedFromWindow()
    }

    /** Reevalúa el modo del sistema y el snapshot; el host conserva su operación. */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        refreshTheme()
    }

    /** Detiene el reloj cuando se oculta esta View o un ancestro. */
    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (ready) behaviorResolver.update(behaviorResolver.resolve(behavior))
    }

    /** Detiene el reloj en ventanas invisibles y lo recupera al volver. */
    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (ready) behaviorResolver.update(behaviorResolver.resolve(behavior))
    }

    /** Coordina resolutores; renderer no vuelve a decidir colores ni iniciar animaciones. */
    private fun render() {
        if (!ready || rendering) return
        rendering = true
        try {
            val state = behaviorResolver.resolve(behavior)
            if (!state.canClick) {
                super.setPressed(false)
                cancelLongPress()
            }
            val tokens =
                ButtonTokensResolver.resolve(
                    tokenBinding.library,
                    variant,
                    tokenBinding.recipe(tokensOverrideName, isInEditMode),
                    initial.tokens,
                    runtime,
                )
            val runtimeText =
                runtime?.let {
                    TextViewTokensOverride(
                            typography = it.typography,
                            fontFamily = it.fontFamily,
                            iconStart = it.iconStart,
                            iconEnd = it.iconEnd,
                        )
                        .toNativeOverrides()
                } ?: TextViewNativeOverrides()
            val textStyle = initial.textStyle.merge(runtimeText).merge(nativeText)
            renderer.render(
                this,
                tokenBinding.library,
                ButtonTokensRenderResolver.resolve(tokens, state),
                variant,
                textStyle,
                state.isLoading,
                radius,
                tokenBackground,
            )
            ViewCompat.setStateDescription(
                this,
                if (state.isLoading) context.getString(R.string.button_loading) else null,
            )
            behaviorResolver.update(state)
        } finally {
            rendering = false
        }
    }

    /** Convierte las medidas estándar a píxeles respetando densidad. */
    private fun dp(value: Float): Int = (value * resources.displayMetrics.density).roundToInt()
}
