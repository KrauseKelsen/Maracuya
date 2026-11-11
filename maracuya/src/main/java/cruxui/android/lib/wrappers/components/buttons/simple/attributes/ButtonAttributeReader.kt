package cruxui.android.lib.wrappers.components.buttons.simple.attributes

import android.content.Context
import android.util.AttributeSet
import cruxui.android.lib.R
import cruxui.android.lib.core.components.buttons.tokens.ButtonLayoutTokens
import cruxui.android.lib.tokens.base.ColorToken
import cruxui.android.lib.wrappers.components.buttons.simple.behavior.ButtonBehavior
import cruxui.android.lib.wrappers.components.buttons.simple.tokens.ButtonTokensOverride
import cruxui.android.lib.wrappers.components.buttons.simple.variant.ButtonVariant
import cruxui.android.lib.wrappers.components.textviews.attributes.TextViewAttributeReader
import cruxui.android.lib.wrappers.components.textviews.tokens.TextViewNativeOverrides

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonAttributes conserva la configuración XML, incluidos estilos heredados del
 * TextView.
 *
 * Justificación: Transporta valores decodificados sin almacenar Context ni recursos abiertos.
 *
 * Se comunica con: ButtonAttributeReader lo construye; Button inicializa estado y renderer.
 *
 * Uso:
 * ```xml
 * <cruxui.android.lib.wrappers.components.buttons.simple.Button
 *     xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:app="http://schemas.android.com/apk/res-auto"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     android:text="Guardar"
 *     app:buttonVariant="primary"
 *     app:isLoading="false" />
 * ```
 *
 * En un layout existente se pueden conservar los namespaces de su raíz.
 *
 * Objetivos:
 * - Separar atributos de comportamiento y presentación.
 *
 * Flujo: XML y estilos Android → lectura propia/textual → configuración inicial de la View.
 *
 * @param text Etiqueta Android; conserva CharSequence y spans.
 * @param enabled Habilitación inicial, con android:enabled por encima del alias antiguo
 *   app:isEnabled.
 * @param variant Valor primary/secondary/terciary; no acepta una variante de hipertexto.
 * @param behavior Flags iniciales de carga y pulsación controlada.
 * @param recipe Nombre de receta registrada.
 * @param tokens Colores explícitos por estado.
 * @param textStyle Tipografía e iconos leídos mediante el lector del TextView existente.
 * @param radius Radio en píxeles, ya convertido por TypedArray.
 * @param maxLines Límite de líneas; uno por defecto.
 * @param iconStartDescription Descripción opcional inicial leída por TextViewAttributeReader.
 * @param iconEndDescription Descripción opcional final; no implica una acción independiente.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal data class ButtonAttributes(
    val text: CharSequence,
    val enabled: Boolean,
    val variant: ButtonVariant,
    val behavior: ButtonBehavior,
    val recipe: String?,
    val tokens: ButtonTokensOverride,
    val textStyle: TextViewNativeOverrides,
    val radius: Float,
    val maxLines: Int,
    val iconStartDescription: CharSequence?,
    val iconEndDescription: CharSequence?,
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonAttributeReader lee atributos propios y reutiliza el lector tipográfico de
 * TextView.
 *
 * Justificación: No duplica resolución de fuentes, tamaños ni drawables start/end. Recicla todos
 * los TypedArray.
 *
 * Se comunica con: DesignButton define los atributos; TextViewAttributeReader aporta las
 * capacidades textuales.
 *
 * Uso:
 * ```kotlin
 * // Dentro del módulo DS:
 * val initial = ButtonAttributeReader.read(context, attrs, defStyleAttr)
 * ```
 *
 * Objetivos:
 * - Respetar estilos Android y mantener una única lógica textual.
 *
 * Flujo: XML y estilos Android → lectura propia/textual → configuración inicial de la View.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
internal object ButtonAttributeReader {
    /**
     * @param context Context temático del layout.
     * @param attrs Atributos del nodo o null para construcción por código.
     * @param defStyleAttr Estilo del botón definido en el tema; cero omite el estilo implícito.
     * @return Configuración inmutable, sin TypedArray retenidos.
     */
    fun read(context: Context, attrs: AttributeSet?, defStyleAttr: Int): ButtonAttributes {
        val text = TextViewAttributeReader.read(context, attrs, defStyleAttr)
        val a = context.obtainStyledAttributes(attrs, R.styleable.DesignButton, defStyleAttr, 0)
        fun color(index: Int): ColorToken? =
            if (a.hasValue(index)) ColorToken(a.getColor(index, 0).toLong()) else null
        return try {
            ButtonAttributes(
                text =
                    a.getText(R.styleable.DesignButton_android_text)
                        ?: a.getText(R.styleable.DesignButton_text)
                        ?: "",
                enabled =
                    a.getBoolean(
                        R.styleable.DesignButton_android_enabled,
                        a.getBoolean(R.styleable.DesignButton_isEnabled, true),
                    ),
                variant =
                    when (a.getInt(R.styleable.DesignButton_buttonVariant, 0)) {
                        0 -> ButtonVariant.PRIMARY
                        1 -> ButtonVariant.SECONDARY
                        2 -> ButtonVariant.TERCIARY
                        else -> error("Unknown Button variant")
                    },
                behavior =
                    ButtonBehavior(
                        isLoading = a.getBoolean(R.styleable.DesignButton_isLoading, false),
                        isPressed = a.getBoolean(R.styleable.DesignButton_isPressed, false),
                    ),
                recipe = a.getString(R.styleable.DesignButton_buttonTokensOverride),
                tokens =
                    ButtonTokensOverride(
                        textColor = color(R.styleable.DesignButton_android_textColor),
                        background = color(R.styleable.DesignButton_buttonBackground),
                        border = color(R.styleable.DesignButton_buttonBorder),
                        pressedTextColor = color(R.styleable.DesignButton_pressedTextColor),
                        pressedBackground = color(R.styleable.DesignButton_pressedBackground),
                        pressedBorder = color(R.styleable.DesignButton_pressedBorder),
                        disabledTextColor = color(R.styleable.DesignButton_disabledTextColor),
                        disabledBackground = color(R.styleable.DesignButton_disabledBackground),
                        disabledBorder = color(R.styleable.DesignButton_disabledBorder),
                    ),
                textStyle =
                    // Button hereda métricas y fuentes de icono; su presencia depende de la fuente.
                    TextViewNativeOverrides(
                            iconStart = text.tokens.iconStart,
                            iconEnd = text.tokens.iconEnd,
                            iconPadding = text.tokens.iconPadding,
                        )
                        .withTextStyle(text.tokens.textStyle()),
                radius =
                    a.getDimension(
                        R.styleable.DesignButton_buttonCornerRadius,
                        ButtonLayoutTokens.CornerRadiusDp * context.resources.displayMetrics.density,
                    ),
                maxLines = a.getInt(R.styleable.DesignButton_android_maxLines, 1),
                iconStartDescription = text.iconStartDescription,
                iconEndDescription = text.iconEndDescription,
            )
        } finally {
            a.recycle()
        }
    }
}
