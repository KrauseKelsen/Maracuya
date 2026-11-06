package exporter.output

import java.util.Locale
import cruxui.android.lib.tokens.base.ColorToken

/** Normaliza un nombre de tema para el contrato de estilos Android. */
internal fun styleName(name: String) = upper(name.replace(Regex("[^a-zA-Z0-9_]"), ""))
/** Conserva el nombre semántico elevando su inicial. */
internal fun upper(value: String) = value.replaceFirstChar { it.uppercaseChar() }
/** Convierte nombres del catálogo a identificadores de recursos estables. */
internal fun snake(value: String) = value.replace(Regex("([a-z0-9])([A-Z])"), "$1_$2").lowercase(Locale.ROOT).replace(Regex("[^a-z0-9_]"), "_")
/** Construye el nombre público del atributo de color. */
internal fun colorAttr(name: String) = "libraryColor${upper(name)}"
/** Construye el nombre público del atributo tipográfico. */
internal fun typeAttr(name: String) = "libraryTypography${upper(name)}"
/** Construye el nombre público del atributo de icono por grupo. */
internal fun iconAttr(group: String, name: String) = "libraryIcon${upper(group)}${upper(name)}"
/** Emite ARGB hexadecimal independiente de la configuración regional. */
internal fun hex(color: ColorToken) = "#%08X".format(Locale.ROOT, color.argb and 0xffffffffL)
