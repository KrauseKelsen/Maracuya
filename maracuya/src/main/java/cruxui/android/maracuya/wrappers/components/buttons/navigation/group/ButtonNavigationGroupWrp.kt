package cruxui.android.maracuya.wrappers.components.buttons.navigation.group

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import cruxui.android.maracuya.ui.components.buttons.navigation.group.core.ButtonNavigationGroupDefaults
import cruxui.android.maracuya.ui.components.buttons.navigation.group.ButtonNavigationGroupMrcy
import cruxui.android.maracuya.ui.components.buttons.navigation.group.behavior.ButtonNavigationGroupBehavior
import cruxui.android.maracuya.ui.components.buttons.navigation.group.variant.ButtonNavigationGroupVariant
import cruxui.android.maracuya.wrappers.core.MrcyXmlComposeView

/**
 *
 * Descripción:
 * Wrapper Android View que expone `ButtonNavigationGroupMrcy` para layouts XML tradicionales.
 *
 * Mantiene una API mutable equivalente a los parametros funcionales de Compose:
 * variante, labels, anchos, estado enabled del grupo, estado enabled por boton y comportamiento.
 *
 * [ButtonNavigationGroupWrp] integra las variantes fijas de dos botones de
 * [ButtonNavigationGroupMrcy] en una vista Android. Lee la configuración inicial desde
 * XML y conserva sus propiedades en estados de Compose para actualizar el contenido
 * cuando el host las modifica. El tema se aplica mediante `WithXmlTheme` de la clase base.
 *
 * Configuración:
 * - [variant] selecciona la combinación de acciones y sus textos e íconos predeterminados.
 * - [leadingLabel] y [trailingLabel] permiten personalizar los textos.
 * - [leadingButtonWidth], [trailingButtonWidth] y [buttonSpacing] configuran las medidas en [Dp].
 * - [setEnabled] establece el estado heredado por los botones sin override individual.
 * - [leadingEnabled] y [trailingEnabled] permiten sobrescribir esa herencia por botón.
 * - [buttonNavigationGroupBehavior] configura la carga del botón final.
 * - [setOnLeadingButtonClickListener] y [setOnTrailingButtonClickListener] registran las acciones.
 *
 * Uso (combinaciones de la preview):
 * - `BACK_CONTINUE` / `BACK_SUBMIT`: Back con ícono + Continue o Submit.
 * - `SKIP_CONTINUE` / `SKIP_SUBMIT`: Skip sin ícono + Continue o Submit.
 *   Submit cambia también el ícono final por el de confirmación.
 * - Cualquiera de estas variantes + `Loading`: muestra carga en el botón final.
 *   Con `trailingLabel = null` muestra "Loading"; un texto personalizado tiene prioridad.
 * - Labels + anchos + separación: personalizan texto y distribución sin cambiar la variante.
 *   Sin separación explícita, los botones ocupan los extremos del espacio disponible.
 *
 * ```kotlin
 * // Equivalente al caso personalizado de la preview. Requiere androidx.compose.ui.unit.dp.
 * val navigation = ButtonNavigationGroupWrp(context).apply {
 *     variant = ButtonNavigationGroupVariant.BACK_CONTINUE
 *     leadingLabel = "Volver"
 *     trailingLabel = "Siguiente"
 *     leadingButtonWidth = 144.dp
 *     trailingButtonWidth = 184.dp
 *     buttonSpacing = 12.dp // Separación fija entre los botones.
 *     setOnLeadingButtonClickListener { /* Regresar al paso anterior. */ }
 *     setOnTrailingButtonClickListener { /* Validar y avanzar al siguiente paso. */ }
 * }
 *
 * // Carga con texto automático; conservar "Siguiente" si no se limpia trailingLabel.
 * navigation.trailingLabel = null
 * navigation.buttonNavigationGroupBehavior = ButtonNavigationGroupBehavior.Loading
 * // Al finalizar, recupera el texto de la variante ("Continue").
 * navigation.buttonNavigationGroupBehavior = ButtonNavigationGroupBehavior.Default
 * ```
 *
 * Los ejemplos con `children` pertenecen exclusivamente a [ButtonNavigationGroupMrcy]:
 * permiten combinar cantidad de botones, variantes, `showIcon` y tokens por hijo.
 * Sin `buttonSpacing` quedan unidos; con `10.dp` o `12.dp` quedan separados.
 * Para esas combinaciones se utiliza la API Compose, ya que este wrapper recibe dos botones fijos.
 *
 * Consideraciones:
 * Un override individual en `true` puede mantener un botón habilitado aunque el grupo
 * esté deshabilitado. Para recuperar la herencia deben usarse los métodos `clear…Override`.
 * Si un botón no tiene listener específico, su acción se delega a `performClick()` de
 * esta vista; ambos botones comparten entonces el listener estándar de Android.
 * El wrapper expone variantes fijas; no recibe la lista de hijos de la otra sobrecarga Compose.
 *
 * @author Krause Kelsen
 * @since 10-19-2026
 * @version 0.0.13
 *
 * @param context Contexto Android utilizado por la vista y para resolver recursos y atributos XML.
 * @param attrs Atributos del layout XML. Por defecto `null`, que utiliza la configuración
 * predeterminada: variante `BACK_CONTINUE`, textos y medidas sin especificar, grupo habilitado,
 * sin overrides individuales y comportamiento `Default`.
 * @param defStyleAttr Identificador del atributo de tema que define el estilo predeterminado
 * de la vista base; `0` no solicita un atributo de estilo. Se transmite a [MrcyXmlComposeView],
 * pero el parser de atributos propios del grupo no recibe este parámetro.
 *
 * @see ButtonNavigationGroupMrcy
 * @see ButtonNavigationGroupVariant
 * @see ButtonNavigationGroupBehavior
 * @see ButtonNavigationGroupDefaults
 * @see MrcyXmlComposeView
 */
class ButtonNavigationGroupWrp @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MrcyXmlComposeView(context, attrs, defStyleAttr) {

    private var variantState by mutableStateOf(ButtonNavigationGroupVariant.BACK_CONTINUE)
    private var leadingLabelState by mutableStateOf<String?>(null)
    private var trailingLabelState by mutableStateOf<String?>(null)
    private var leadingButtonWidthState by mutableStateOf(ButtonNavigationGroupDefaults.LeadingButtonWidth)
    private var trailingButtonWidthState by mutableStateOf(ButtonNavigationGroupDefaults.TrailingButtonWidth)
    private var buttonSpacingState by mutableStateOf(ButtonNavigationGroupDefaults.ButtonSpacing)
    private var enabledState by mutableStateOf(isEnabled)
    private var leadingEnabledOverrideState by mutableStateOf<Boolean?>(null)
    private var trailingEnabledOverrideState by mutableStateOf<Boolean?>(null)
    private var buttonNavigationGroupBehaviorState by mutableStateOf(ButtonNavigationGroupBehavior.Default)
    private var onLeadingButtonClickState by mutableStateOf<(() -> Unit)?>(null)
    private var onTrailingButtonClickState by mutableStateOf<(() -> Unit)?>(null)

    init {
        val initialValues = ButtonNavigationGroupAttributeParser.parse(context, attrs)
        modifier = initialValues.modifier
        variantState = initialValues.variant
        leadingLabelState = initialValues.leadingLabel
        trailingLabelState = initialValues.trailingLabel
        leadingButtonWidthState = initialValues.leadingButtonWidth
        trailingButtonWidthState = initialValues.trailingButtonWidth
        buttonSpacingState = initialValues.buttonSpacing
        enabledState = initialValues.enabled
        leadingEnabledOverrideState = initialValues.leadingEnabledOverride
        trailingEnabledOverrideState = initialValues.trailingEnabledOverride
        buttonNavigationGroupBehaviorState = initialValues.buttonNavigationGroupBehavior
    }

    /**
     * Combinación fija de acciones: `BACK_CONTINUE`, `BACK_SUBMIT`, `SKIP_CONTINUE`
     * o `SKIP_SUBMIT`. Determina textos e íconos predeterminados; los labels explícitos
     * conservan prioridad. Sin configuración XML, utiliza `BACK_CONTINUE`.
     */
    var variant: ButtonNavigationGroupVariant
        get() = variantState
        set(value) {
            variantState = value
        }

    /**
     * Texto personalizado del botón inicial. `null` utiliza `"Back"` o `"Skip"`
     * según [variant]. Una cadena vacía es un texto explícito y no activa el valor
     * predeterminado. Inicialmente `null` si XML no proporciona un texto.
     */
    var leadingLabel: String?
        get() = leadingLabelState
        set(value) {
            leadingLabelState = value
        }

    /**
     * Texto personalizado del botón final. `null` utiliza `"Continue"` o `"Submit"`
     * según [variant], o `"Loading"` si `isTrailingLoading` está activo.
     * Un texto explícito, incluso vacío, tiene prioridad sobre esos valores.
     * Inicialmente `null` si XML no proporciona un texto.
     */
    var trailingLabel: String?
        get() = trailingLabelState
        set(value) {
            trailingLabelState = value
        }

    /**
     * Ancho solicitado para el botón inicial, expresado en [Dp]. Por defecto usa
     * [ButtonNavigationGroupDefaults.LeadingButtonWidth] (`Dp.Unspecified`), que
     * delega el tamaño al componente hijo y sus restricciones de layout.
     */
    var leadingButtonWidth: Dp
        get() = leadingButtonWidthState
        set(value) {
            leadingButtonWidthState = value
        }

    /**
     * Ancho solicitado para el botón final, expresado en [Dp]. Por defecto usa
     * [ButtonNavigationGroupDefaults.TrailingButtonWidth] (`Dp.Unspecified`), que
     * delega el tamaño al componente hijo y sus restricciones de layout.
     */
    var trailingButtonWidth: Dp
        get() = trailingButtonWidthState
        set(value) {
            trailingButtonWidthState = value
        }

    /**
     * Separación entre botones, expresada en [Dp]. El valor predeterminado
     * [ButtonNavigationGroupDefaults.ButtonSpacing] (`Dp.Unspecified`) coloca los
     * botones hacia los extremos de la fila usando el espacio disponible.
     * Un valor explícito positivo fija la separación; `0.dp` une los botones y
     * colapsa el borde compartido. El resolver normaliza valores negativos a cero.
     */
    var buttonSpacing: Dp
        get() = buttonSpacingState
        set(value) {
            buttonSpacingState = value
        }

    /**
     * Estado habilitado solicitado para el botón inicial. Sin override devuelve
     * el estado del grupo. Asignar `true` o `false` establece un override persistente,
     * independiente de cambios posteriores en [setEnabled]. No representa el estado
     * de carga interno del botón.
     *
     * @see clearLeadingEnabledOverride
     */
    var leadingEnabled: Boolean
        get() = leadingEnabledOverrideState ?: enabledState
        set(value) {
            leadingEnabledOverrideState = value
        }

    /**
     * Estado habilitado solicitado para el botón final. Sin override devuelve
     * el estado del grupo. Asignar `true` o `false` establece un override persistente,
     * independiente de cambios posteriores en [setEnabled]. No representa el estado
     * de carga interno del botón.
     *
     * @see clearTrailingEnabledOverride
     */
    var trailingEnabled: Boolean
        get() = trailingEnabledOverrideState ?: enabledState
        set(value) {
            trailingEnabledOverrideState = value
        }

    /**
     * Comportamiento de carga del botón final. `isTrailingLoading` solicita mostrar
     * carga y `startsTrailingLoadingOnClick` solicita iniciarla al pulsarlo.
     * El wrapper transmite esta configuración al componente Compose; no ejecuta
     * operaciones de negocio. Sin configuración XML usa [ButtonNavigationGroupBehavior.Default],
     * con ambas opciones en `false`.
     */
    var buttonNavigationGroupBehavior: ButtonNavigationGroupBehavior
        get() = buttonNavigationGroupBehaviorState
        set(value) {
            buttonNavigationGroupBehaviorState = value
        }

    /**
     * Actualiza el estado habilitado de la vista Android y el estado del grupo en Compose.
     * Los botones sin override individual heredan este valor; los overrides existentes
     * se conservan y pueden mantener habilitado un botón aunque `enabled` sea `false`.
     *
     * @param enabled `true` para habilitar el grupo; `false` para deshabilitarlo.
     * @see clearLeadingEnabledOverride
     * @see clearTrailingEnabledOverride
     */
    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        enabledState = enabled
    }

    /**
     * Define la accion del boton inicial del grupo.
     *
     * Sustituye el listener específico anterior. Cuando existe, se invoca en lugar
     * de `performClick()`; no se ejecutan ambos mecanismos para el mismo evento.
     *
     * @param listener Acción sin argumentos ni valor de retorno. `null` elimina el
     * listener específico y restaura la delegación a `performClick()` de la vista.
     */
    fun setOnLeadingButtonClickListener(listener: (() -> Unit)?) {
        onLeadingButtonClickState = listener
    }

    /**
     * Define la accion del boton final del grupo.
     *
     * Sustituye el listener específico anterior. Cuando existe, se invoca en lugar
     * de `performClick()`; no se ejecutan ambos mecanismos para el mismo evento.
     *
     * @param listener Acción sin argumentos ni valor de retorno. `null` elimina el
     * listener específico y restaura la delegación a `performClick()` de la vista.
     */
    fun setOnTrailingButtonClickListener(listener: (() -> Unit)?) {
        onTrailingButtonClickState = listener
    }

    /**
     * Elimina el override y vuelve a heredar `isEnabled` desde el grupo.
     *
     * Restablece la herencia del botón inicial usando el estado del grupo en Compose.
     * A partir de esta llamada, los cambios mediante [setEnabled] afectan a este botón
     * hasta que se asigne un nuevo override individual.
     */
    fun clearLeadingEnabledOverride() {
        leadingEnabledOverrideState = null
    }

    /**
     * Elimina el override y vuelve a heredar `isEnabled` desde el grupo.
     *
     * Restablece la herencia del botón final usando el estado del grupo en Compose.
     * A partir de esta llamada, los cambios mediante [setEnabled] afectan a este botón
     * hasta que se asigne un nuevo override individual.
     */
    fun clearTrailingEnabledOverride() {
        trailingEnabledOverrideState = null
    }

    /**
     * Descripción:
     * Compone el grupo dentro del tema XML proporcionado por la clase base.
     * Es el punto de integración con Compose; el host configura la vista mediante
     * sus propiedades y listeners.
     */
    @Composable
    override fun Content() {
        WithXmlTheme {
            ButtonNavigationGroupContent()
        }
    }

    /**
     * Transmite los estados actuales y el modificador de la vista al componente Compose.
     * Resuelve cada estado habilitado individual dando prioridad al override y conecta
     * las acciones de los botones con los despachadores de esta vista.
     */
    @Composable
    private fun ButtonNavigationGroupContent() {
        ButtonNavigationGroupMrcy(
            modifier = modifierState,
            variant = variantState,
            onLeadingButtonClick = ::dispatchLeadingButtonClick,
            onTrailingButtonClick = ::dispatchTrailingButtonClick,
            leadingLabel = leadingLabelState,
            trailingLabel = trailingLabelState,
            leadingButtonWidth = leadingButtonWidthState,
            trailingButtonWidth = trailingButtonWidthState,
            buttonSpacing = buttonSpacingState,
            enabled = enabledState,
            leadingEnabled = leadingEnabledOverrideState ?: enabledState,
            trailingEnabled = trailingEnabledOverrideState ?: enabledState,
            buttonNavigationGroupBehavior = buttonNavigationGroupBehaviorState,
        )
    }

    /** Invoca el listener del botón inicial o, si es nulo, `performClick()` de la vista. */
    private fun dispatchLeadingButtonClick() {
        onLeadingButtonClickState?.invoke() ?: performClick()
    }

    /** Invoca el listener del botón final o, si es nulo, `performClick()` de la vista. */
    private fun dispatchTrailingButtonClick() {
        onTrailingButtonClickState?.invoke() ?: performClick()
    }
}
