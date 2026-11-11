package cruxui.android.lib.wrappers.components.buttons.simple

import android.content.Context
import android.util.AttributeSet

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonWrp conserva el nombre XML anterior y hereda el Button nativo.
 *
 * Justificación: Permite migrar layouts gradualmente sin alojar Compose ni duplicar lógica.
 *
 * Se comunica con: Todas sus capacidades se implementan en Button; las APIs antiguas específicas de
 * Compose se retiran.
 *
 * Uso:
 * ```kotlin
 * val button = ButtonWrp(context)
 * button.text = "Continuar"
 * button.setOnClickListener { button.isLoading = true }
 * ```
 *
 * Para layouts nuevos utilizar cruxui.android.lib.wrappers.components.buttons.simple.Button.
 * showProgressOnPress deja de existir; la carga se controla explícitamente con isLoading.
 *
 * Objetivos:
 * - Conservar la ruta XML sin conservar la implementación anterior.
 *
 * Flujo: Entrada pública → tokens y behavior → tokensrender → representación de plataforma.
 *
 * @param context Context temático.
 * @param attrs Atributos iniciales.
 * @param defStyleAttr Estilo opcional del botón.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
@Deprecated("Use the native Button", ReplaceWith("Button(context, attrs, defStyleAttr)"))
class ButtonWrp
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    Button(context, attrs, defStyleAttr)
