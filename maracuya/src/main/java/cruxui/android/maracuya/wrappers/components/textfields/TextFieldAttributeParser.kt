package cruxui.android.maracuya.wrappers.components.textfields

import android.content.Context
import android.util.AttributeSet
import cruxui.android.maracuya.R
import cruxui.android.maracuya.ui.components.textfields.TextFieldVariant

/**
 * Resuelve los atributos XML de `TextFieldWrp` y los convierte en `TextFieldAttributes`.
 *
 * El parser no renderiza ni contiene estado mutable: únicamente traduce datos XML,
 * normaliza la variante y prepara el valor PIN cuando el layout lo define como texto.
 */
internal object TextFieldAttributeParser {

    private const val DEFAULT_VALUE = ""
    private const val DEFAULT_LABEL = "Label"
    private const val DEFAULT_PLACEHOLDER = "Placeholder"
    private const val DEFAULT_PIN_LENGTH = 4
    private const val RES_AUTO_NAMESPACE = "http://schemas.android.com/apk/res-auto"

    private const val ATTR_TEXT_FIELD_VARIANT = "textFieldVariant"
    private const val ATTR_TEXT_FIELD_LENGTH = "textFieldLength"

    private const val VARIANT_DEFAULT = "default"
    private const val VARIANT_USER = "user"
    private const val VARIANT_FACE_ID = "face_id"
    private const val VARIANT_PASSWORD = "password"
    private const val VARIANT_PIN = "pin"

    fun parse(context: Context, attrs: AttributeSet?): TextFieldAttributes {
        if (attrs == null) {
            return TextFieldAttributes(
                value = DEFAULT_VALUE,
                pinValue = emptyPin(DEFAULT_PIN_LENGTH),
                label = DEFAULT_LABEL,
                placeholder = DEFAULT_PLACEHOLDER,
                length = DEFAULT_PIN_LENGTH,
                enabled = true,
                readOnly = false,
                hasError = false,
                optionalText = false,
                showLabelIcon = false,
                bottomText = null,
                showBottomIcon = false,
                textFieldVariant = TextFieldVariant.DEFAULT,
                enableImplicitTrailingClear = true,
            )
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextFieldWrp)
        return try {
            val variant = attrs.getRawAttributeValue(ATTR_TEXT_FIELD_VARIANT)
                .toTextFieldVariant(
                    fallback = typedArray.getString(R.styleable.TextFieldWrp_textFieldVariant),
                )

            val length = attrs.getRawAttributeValue(ATTR_TEXT_FIELD_LENGTH)
                .toSupportedPinLength(
                    fallback = typedArray.getString(R.styleable.TextFieldWrp_textFieldLength),
                )
            val value = typedArray.getString(R.styleable.TextFieldWrp_textFieldValue) ?: DEFAULT_VALUE
            val pinValue = typedArray.getString(R.styleable.TextFieldWrp_textFieldPinValue)
                ?.toPinValue(length)
                ?: value.toPinValue(length)

            TextFieldAttributes(
                value = value,
                pinValue = pinValue,
                label = typedArray.getString(R.styleable.TextFieldWrp_textFieldLabel) ?: DEFAULT_LABEL,
                placeholder = typedArray.getString(R.styleable.TextFieldWrp_textFieldPlaceholder) ?: DEFAULT_PLACEHOLDER,
                length = length,
                enabled = typedArray.getBoolean(R.styleable.TextFieldWrp_isEnabled, true),
                readOnly = typedArray.getBoolean(R.styleable.TextFieldWrp_textFieldReadOnly, false),
                hasError = typedArray.getBoolean(R.styleable.TextFieldWrp_textFieldHasError, false),
                optionalText = typedArray.getBoolean(R.styleable.TextFieldWrp_optionalText, false),
                showLabelIcon = typedArray.getBoolean(R.styleable.TextFieldWrp_textFieldShowLabelIcon, false),
                bottomText = typedArray.getString(R.styleable.TextFieldWrp_textFieldBottomText),
                showBottomIcon = typedArray.getBoolean(R.styleable.TextFieldWrp_textFieldShowBottomIcon, false),
                textFieldVariant = variant,
                enableImplicitTrailingClear = typedArray.getBoolean(
                    R.styleable.TextFieldWrp_textFieldEnableImplicitTrailingClear,
                    true,
                ),
                textFieldTokensOverrideName = typedArray.getString(
                    R.styleable.TextFieldWrp_textFieldTokensOverride,
                ),
            )
        } finally {
            typedArray.recycle()
        }
    }

    /**
     * Convierte la variante XML a `TextFieldVariant` sin perder la variante PIN.
     *
     * Primero se intenta usar el valor crudo de `AttributeSet` porque conserva literales
     * como `pin` aun cuando Android Studio tenga cache viejo de `styleable`; si no existe,
     * se usa el valor resuelto por `TypedArray`. También se soportan nombres del enum de
     * Kotlin (`PIN`, `FACE_ID`) y valores numéricos (`0`-`4`) por compatibilidad.
     */
    private fun String?.toTextFieldVariant(fallback: String?): TextFieldVariant = when (normalizedXmlValue(fallback)) {
        VARIANT_USER, "text_field_user", "textfielduser", "1" -> TextFieldVariant.USER
        VARIANT_FACE_ID, "faceid", "text_field_face_id", "textfieldfaceid", "2" -> {
            TextFieldVariant.FACE_ID
        }
        VARIANT_PASSWORD, "text_field_password", "textfieldpassword", "3" -> TextFieldVariant.PASSWORD
        VARIANT_PIN, "text_field_pin", "textfieldpin", "4" -> TextFieldVariant.PIN
        VARIANT_DEFAULT, "text_field_default", "textfielddefault", "0", null, "" -> TextFieldVariant.DEFAULT
        else -> TextFieldVariant.DEFAULT
    }

    /** Convierte el `length` recibido por XML a un entero soportado por el PIN. */
    private fun String?.toSupportedPinLength(fallback: String?): Int = when (normalizedXmlValue(fallback)?.toIntOrNull()) {
        6 -> 6
        else -> DEFAULT_PIN_LENGTH
    }

    /** Obtiene un atributo `app:*` crudo antes de pasar por `TypedArray`. */
    private fun AttributeSet.getRawAttributeValue(name: String): String? = getAttributeValue(RES_AUTO_NAMESPACE, name)
        ?: getAttributeValue(null, name)

    /** Normaliza literales XML, referencias enum antiguas y nombres Kotlin del enum. */
    private fun String?.normalizedXmlValue(fallback: String?): String? = (this
        .takeUnless { it.isNullOrBlank() }
        ?: fallback?.takeUnless { it.isBlank() })
        ?.substringAfterLast('.')
        ?.substringAfterLast('/')
        ?.trim()
        ?.lowercase()
        ?.replace('-', '_')

    private fun String.toPinValue(length: Int): List<String> {
        val digits = filter(Char::isDigit).take(length).map { it.toString() }
        return digits + emptyPin(length - digits.size)
    }

    private fun emptyPin(length: Int): List<String> = List(length.coerceAtLeast(0)) { "" }
}
