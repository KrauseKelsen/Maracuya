package com.example.mylibrary.wrappers.theme

import com.example.mylibrary.theme.ThemeStyle

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `MyLibraryThemeConfig` es el contrato interno que concentra la configuración efectiva
 * del host de tema XML (`MyLibraryThemeWrp`) para toda una jerarquía de Views.
 *
 * Su propósito es evitar que cada wrapper de componente (botones, inputs, etc.) tenga
 * que leer y resolver atributos de tema por separado. En su lugar, el host parsea una vez
 * y expone esta estructura inmutable para consumo consistente.
 *
 * Función:
 * - Consolidar los parámetros de theming recibidos desde XML.
 * - Mantener un estado único y explícito del tema activo por jerarquía.
 * - Servir de puente hacia `MyLibraryTheme` desde wrappers basados en `AbstractComposeView`.
 *
 * Relación con otras clases:
 * - `MyLibraryThemeAttributeParser` crea esta configuración desde `AttributeSet`.
 * - `MyLibraryThemeWrp` guarda una instancia mutable de esta data class.
 * - `PrimaryButtonWrp` consume esta config (vía host) para aplicar `MyLibraryTheme`.
 *
 * No está pensada para uso directo por el host app.
 * Debe mantenerse como detalle interno del módulo wrappers.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @param themeName Nombre lógico del tema (ej: `Maracuya`) que se resolverá mediante
 *                  `LibraryThemes.fromName`.
 * @param themeStyle Modo visual solicitado (`LIGHT`, `DARK`, `AUTO`).
 * @param useMaterial Indica si debe inyectarse también `MaterialTheme` además de los
 *                    tokens semánticos del DS.
 *
 * @see com.example.mylibrary.wrappers.theme.MyLibraryThemeWrp
 */
internal data class MyLibraryThemeConfig(
    val themeName: String,
    val themeStyle: ThemeStyle,
    val useMaterial: Boolean,
)
