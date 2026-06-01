package com.example.mylibrary.wrappers.components.buttons.primary

import android.content.Context
import android.util.AttributeSet
import com.example.mylibrary.R

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `PrimaryButtonAttributeParser` resuelve atributos XML del wrapper `PrimaryButtonWrp`
 * y los transforma en un objeto tipado (`PrimaryButtonAttributes`).
 *
 * La clase encapsula la interacción con `TypedArray`, centralizando defaults y evitando
 * que el wrapper visual contenga detalles de infraestructura Android.
 *
 * Función:
 * - Leer `text`, `showProgressOnPress` e `isEnabled` desde XML.
 * - Aplicar defaults seguros cuando no existen attrs.
 * - Garantizar reciclado de `TypedArray` para evitar fugas.
 *
 * Relación con otras clases:
 * - `PrimaryButtonWrp` invoca `parse` durante `init`.
 * - `PrimaryButtonAttributes` modela el resultado final del parseo.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @see com.example.mylibrary.wrappers.components.buttons.primary.PrimaryButtonWrp
 */
internal object PrimaryButtonAttributeParser {

    private const val DEFAULT_TEXT = "Primary Button"

    fun parse(context: Context, attrs: AttributeSet?): PrimaryButtonAttributes {
        if (attrs == null) {
            return PrimaryButtonAttributes(
                text = DEFAULT_TEXT,
                showProgressOnPress = true,
                enabled = true,
            )
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PrimaryButtonWrp)
        return try {
            val text = typedArray.getString(R.styleable.PrimaryButtonWrp_text)
                ?: DEFAULT_TEXT

            val showProgressOnPress = typedArray.getBoolean(
                R.styleable.PrimaryButtonWrp_showProgressOnPress,
                true,
            )

            val enabled = typedArray.getBoolean(
                R.styleable.PrimaryButtonWrp_isEnabled,
                true,
            )

            PrimaryButtonAttributes(
                text = text,
                showProgressOnPress = showProgressOnPress,
                enabled = enabled,
            )
        } finally {
            typedArray.recycle()
        }
    }
}
