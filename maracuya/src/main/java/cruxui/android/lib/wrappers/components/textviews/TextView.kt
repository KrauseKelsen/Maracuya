package cruxui.android.lib.wrappers.components.textviews

import cruxui.android.lib.wrappers.core.resources.pixels
import cruxui.android.lib.wrappers.core.resources.resolve
import cruxui.android.lib.wrappers.capabilities.compounddrawables.IconCallbacks
import cruxui.android.lib.wrappers.capabilities.compounddrawables.InlineCompoundDrawableLayout
import cruxui.android.lib.wrappers.capabilities.text.NativeTextTouchTarget
import cruxui.android.lib.tokens.spacings.InteractionSpacings
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.appcompat.widget.AppCompatTextView
import cruxui.android.lib.R
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.tokens.base.FontFamilyToken
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.TypographyToken
import cruxui.android.lib.wrappers.components.textviews.attributes.TextViewAttributeReader
import cruxui.android.lib.wrappers.components.textviews.behavior.*
import cruxui.android.lib.wrappers.components.textviews.core.TextViewState
import cruxui.android.lib.wrappers.components.textviews.render.TextViewRenderer
import cruxui.android.lib.wrappers.components.textviews.tokens.*
import cruxui.android.lib.wrappers.components.textviews.tokensrender.TextViewTokensRenderResolver
import cruxui.android.lib.wrappers.components.textviews.variant.TextViewVariant
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableBehaviorController
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableSlot
import cruxui.android.lib.wrappers.core.behavior.ViewGestureController
import cruxui.android.lib.wrappers.core.theme.AndroidThemeAware
import cruxui.android.lib.wrappers.core.theme.AndroidThemeResolver
import cruxui.android.lib.wrappers.core.tokens.*
import kotlin.math.roundToInt

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextView` es el componente nativo del DS para layouts XML y código Android. Hereda de AppCompatTextView y
 * comparte el contrato semántico del TextView Compose.
 *
 * Justificación:
 * Permite consumir el tema existente sin alojar una composición dentro de cada View. Conserva APIs Android de
 * texto, spans y presentación, y adapta tokens, variantes, estados e interacciones al ciclo de vida nativo.
 *
 * Se comunica con:
 * Coordina TextViewAttributeReader, TextViewState, AndroidThemeResolver, TextViewTokensOverrideRegistry, los
 * resolutores de tokens/render/variante/comportamiento y TextViewRenderer. Los controladores compartidos
 * gestionan gestos y accesibilidad.
 *
 * Uso:
 * API pública del host Android. Usar el nombre completo de esta clase en XML para distinguirla de
 * android.widget.TextView y del composable homónimo.
 *
 * Ejemplo mínimo dentro de un proveedor de tema:
 * ```xml
 * <cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp
 *     xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:app="http://schemas.android.com/apk/res-auto"
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:themeName="Maracuya"
 *     app:themeStyle="auto">
 *     <cruxui.android.lib.wrappers.components.textviews.TextView
 *         android:id="@+id/title"
 *         android:layout_width="match_parent"
 *         android:layout_height="wrap_content"
 *         android:text="@string/section_title" />
 * </cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp>
 * ```
 * `section_title` pertenece a los recursos de la app. Si no hay proveedor, se resuelve Maracuya en AUTO. Un
 * proveedor anidado define un ámbito independiente.
 *
 * Receta tipográfica desde el registro existente, antes de inflar el layout:
 * ```kotlin
 * TextViewTokensOverrideRegistry.register("sectionTitle") { library: LibraryThemeSnapshot ->
 *     TextViewTokensOverride(
 *         typography = library.typography.headline24,
 *         fontFamily = library.fontFamily,
 *         textColor = library.colors.brandPrimary,
 *         iconEnd = library.icons.alerts.info,
 *     )
 * }
 * ```
 * Seleccionarla con `app:textViewTokensOverride="sectionTitle"`. No existe un atributo mrcyTypographyToken: la
 * tipografía es un campo del override. Registrar desde Application o desde la preparación de la pantalla antes
 * de inflate; no capturar Activities en recetas globales.
 *
 * Opciones XML del componente:
 * - `app:variant="standard|hipertext"`: normal o enlace subrayado; no modifica el texto ni abre URLs.
 * - `app:error="true"` y `android:enabled="false"`: estados independientes; deshabilitado tiene prioridad y
 *   bloquea interacción.
 * - `app:iconStart` / `app:iconEnd`: referencias drawable. También admite
 *   android:drawableStart/android:drawableEnd; el atributo app del mismo lado prevalece.
 * - `app:showIconStart` / `app:showIconEnd`: visibilidad del icono elegido; true no crea un icono cuando no hay
 *   ninguno.
 * - `app:iconPadding="8dp"`: separación; también acepta android:drawablePadding.
 * - `app:iconStartContentDescription` / `app:iconEndContentDescription`: textos localizados de las acciones
 *   accesibles. Son obligatorios antes de medir cuando el icono tiene acciones.
 * - `app:errorTextColor` / `app:disabledTextColor`: color o selector para ese estado.
 * - `android:textIsSelectable="true"`: selección y copia nativas.
 *
 * Opciones Android de estilo:
 * `android:text`, `textColor`, `textAppearance`, `fontFamily`, `textStyle`, `textFontWeight`, `textSize`,
 * `lineHeight`, `lineSpacingExtra`, `lineSpacingMultiplier`, `letterSpacing`, `textAlignment`, `gravity`,
 * `justificationMode`, `background` y `foreground` conservan su función nativa. Para tamaños usar sp;
 * letterSpacing usa em. maxLines, ellipsize, padding y las demás opciones heredadas se configuran como en
 * AppCompatTextView.
 *
 * También se pueden usar recursos del catálogo generado. Aplicar `android:theme="@style/ThemeOverlay.Maracuya"`
 * en el ámbito Android y declarar `android:textAppearance="?attr/libraryTypographyHeadline24"` o
 * `android:textColor="?attr/libraryColorBrandPrimary"`. El overlay y el proveedor semántico deben representar la
 * misma marca/modo. Cambiar el proveedor actualiza recetas, pero no reinfla estilos ni reemplaza el Context de
 * Views existentes; para referencias XML explícitas recrear o reinflar con el overlay correspondiente.
 *
 * Uso desde Kotlin:
 * ```kotlin
 * val title = findViewById<TextView>(R.id.title)
 * title.text = "Datos de contacto" // también conserva CharSequence con spans
 * title.setTokensOverrideName("sectionTitle")
 * title.setTypography(title.getThemeTokens().typography.body1)
 * title.setTextSizeSp(18f)
 * title.setOnClickListener { /* acción del texto */ }
 * title.setOnDoubleClickListener { /* doble toque */ }
 * title.setOnLongClickListener { /* pulsación larga */ true }
 * title.setOnIconEndClickListener { /* acción independiente */ }
 * title.setIconEndContentDescription(getString(R.string.more_information))
 * title.setBehavior(TextViewBehavior.Selectable)
 * ```
 * `more_information` se declara y traduce en el host. Los listeners no se declaran como campos del override. La
 * selección reserva long press y doble toque del texto y conserva los listeners para cuando se desactive. Los
 * iconos tienen acciones separadas y accesibles.
 *
 * Precedencia y limpieza:
 * 1) Setters/parche de ejecución.
 * 2) Atributos XML resueltos por Android, incluidos style y textAppearance.
 * 3) Receta registrada.
 * 4) Defaults del tema y variante.
 * `setTokensOverride` usa el contrato semántico compartido; `setNativeOverrides` acepta recursos, selectores,
 * Typeface y dimensiones Android. Se combinan campos presentes. Una tipografía nueva sustituye sus métricas
 * previas; un tamaño aplicado después ajusta solo ese valor.
 * `setTextColor(Int)` recibe ARGB; `setColor(Int)` recibe @ColorRes. El color nativo se aplica a todos los
 * estados, mientras TextViewTokensOverride.textColor configura el estado normal.
 * Para retirar un icono usar `setIconEnd(null as IconToken?)` o `ViewIcon.None`; null en un override semántico
 * significa conservar. Para restaurar XML/receta usar clearOverrides, clearColorOverride o
 * clearTypographyOverrides. `setTokensOverrideName(null)` retira la receta; `setThemeTokens(null)` restaura el
 * proveedor más cercano.
 *
 * Los defaults de layout proceden de TextViewLayoutTokens: separación de 8dp, celda interactiva de 48dp,
 * includeFontPadding=false y sin padding exterior añadido. Un icono decorativo ocupa su tamaño del token;
 * quitar su último callback restaura ese tamaño. Disabled conserva las celdas. Con match_parent, el icono
 * final sigue junto al texto; el sobrante no modifica los valores de android:padding del host.
 * El gesto del texto comienza en su bloque textual, ampliado al mínimo táctil; el espacio sobrante no activa
 * el listener. Los iconos interactivos tienen prioridad y no propagan su acción al texto.
 *
 * Los setters se llaman en el hilo de UI. Los callbacks y parches de ejecución no se serializan automáticamente:
 * el propietario de la pantalla los reaplica al recrearla. Una receta reemplazada requiere refreshTheme en
 * instancias existentes.
 *
 * Objetivos:
 * - Ofrecer uso nativo XML y Kotlin con el DS.
 * - Reutilizar contratos y tema Compose.
 * - Mantener estilos y eventos separados.
 * - Preservar personalizaciones explícitas al refrescar el tema.
 *
 * Flujo de construcción o ejecución:
 * 1) Leer XML y estilos.
 * 2) Resolver biblioteca y receta.
 * 3) Combinar capas.
 * 4) Adaptar y renderizar.
 * 5) Actualizar colores/gestos ante cambios de estado o tema.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param context Context Android usado para recursos, estilos y creación nativa.
 * @param attrs Atributos del layout; null en construcción programática.
 * @param defStyleAttr Atributo de estilo del componente, R.attr.textViewStyle por defecto.
 * @see cruxui.android.lib.wrappers.components.textviews.tokens.TextViewTokensOverrideRegistry
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride
 */
class TextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textViewStyle,
) : AppCompatTextView(context, attrs, defStyleAttr), AndroidThemeAware {

    private var ready = false
    private var applying = false
    private val initial = TextViewAttributeReader.read(context, attrs, defStyleAttr)
    private val state = TextViewState(initial.variant, initial.error, initial.overrideName)
    private var behavior = TextViewBehavior(initial.selectable)
    private var callbacks = TextViewCallbacks(
        iconStart = IconCallbacks(contentDescription = initial.iconStartDescription),
        iconEnd = IconCallbacks(contentDescription = initial.iconEndDescription),
    )
    private val tokenBinding = ComponentTokenBinding(TextViewTokensOverrideRegistry) { AndroidThemeResolver.resolve(this) }
    private val library: LibraryThemeSnapshot get() = tokenBinding.library
    private var resolved: TextViewResolvedTokens? = null
    private val inlineLayout = InlineCompoundDrawableLayout(this)
    private val renderer = TextViewRenderer { inlineLayout.endOffset }
    private val icons = CompoundDrawableBehaviorController(this, minimumTargetSizeDp = 0f)
    private val textTouchTarget = NativeTextTouchTarget(this, InteractionSpacings.MinimumTargetSizeDp)
    private val textGestures = ViewGestureController(this,
        active = { !behavior.textSelectionEnabled && callbacks.onDoubleClick != null },
        hasClick = { callbacks.onClick != null },
        doubleClick = ::performDoubleClick,
    )

    private var iconInteractionLayout = false to false

    init {
        includeFontPadding = initial.includeFontPadding
        minLines = initial.minLines
        maxLines = initial.maxLines
        gravity = initial.tokens.gravity ?: (android.view.Gravity.START or android.view.Gravity.CENTER_VERTICAL)
        ready = true
        syncBehavior()
        refreshDrawableState()
        renderTokens()
    }

    /** Reserva el sobrante de ancho al final del contenido, manteniendo el padding del host. */
    override fun getCompoundPaddingLeft(): Int = super.getCompoundPaddingLeft() +
        if (ready) inlineLayout.leftInset else 0

    /** Conserva el icono final junto al texto incluso con layout_width="match_parent". */
    override fun getCompoundPaddingRight(): Int = super.getCompoundPaddingRight() +
        if (ready) inlineLayout.rightInset else 0

    /** Mide primero el espacio disponible y después, si hay iconos, el bloque textual intrínseco. */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!ready) { super.onMeasure(widthMeasureSpec, heightMeasureSpec); return }
        TextViewBehaviorResolver.requireIconDescription(callbacks.iconStart, compoundDrawablesRelative[0] != null)
        TextViewBehaviorResolver.requireIconDescription(callbacks.iconEnd, compoundDrawablesRelative[2] != null)
        inlineLayout.reset()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (inlineLayout.resolve()) super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * Consulta la variante de esta instancia.
     * @return DEFAULT o HIPERTEXT.
     */
    fun getVariant(): TextViewVariant = state.variant
    /**
     * Cambia la presentación y vuelve a resolver la apariencia; conserva texto, callbacks y parches explícitos.
     * @param variant DEFAULT o HIPERTEXT.
     */
    fun setVariant(variant: TextViewVariant) { state.variant = variant; renderTokens() }

    /**
     * Consulta el nombre de receta seleccionado.
     * @return Nombre original configurado, o null si no hay selección.
     */
    fun getTokensOverrideName(): String? = state.overrideName
    /**
     * Selecciona una receta ya registrada y renderiza con el tema actual.
     * @param name Nombre de registro; null o vacío omite la receta.
     * @throws IllegalArgumentException Si el nombre no está registrado durante la ejecución normal.
     */
    fun setTokensOverrideName(name: String?) { state.overrideName = name; renderTokens() }

    /**
     * Aplica campos semánticos compartidos con Compose como un parche de ejecución.
     * Campos null conservan lo anterior. textColor afecta al estado normal; errorColor y disabledColor a sus
     * estados.
     * @param tokens Personalización parcial; usar clearOverrides para retirarla.
     */
    fun setTokensOverride(tokens: TextViewTokensOverride) = setNativeOverrides(tokens.toNativeOverrides())
    /**
     * Obtiene el parche de ejecución actual, sin incluir XML ni receta.
     * @return Configuración inmutable que el host puede inspeccionar o copiar.
     */
    fun getNativeOverrides(): TextViewNativeOverrides = state.runtime
    /**
     * Combina un parche Android sobre los valores explícitos actuales y renderiza.
     * Uso del host cuando necesita recursos, dimensiones, selectores o Typeface. Dentro de la nueva capa, los
     * valores específicos prevalecen sobre su tipografía.
     * @param tokens Campos presentes que se desean sustituir; null conserva campos anteriores.
     */
    fun setNativeOverrides(tokens: TextViewNativeOverrides) {
        state.runtime = state.runtime.merge(tokens)
        renderTokens()
    }
    /**
     * Retira todos los parches de ejecución y restaura XML, receta y defaults. Conserva el nombre de receta,
     * texto, tema explícito y listeners.
     */
    fun clearOverrides() { state.runtime = TextViewNativeOverrides(); renderTokens() }
    /**
     * Retira colores de texto de ejecución, incluidos los específicos de estado. Restaura los valores
     * inferiores; no elimina fondo ni foreground.
     */
    fun clearColorOverride() { state.runtime = state.runtime.withoutColors(); renderTokens() }
    /**
     * Retira tipografía, familia, métricas y opciones de párrafo del parche de ejecución. Restaura XML/receta;
     * conserva texto, colores, iconos y eventos.
     */
    fun clearTypographyOverrides() { state.runtime = state.runtime.withoutTypography(); renderTokens() }

    /**
     * Configura un color/selector Android por recurso para todos los estados.
     * @param resource ID @ColorRes; no pasar un entero ARGB. Para ARGB usar setTextColor.
     */
    fun setColor(@ColorRes resource: Int) = setColor(TextColor.Resource(resource))
    /**
     * Aplica un token DS como color nativo general. Su valor queda fijado hasta otro cambio explícito.
     * @param token Color aplicado a todos los estados; para seguir el tema usar una receta o TextColor.Semantic.
     */
    fun setColor(token: ColorToken) = setColor(TextColor.Token(token))
    /**
     * Aplica una fuente de color nativa y la conserva al resolver el tema.
     * @param source Recurso, selector, valor, rol o selección semántica que se resolverá con la biblioteca
     *     actual.
     */
    fun setColor(source: TextColor) = setNativeOverrides(TextViewNativeOverrides(textColor = source))
    /**
     * Selecciona un rol de color que se reevalúa contra el tema de la instancia.
     * @param role Rol corto de TextColorRole; para toda la paleta usar TextColor.Semantic.
     */
    fun setColorRole(role: TextColorRole) = setColor(TextColor.Role(role))
    /**
     * Configura la familia desde un recurso Android. Conserva peso/cursiva resueltos en el resto de capas.
     * @param resource ID @FontRes.
     */
    fun setFont(@FontRes resource: Int) = setNativeOverrides(TextViewNativeOverrides(font = TextFont.Resource(resource)))
    /**
     * Configura una familia corporativa utilizando su catálogo de pesos y cursivas.
     * @param family Familia DS explícita para esta instancia.
     */
    fun setFont(family: FontFamilyToken) = setNativeOverrides(TextViewNativeOverrides(font = TextFont.Family(family)))
    /**
     * Aplica un estilo semántico completo y sustituye las métricas explícitas anteriores. La familia es
     * independiente.
     * @param token Tipografía concreta; para seguir cambios de marca usar una receta registrada.
     */
    fun setTypography(token: TypographyToken) = setNativeOverrides(TextViewNativeOverrides(typography = TextTypography.Value(token)))
    /**
     * Selecciona un rol tipográfico que se resuelve contra la biblioteca actual.
     * @param role Cualquier rol generado desde LibraryTypographyTokens, por ejemplo Headline24Medium o LegalBold.
     */
    fun setTypography(role: TextTypographyRole) = setNativeOverrides(TextViewNativeOverrides(typography = TextTypography.Role(role)))
    /**
     * Configura solo el tamaño en sp; participa en el escalado de fuente del dispositivo.
     * @param size Tamaño finito y no negativo.
     */
    fun setTextSizeSp(size: Float) = setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    /**
     * Configura el icono inicial o lo retira explícitamente. La posición respeta la dirección de lectura.
     * @param icon Token del icono; null lo retira. No cambia listeners ni descripción.
     */
    fun setIconStart(icon: IconToken?) = setIconStart(icon?.let(ViewIcon::Token) ?: ViewIcon.None)
    /**
     * Configura el icono final o lo retira explícitamente. La posición respeta la dirección de lectura.
     * @param icon Token del icono; null lo retira. No cambia listeners ni descripción.
     */
    fun setIconEnd(icon: IconToken?) = setIconEnd(icon?.let(ViewIcon::Token) ?: ViewIcon.None)
    /**
     * Configura la fuente del icono inicial y actualiza su visibilidad.
     * @param icon Token, selección semántica o ViewIcon.None para retirarlo.
     */
    fun setIconStart(icon: ViewIcon) = setNativeOverrides(TextViewNativeOverrides(iconStart = icon, showIconStart = icon != ViewIcon.None))
    /**
     * Configura la fuente del icono final y actualiza su visibilidad.
     * @param icon Token, selección semántica o ViewIcon.None para retirarlo.
     */
    fun setIconEnd(icon: ViewIcon) = setNativeOverrides(TextViewNativeOverrides(iconEnd = icon, showIconEnd = icon != ViewIcon.None))
    /**
     * Muestra u oculta el icono inicial elegido sin cambiar su fuente.
     * @param show true lo muestra si existe; no inventa un icono predeterminado.
     */
    fun setShowIconStart(show: Boolean) = setNativeOverrides(TextViewNativeOverrides(showIconStart = show))
    /**
     * Muestra u oculta el icono final elegido sin cambiar su fuente.
     * @param show true lo muestra si existe; false lo oculta.
     */
    fun setShowIconEnd(show: Boolean) = setNativeOverrides(TextViewNativeOverrides(showIconEnd = show))

    /**
     * Inyecta una biblioteca por instancia y reevalúa receta y estilos.
     * @param theme Snapshot explícito; null restaura herencia del proveedor XML. No cambia recursos compilados
     *     ni el Context.
     */
    fun setThemeTokens(theme: LibraryThemeSnapshot?) { tokenBinding.explicitTheme = theme; refreshTheme() }
    /**
     * Obtiene la biblioteca resuelta de esta instancia.
     * @return Snapshot actual para consultar colores, tipografías, familia e iconos.
     */
    fun getThemeTokens(): LibraryThemeSnapshot = library

    /**
     * Consulta las capacidades configuradas.
     * @return TextViewBehavior solicitado, aunque la selección efectiva esté suspendida por disabled.
     */
    fun getBehavior(): TextViewBehavior = behavior
    /**
     * Sustituye las capacidades de selección, cancela gestos en curso y sincroniza listeners.
     * @param value Configuración completa; la selección reserva pulsación larga y doble toque del texto.
     */
    fun setBehavior(value: TextViewBehavior) {
        behavior = value
        textGestures.cancel()
        syncBehavior()
    }

    /**
     * Conecta la API nativa de selección con TextViewBehavior.
     * @param selectable true solicita selección/copia; los callbacks se conservan al alternar esta capacidad.
     */
    override fun setTextIsSelectable(selectable: Boolean) {
        if (!ready || applying) super.setTextIsSelectable(selectable)
        else setBehavior(behavior.copy(textSelectionEnabled = selectable))
    }

    /**
     * Sincroniza selección y listeners nativos con las capacidades configuradas.
     * Uso privado: no modifica los callbacks almacenados al reservar gestos para selección; aplica acciones de
     * iconos por separado.
     */
    private fun syncBehavior() {
        applying {
            super.setTextIsSelectable(isEnabled && behavior.textSelectionEnabled)
            super.setOnClickListener(callbacks.onClick)
            super.setOnLongClickListener(callbacks.onLongClick.takeUnless { behavior.textSelectionEnabled })
            if (!behavior.textSelectionEnabled) {
                isClickable = callbacks.onClick != null || callbacks.onDoubleClick != null
                isLongClickable = callbacks.onLongClick != null
            }
        }
        for (slot in CompoundDrawableSlot.entries) {
            val actions = TextViewBehaviorResolver.icon(callbacks, slot)
            icons.setOnClickListener(slot, actions.onClick, actions.contentDescription)
            icons.setOnLongClickListener(slot, actions.onLongClick, actions.contentDescription)
        }
        val next = (callbacks.iconStart.onClick != null || callbacks.iconStart.onLongClick != null) to
            (callbacks.iconEnd.onClick != null || callbacks.iconEnd.onLongClick != null)
        if (next != iconInteractionLayout) {
            iconInteractionLayout = next
            renderTokens()
        }
    }

    /**
     * Asigna la acción sencilla del texto mediante la API Android.
     * @param listener Callback, o null para retirarlo. No configura acciones de iconos.
     */
    override fun setOnClickListener(listener: OnClickListener?) {
        if (!ready) { super.setOnClickListener(listener); return }
        callbacks = callbacks.copy(onClick = listener)
        syncBehavior()
    }
    /**
     * Asigna la acción larga del texto. Se conserva, pero no se ejecuta como gesto personalizado durante
     * selección.
     * @param listener Callback que devuelve si consumió la acción, o null para retirarlo.
     */
    override fun setOnLongClickListener(listener: OnLongClickListener?) {
        if (!ready) { super.setOnLongClickListener(listener); return }
        callbacks = callbacks.copy(onLongClick = listener)
        syncBehavior()
    }
    /**
     * Configura doble toque del texto y cancela el gesto pendiente.
     * Si es la única acción de pulsación, la activación accesible también ejecuta este callback.
     * @param listener Acción de doble toque, o null para retirarla.
     */
    fun setOnDoubleClickListener(listener: OnClickListener?) {
        textGestures.cancel()
        callbacks = callbacks.copy(onDoubleClick = listener)
        syncBehavior()
    }
    /**
     * Configura la acción sencilla del icono inicial, independiente del texto.
     * @param listener Callback o null. Declarar también una descripción localizada para accesibilidad.
     */
    fun setOnIconStartClickListener(listener: OnClickListener?) { callbacks = callbacks.copy(iconStart = callbacks.iconStart.copy(onClick = listener)); syncBehavior() }
    /**
     * Configura la acción sencilla del icono final, independiente del texto.
     * @param listener Callback o null. Declarar también una descripción localizada para accesibilidad.
     */
    fun setOnIconEndClickListener(listener: OnClickListener?) { callbacks = callbacks.copy(iconEnd = callbacks.iconEnd.copy(onClick = listener)); syncBehavior() }
    /**
     * Configura la pulsación larga del icono inicial.
     * @param listener Callback que devuelve true si consume la acción; null la retira.
     */
    fun setOnIconStartLongClickListener(listener: OnLongClickListener?) { callbacks = callbacks.copy(iconStart = callbacks.iconStart.copy(onLongClick = listener)); syncBehavior() }
    /**
     * Configura la pulsación larga del icono final.
     * @param listener Callback que devuelve true si consume la acción; null la retira.
     */
    fun setOnIconEndLongClickListener(listener: OnLongClickListener?) { callbacks = callbacks.copy(iconEnd = callbacks.iconEnd.copy(onLongClick = listener)); syncBehavior() }
    /**
     * Configura la descripción de las acciones accesibles del icono inicial.
     * @param description Texto localizado obligatorio para un icono con acciones; null solo para decorativos.
     */
    fun setIconStartContentDescription(description: CharSequence?) { callbacks = callbacks.copy(iconStart = callbacks.iconStart.copy(contentDescription = description)); syncBehavior() }
    /**
     * Configura la descripción de las acciones accesibles del icono final.
     * @param description Texto localizado obligatorio para un icono con acciones; null solo para decorativos.
     */
    fun setIconEndContentDescription(description: CharSequence?) { callbacks = callbacks.copy(iconEnd = callbacks.iconEnd.copy(contentDescription = description)); syncBehavior() }
    /**
     * Solicita la acción sencilla del icono inicial.
     * @return true si se activó su listener; false si no hay acción, el icono no está disponible o la View está
     * deshabilitada.
     */
    fun performIconStartClick() = icons.performClick(CompoundDrawableSlot.Start)
    /**
     * Solicita la acción sencilla del icono final.
     * @return true si se activó su listener; false si no hay acción, el icono no está disponible o la View está
     * deshabilitada.
     */
    fun performIconEndClick() = icons.performClick(CompoundDrawableSlot.End)
    /**
     * Solicita la acción larga del icono inicial.
     * @return Resultado de su listener; false si no hay acción disponible o la View está deshabilitada.
     */
    fun performIconStartLongClick() = icons.performLongClick(CompoundDrawableSlot.Start)
    /**
     * Solicita la acción larga del icono final.
     * @return Resultado de su listener; false si no hay acción disponible o la View está deshabilitada.
     */
    fun performIconEndLongClick() = icons.performLongClick(CompoundDrawableSlot.End)

    /**
     * Solicita programáticamente la acción de doble toque del texto.
     * @return true si se ejecutó un callback; false si falta, está deshabilitado o la selección reserva el
     * gesto.
     */
    fun performDoubleClick(): Boolean {
        if (!TextViewBehaviorResolver.customTextGestures(behavior, isEnabled)) return false
        val listener = callbacks.onDoubleClick ?: return false
        listener.onClick(this)
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED)
        return true
    }

    /**
     * Activa el texto con las reglas nativas; si solo hay callback doble, usa ese callback como activación
     * accesible.
     * @return Resultado de la activación; false cuando está deshabilitado.
     */
    override fun performClick(): Boolean {
        if (!ready) return super.performClick()
        if (!isEnabled) return false
        return if (callbacks.onClick == null && callbacks.onDoubleClick != null && !behavior.textSelectionEnabled)
            performDoubleClick() else super.performClick()
    }
    /**
     * Invoca directamente el listener nativo de click cuando está habilitado. No sustituye una acción ausente
     * por doble toque.
     * @return Resultado nativo, o false si está deshabilitado.
     */
    override fun callOnClick(): Boolean = if (!ready || isEnabled) super.callOnClick() else false
    /**
     * Solicita la acción larga nativa cuando está habilitado. La selección puede manejarla sin invocar el
     * callback personalizado.
     * @return Resultado nativo, o false si está deshabilitado.
     */
    override fun performLongClick(): Boolean = if (!ready || isEnabled) super.performLongClick() else false

    /**
     * Distribuye eventos primero a iconos, después al controlador de doble toque y por último al TextView
     * nativo.
     * Uso del ciclo Android; el host normalmente configura listeners, no llama este método.
     * @param event Evento táctil actual.
     * @return true si alguno de los controladores o el componente nativo consume el evento.
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!ready) return super.onTouchEvent(event)
        if (icons.onTouchEvent(event)) return true
        if (event.actionMasked == MotionEvent.ACTION_DOWN && !textTouchTarget.contains(event.x, event.y)) return false
        if (textGestures.onTouchEvent(event)) return true
        return super.onTouchEvent(event)
    }

    /**
     * Completa el nodo accesible con acciones de iconos, error y habilitación.
     * Uso del framework Android; elimina acciones de activación cuando está deshabilitado.
     * @param info Nodo que se está construyendo.
     */
    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        if (!ready) return
        icons.onInitializeAccessibilityNodeInfo(info)
        info.isContentInvalid = state.error
        if (state.error && info.error.isNullOrEmpty()) info.error = context.getString(R.string.text_view_error)
        if (isEnabled && callbacks.onDoubleClick != null && callbacks.onClick == null && !behavior.textSelectionEnabled) {
            info.isClickable = true
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK)
        }
        if (!isEnabled) {
            info.isClickable = false
            info.isLongClickable = false
            info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK)
            info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK)
        }
    }
    /**
     * Despacha una acción accesible a los iconos o a la implementación nativa.
     * @param action ID de acción estándar o de icono.
     * @param arguments Argumentos del servicio, si existen.
     * @return true si alguna implementación consumió la acción.
     */
    override fun performAccessibilityAction(action: Int, arguments: Bundle?): Boolean =
        (ready && icons.performAccessibilityAction(action, arguments)) || super.performAccessibilityAction(action, arguments)

    /**
     * Consulta el estado visual de error del DS.
     * @return true cuando error está activo; es independiente del estado enabled.
     */
    fun hasError(): Boolean = state.error
    /**
     * Cambia el estado de error y notifica la apariencia/accesibilidad.
     * @param error true activa el estado; false también limpia un error nativo presente.
     */
    fun setErrorState(error: Boolean) {
        if (state.error == error) return
        state.error = error
        if (!error && getError() != null) super.setError(null, null)
        refreshDrawableState()
        sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)
    }
    /**
     * Conecta el mensaje e icono de error nativos con el estado DS.
     * @param error Mensaje de error; null desactiva el estado.
     * @param icon Icono gestionado por la API nativa de error, distinto de los tokens start/end.
     */
    override fun setError(error: CharSequence?, icon: Drawable?) {
        super.setError(error, icon)
        if (ready) setErrorState(error != null)
    }
    /**
     * Actualiza la habilitación y sincroniza selección, gestos y colores.
     * @param enabled false cancela gestos pendientes y bloquea acciones; conserva su configuración.
     */
    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (ready) {
            if (!enabled) { textGestures.cancel(); icons.cancel() }
            syncBehavior()
            renderColors()
        }
    }
    /**
     * Agrega state_error al estado drawable cuando corresponde. Uso del framework.
     * @param extraSpace Espacio adicional solicitado por la superclase o el llamador.
     * @return Array nativo de estados que pueden consumir los selectores de color.
     */
    override fun onCreateDrawableState(extraSpace: Int): IntArray = super.onCreateDrawableState(extraSpace + 1).also {
        if (ready && state.error) mergeDrawableStates(it, intArrayOf(R.attr.state_error))
    }
    /**
     * Reaplica colores y notifica al controlador de iconos cuando cambia el estado drawable.
     * Uso del ciclo nativo; evita recursión durante la aplicación del renderer.
     */
    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if (ready && !applying) renderColors()
    }

    /**
     * Configura un ARGB nativo como color general y lo conserva ante cambios de tema o variante.
     * @param color Entero @ColorInt; no un ID de recurso. Para recursos usar setColor.
     */
    override fun setTextColor(color: Int) {
        if (!ready || applying) super.setTextColor(color) else setColor(TextColor.Value(color))
    }
    /**
     * Configura un selector nativo que conserva estados pressed, disabled y state_error declarados por el host.
     * @param colors ColorStateList aplicado a todos los estados según sus reglas.
     */
    override fun setTextColor(colors: ColorStateList) {
        if (!ready || applying) super.setTextColor(colors) else setColor(TextColor.States(colors))
    }
    /**
     * Configura el tamaño conservando su unidad original para futuros cambios de configuración.
     * @param unit Unidad TypedValue, por ejemplo COMPLEX_UNIT_SP o COMPLEX_UNIT_PX.
     * @param size Tamaño finito y no negativo en la unidad indicada.
     */
    override fun setTextSize(unit: Int, size: Float) {
        if (!ready || applying) super.setTextSize(unit, size)
        else setNativeOverrides(TextViewNativeOverrides(textSize = TextDimension(size, unit)))
    }
    /**
     * Configura una Typeface concreta y conserva su peso y cursiva.
     * @param tf Fuente nativa; null usa Typeface.DEFAULT.
     */
    override fun setTypeface(tf: Typeface?) {
        if (!ready || applying) super.setTypeface(tf)
        else setNativeOverrides(TextViewNativeOverrides(font = TextFont.Value(tf ?: Typeface.DEFAULT),
            weight = (tf ?: Typeface.DEFAULT).weight, italic = tf?.isItalic ?: false))
    }
    /**
     * Configura una Typeface y el estilo NORMAL/BOLD/ITALIC/BOLD_ITALIC solicitado.
     * @param tf Familia nativa; null usa Typeface.DEFAULT.
     * @param style Máscara de estilo Typeface; se traduce a peso y cursiva del parche.
     */
    override fun setTypeface(tf: Typeface?, style: Int) {
        if (!ready || applying) super.setTypeface(tf, style)
        else setNativeOverrides(TextViewNativeOverrides(font = TextFont.Value(tf ?: Typeface.DEFAULT),
            weight = if (style and Typeface.BOLD != 0) 700 else 400, italic = style and Typeface.ITALIC != 0))
    }
    /**
     * Configura tracking nativo en em y lo conserva al resolver el tema.
     * @param letterSpacing Separación finita; no se expresa en sp como el token semántico.
     */
    override fun setLetterSpacing(letterSpacing: Float) {
        if (!ready || applying) super.setLetterSpacing(letterSpacing)
        else setNativeOverrides(TextViewNativeOverrides(letterSpacingEm = letterSpacing))
    }
    /**
     * Configura alineación nativa explícita.
     * @param textAlignment Constante View.TEXT_ALIGNMENT_*; su interpretación depende también de la
     *     compatibilidad RTL de Android.
     */
    override fun setTextAlignment(textAlignment: Int) {
        if (!ready || applying) super.setTextAlignment(textAlignment)
        else setNativeOverrides(TextViewNativeOverrides(textAlignment = textAlignment))
    }
    /**
     * Configura gravedad del texto y selecciona TEXT_ALIGNMENT_GRAVITY.
     * @param gravity Combinación Gravity, por ejemplo END or CENTER_VERTICAL.
     */
    override fun setGravity(gravity: Int) {
        if (!ready || applying) super.setGravity(gravity)
        else setNativeOverrides(TextViewNativeOverrides(gravity = gravity, textAlignment = TEXT_ALIGNMENT_GRAVITY))
    }
    /**
     * Configura el modo nativo de justificación del párrafo.
     * @param justificationMode Constante admitida por TextView para la versión Android del host.
     */
    override fun setJustificationMode(justificationMode: Int) {
        if (!ready || applying) super.setJustificationMode(justificationMode)
        else setNativeOverrides(TextViewNativeOverrides(justificationMode = justificationMode))
    }
    /**
     * Configura la altura de línea en píxeles y retira el parche de lineSpacing anterior.
     * @param lineHeight Altura no negativa en px.
     */
    override fun setLineHeight(lineHeight: Int) {
        if (!ready || applying) super.setLineHeight(lineHeight)
        else setNativeOverrides(TextViewNativeOverrides(lineHeight = TextDimension(lineHeight.toFloat(), TypedValue.COMPLEX_UNIT_PX)))
    }
    /**
     * Configura altura de línea conservando su unidad; adapta la llamada para Android anteriores a API 34.
     * @param unit Unidad TypedValue.
     * @param lineHeight Altura finita y no negativa. Sustituye el parche de lineSpacing.
     */
    override fun setLineHeight(unit: Int, lineHeight: Float) {
        if (!ready || applying) {
            if (Build.VERSION.SDK_INT >= 34) super.setLineHeight(unit, lineHeight)
            else super.setLineHeight(TextDimension(lineHeight, unit).pixels(context).roundToInt())
        } else setNativeOverrides(TextViewNativeOverrides(lineHeight = TextDimension(lineHeight, unit)))
    }
    /**
     * Configura el espaciado nativo y retira una altura de línea explícita previa.
     * @param add Espacio adicional por línea en píxeles; puede ser negativo.
     * @param mult Multiplicador finito y no negativo de la altura de fuente.
     */
    override fun setLineSpacing(add: Float, mult: Float) {
        if (!ready || applying) super.setLineSpacing(add, mult)
        else setNativeOverrides(TextViewNativeOverrides(lineSpacing = TextLineSpacing(add, mult)))
    }
    /** Configura la separación nativa en píxeles y la conserva al refrescar tokens o tema. */
    override fun setCompoundDrawablePadding(pad: Int) {
        if (!ready || applying) super.setCompoundDrawablePadding(pad)
        else setNativeOverrides(TextViewNativeOverrides(iconPadding = TextDimension(pad.toFloat(), TypedValue.COMPLEX_UNIT_PX)))
    }

    /**
     * Configura el drawable de fondo como parche nativo persistente.
     * @param background Drawable, o null para retirar el fondo explícitamente.
     */
    override fun setBackground(background: Drawable?) {
        if (!ready || applying) super.setBackground(background)
        else setNativeOverrides(TextViewNativeOverrides(background = ViewSurface.Value(background)))
    }
    /**
     * Configura un ARGB de fondo para esta instancia.
     * @param color Entero de color nativo, no ID de recurso.
     */
    override fun setBackgroundColor(color: Int) {
        if (!ready || applying) super.setBackgroundColor(color)
        else setNativeOverrides(TextViewNativeOverrides(background = ViewSurface.Color(TextColor.Value(color))))
    }
    /**
     * Configura el fondo conservando la identidad del recurso.
     * @param resid ID de recurso drawable/color; cero retira el fondo.
     */
    override fun setBackgroundResource(resid: Int) {
        if (!ready || applying) super.setBackgroundResource(resid)
        else setNativeOverrides(TextViewNativeOverrides(background = if (resid == 0) ViewSurface.Value(null) else ViewSurface.Resource(resid)))
    }
    /**
     * Configura una capa drawable sobre el contenido.
     * @param foreground Drawable, o null para retirarlo explícitamente.
     */
    override fun setForeground(foreground: Drawable?) {
        if (!ready || applying) super.setForeground(foreground)
        else setNativeOverrides(TextViewNativeOverrides(foreground = ViewSurface.Value(foreground)))
    }
    /**
     * Aplica un TextAppearance con el Context de la View y registra sus campos presentes como parche.
     * @param resId ID @StyleRes, por ejemplo un TextAppearance generado del DS.
     */
    override fun setTextAppearance(resId: Int) = setTextAppearance(context, resId)
    /**
     * Aplica la API nativa de TextAppearance y decodifica los campos gestionados para conservarlos en futuros
     * renders.
     * @param context Context utilizado para resolver el estilo.
     * @param resId ID del TextAppearance; sus campos ausentes no eliminan otros parches.
     */
    @Suppress("DEPRECATION")
    override fun setTextAppearance(context: Context, resId: Int) {
        if (!ready || applying) { super.setTextAppearance(context, resId); return }
        applying { super.setTextAppearance(context, resId) }
        setNativeOverrides(TextViewAttributeReader.readAppearance(context, resId))
    }

    /**
     * Al adjuntarse, reevalúa el proveedor XML más cercano y su receta. Uso del ciclo Android; el padre puede no
     * estar disponible durante el constructor.
     */
    override fun onAttachedToWindow() { super.onAttachedToWindow(); refreshTheme() }
    /**
     * Cancela gestos pendientes de texto e iconos antes de separarse de la ventana. Uso del ciclo Android.
     */
    override fun onDetachedFromWindow() { textGestures.cancel(); icons.cancel(); super.onDetachedFromWindow() }
    /**
     * Delega el cambio nativo y vuelve a resolver el tema y las dimensiones.
     * @param newConfig Configuración Android actualizada, incluido modo o escalado de fuentes.
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        applying { super.onConfigurationChanged(newConfig) }
        refreshTheme()
    }
    /**
     * Reevalúa la biblioteca heredada o explícita y vuelve a resolver la receta.
     * El proveedor la invoca al actualizar su ámbito; el host puede llamarla después de reemplazar una receta
     * registrada. No reinfla estilos ni modifica el Context.
     */
    override fun refreshTheme() {
        if (!ready) return
        tokenBinding.refresh()
        renderTokens()
    }
    /**
     * Resuelve receta, XML y parche actual; adapta métricas e iconos y solicita el render de colores.
     * Uso privado. En el editor de layouts omite recetas desconocidas para permitir previsualización sin
     * Application.
     */
    private fun renderTokens() {
        if (!ready || applying) return
        val recipe = tokenBinding.recipe(state.overrideName, preview = isInEditMode)
        val next = TextViewTokensResolver.resolve(library, state.variant, recipe, initial.tokens, state.runtime)
        resolved = next
        applying { renderer.render(this, TextViewTokensRenderResolver.resolve(context, library, next), state.variant,
            iconInteractionLayout.first, iconInteractionLayout.second) }
        renderColors()
    }
    /**
     * Resuelve los colores del estado actual y actualiza las superficies e iconos. Uso privado; no reconstruye
     * las métricas del texto.
     */
    private fun renderColors() {
        if (!ready || applying) return
        val tokens = resolved ?: return
        val colors = TextViewTokensRenderResolver.color(context, library, tokens, isEnabled, state.error, drawableState)
        applying { renderer.color(this, colors) }
        icons.onDrawableStateChanged()
    }
    /**
     * Ejecuta una escritura interna protegida contra los setters virtuales que registrarían otro parche.
     * Uso privado; restaura el indicador incluso si el renderer lanza una excepción.
     * @param action Escrituras internas sobre la View.
     */
    private inline fun applying(action: () -> Unit) {
        val previous = applying
        applying = true
        try { action() } finally { applying = previous }
    }
}
