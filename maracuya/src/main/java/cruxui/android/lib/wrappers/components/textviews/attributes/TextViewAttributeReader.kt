package cruxui.android.lib.wrappers.components.textviews.attributes

import cruxui.android.lib.core.components.textviews.tokens.TextViewLayoutTokens
import android.content.Context
import android.content.res.TypedArray
import cruxui.android.lib.wrappers.capabilities.text.NativeTextStyleAttributeReader
import cruxui.android.lib.wrappers.capabilities.text.TextAttributeIndices
import android.util.AttributeSet
import cruxui.android.lib.R
import cruxui.android.lib.wrappers.components.textviews.tokens.*
import cruxui.android.lib.wrappers.core.tokens.*
import cruxui.android.lib.wrappers.core.attributes.*
import cruxui.android.lib.wrappers.components.textviews.tokens.TextViewNativeOverrides
import cruxui.android.lib.wrappers.components.textviews.variant.TextViewVariant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewAttributes` transporta la configuración inicial leída de XML y estilos Android.
 *
 * Justificación:
 * Mantiene separada la decodificación del layout de la construcción de estado y render.
 *
 * Se comunica con:
 * TextViewAttributeReader lo produce; el constructor de TextView inicializa estado, capacidades, descripciones y
 * la capa XML a partir de él.
 *
 * Uso:
 * Tipo interno del DS. El host declara los atributos del layout; no crea este objeto. Las fuentes nativas se
 * guardan en tokens para recuperar XML después de limpiar parches de ejecución.
 *
 * Objetivos:
 * - Transportar la configuración inicial.
 * - Conservar opciones funcionales y visuales sin mezclarlas.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param variant Variante leída de standard/hipertext.
 * @param error Estado inicial de error.
 * @param overrideName Nombre de receta XML.
 * @param selectable Solicitud inicial de selección.
 * @param iconStartDescription Descripción accesible inicial.
 * @param iconEndDescription Descripción accesible final.
 * @param tokens Capa visual XML decodificada.
 * @param includeFontPadding Padding de fuente explícito o default común con Compose.
 * @param minLines Mínimo de líneas; una por defecto.
 * @param maxLines Máximo de líneas; sin límite práctico por defecto.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal data class TextViewAttributes(
    val variant: TextViewVariant,
    val error: Boolean,
    val overrideName: String?,
    val selectable: Boolean,
    val iconStartDescription: CharSequence?,
    val iconEndDescription: CharSequence?,
    val tokens: TextViewNativeOverrides,
    val includeFontPadding: Boolean,
    val minLines: Int,
    val maxLines: Int,
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewAttributeReader` convierte atributos XML, estilos y TextAppearance en fuentes nativas tipadas.
 *
 * Justificación:
 * Deja a Android resolver la cascada de estilos y conserva la ausencia de cada atributo, necesaria para no
 * sobrescribir defaults del DS.
 *
 * Se comunica con:
 * Lee R.styleable.DesignTextView con los helpers de wrappers.core.attributes; produce TextViewAttributes y
 * TextViewNativeOverrides.
 *
 * Uso:
 * Objeto interno del DS. La View lo usa al construirse y al aplicar setTextAppearance.
 * El host configura XML, style o el atributo de tema textViewStyle. El lector recicla cada TypedArray que crea;
 * no evalúa recetas ni consulta locals.
 *
 * Objetivos:
 * - Respetar la cascada Android.
 * - Conservar unidades, selectores y ausencia.
 * - Separar lectura de render.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal object TextViewAttributeReader {
    private val textIndices = TextAttributeIndices(
        textStyle = R.styleable.DesignTextView_android_textStyle,
        fontFamily = R.styleable.DesignTextView_android_fontFamily,
        lineHeight = R.styleable.DesignTextView_android_lineHeight,
        lineSpacingExtra = R.styleable.DesignTextView_android_lineSpacingExtra,
        lineSpacingMultiplier = R.styleable.DesignTextView_android_lineSpacingMultiplier,
        textSize = R.styleable.DesignTextView_android_textSize,
        letterSpacing = R.styleable.DesignTextView_android_letterSpacing,
        weight = R.styleable.DesignTextView_android_textFontWeight,
        textAlignment = R.styleable.DesignTextView_android_textAlignment,
        gravity = R.styleable.DesignTextView_android_gravity,
        justificationMode = R.styleable.DesignTextView_android_justificationMode,
    )

    /**
     * Lee la configuración inicial y combina TextAppearance con los atributos superiores del componente.
     *
     * @param context Context temático que resuelve recursos.
     * @param attrs Atributos del layout o null.
     * @param defStyleAttr Atributo de estilo del componente; se usa Widget.Maracuya.TextView como respaldo.
     * @return Configuración inicial; recicla internamente su TypedArray.
     */
    fun read(context: Context, attrs: AttributeSet?, defStyleAttr: Int): TextViewAttributes {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.DesignTextView, defStyleAttr, R.style.Widget_Maracuya_TextView,
        )
        return try {
            val appearance = a.getResourceId(R.styleable.DesignTextView_android_textAppearance, 0)
            TextViewAttributes(
                variant = when (a.getInt(R.styleable.DesignTextView_variant, 0)) {
                    0 -> TextViewVariant.DEFAULT
                    1 -> TextViewVariant.HIPERTEXT
                    else -> error("Unknown TextView variant")
                },
                error = a.getBoolean(R.styleable.DesignTextView_error, false),
                overrideName = a.getString(R.styleable.DesignTextView_textViewTokensOverride),
                selectable = a.getBoolean(R.styleable.DesignTextView_android_textIsSelectable, false),
                iconStartDescription = a.getText(R.styleable.DesignTextView_iconStartContentDescription),
                iconEndDescription = a.getText(R.styleable.DesignTextView_iconEndContentDescription),
                includeFontPadding = a.getBoolean(R.styleable.DesignTextView_android_includeFontPadding, TextViewLayoutTokens.IncludeFontPadding),
                minLines = a.getInt(R.styleable.DesignTextView_android_minLines, TextViewLayoutTokens.MinLines),
                maxLines = a.getInt(R.styleable.DesignTextView_android_maxLines, TextViewLayoutTokens.MaxLines),
                tokens = (if (appearance != 0) readAppearance(context, appearance) else TextViewNativeOverrides())
                    .merge(readTokens(a)),
            )
        } finally { a.recycle() }
    }

    /**
     * Decodifica los campos visuales gestionados presentes en un estilo.
     *
     * @param context Context usado para resolver el estilo.
     * @param style ID del TextAppearance.
     * @return Parche parcial; los campos ausentes permanecen null.
     */
    fun readAppearance(context: Context, style: Int): TextViewNativeOverrides {
        val a = context.obtainStyledAttributes(style, R.styleable.DesignTextView)
        return try { readTokens(a) } finally { a.recycle() }
    }

    /**
     * Convierte un TypedArray ya resuelto en el parche nativo del componente. Uso privado.
     *
     * @param a Array del styleable DesignTextView; su propietario debe reciclarlo.
     * @return Fuentes de color, tipografía, párrafo, iconos y superficies. lineHeight prevalece si también hay
     * espaciado declarado.
     */
    private fun readTokens(a: TypedArray): TextViewNativeOverrides {
        return TextViewNativeOverrides(
            textColor = a.color(R.styleable.DesignTextView_android_textColor),
            background = a.surface(R.styleable.DesignTextView_android_background),
            foreground = a.surface(R.styleable.DesignTextView_android_foreground),
            foregroundError = a.color(R.styleable.DesignTextView_errorTextColor),
            foregroundDisabled = a.color(R.styleable.DesignTextView_disabledTextColor),
            iconStart = a.icon(R.styleable.DesignTextView_iconStart, R.styleable.DesignTextView_android_drawableStart),
            iconEnd = a.icon(R.styleable.DesignTextView_iconEnd, R.styleable.DesignTextView_android_drawableEnd),
            showIconStart = a.optionalBoolean(R.styleable.DesignTextView_showIconStart),
            showIconEnd = a.optionalBoolean(R.styleable.DesignTextView_showIconEnd),
            iconPadding = a.dimension(R.styleable.DesignTextView_iconPadding)
                ?: a.dimension(R.styleable.DesignTextView_android_drawablePadding),
        ).withTextStyle(NativeTextStyleAttributeReader.read(a, textIndices))
    }

}
