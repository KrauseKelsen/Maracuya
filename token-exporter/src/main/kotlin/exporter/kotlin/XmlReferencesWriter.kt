package exporter.kotlin

import exporter.catalog.*
import exporter.output.*

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * XmlReferencesWriter: Genera la API tipada de atributos Android organizada por familias semánticas.
 *
 * Justificación y uso:
 * Recibe ExportCatalog y ExportOutput. No descubre temas ni decide políticas de interacción.
 * Uso interno de construcción; el host consume los recursos del AAR.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object XmlReferencesWriter : CatalogWriter {
    /** Genera exclusivamente los recursos asignados a este escritor. */
    override fun write(catalog: ExportCatalog, output: ExportOutput) {
        val colorNames = catalog.colorNames
        val typographyNames = catalog.typographyNames
        val iconGroups = catalog.iconGroups
        val refs = buildString {
            appendLine("package cruxui.android.lib.theme")
            appendLine("import cruxui.android.lib.R")
            appendLine(typeDocumentation(
                "LibraryXmlReferences",
                "Agrupa los atributos XML generados por colores, tipografías, alturas de línea, fuentes e iconos.",
                "API pública del host. Todas las hojas son @AttrRes, no valores finales. Resolver con context.obtainStyledAttributes " +
                    "usando un Context con ThemeOverlay de la marca. Ejemplo: typography.headline24 devuelve el atributo cuyo valor es un TextAppearance. " +
                    "En XML usar android:textAppearance=\"?attr/libraryTypographyHeadline24\". ThemeExports.xmlReferences expone este mismo objeto. " +
                    "Cambiar el proveedor semántico no reinfla estilos Android ya aplicados.",
            ))
            appendLine("object LibraryXmlReferences {")
            appendLine(typeDocumentation("colors", "Atributos de los roles de color del catálogo.", "El host consulta colors.brandPrimary o usa ?attr/libraryColorBrandPrimary en XML. Resolver como color o ColorStateList en el tema Android activo.").prependIndent("    "))
            appendLine("    object colors {")
            for (name in colorNames) {
                appendLine("        /** Atributo del color $name; resolver contra el tema Android activo. */")
                appendLine("        @androidx.annotation.AttrRes val $name = R.attr.${colorAttr(name)}")
            }
            appendLine("    }")
            appendLine(typeDocumentation("typography", "Atributos de estilos tipográficos corporativos.", "El host consulta typography.headline24 y resuelve su valor como @StyleRes. En XML se usa android:textAppearance con el atributo semántico y un overlay de marca.").prependIndent("    "))
            appendLine("    object typography {")
            for (name in typographyNames) {
                appendLine("        /** Atributo cuyo valor es el TextAppearance de $name. */")
                appendLine("        @androidx.annotation.AttrRes val $name = R.attr.${typeAttr(name)}")
            }
            appendLine("    }")
            appendLine(typeDocumentation("lineHeight", "Atributos de alturas de línea de cada estilo tipográfico.", "Resolver como dimensión, no como ID de estilo. En XML puede usarse android:lineHeight=\"?attr/libraryLineHeightHeadline24\" con el overlay de marca.").prependIndent("    "))
            appendLine("    object lineHeight {")
            for (name in typographyNames) {
                appendLine("        /** Atributo de dimensión para la altura de línea de $name. */")
                appendLine("        @androidx.annotation.AttrRes val $name = R.attr.libraryLineHeight${upper(name)}")
            }
            appendLine("    }")
            appendLine(typeDocumentation("fonts", "Referencia a la familia tipográfica primaria del tema.",
                "El host resuelve fonts.primary como @FontRes o usa android:fontFamily=\"?attr/libraryFontPrimary\" en XML. La familia incluye pesos y cursivas del catálogo.").prependIndent("    "))
            appendLine("    object fonts {")
            appendLine("        /** Atributo de la familia primaria; requiere un Context con el overlay de marca. */")
            appendLine("        @androidx.annotation.AttrRes val primary = R.attr.libraryFontPrimary")
            appendLine("    }")
            iconReferences("icons", "", iconGroups, "Referencia drawable; usar android:src o android:drawableStart.")
            iconReferences("iconSizes", "Size", iconGroups, "Dimensión dp; resolver como dimensión o usar en layout_width/layout_height.")
            iconReferences("iconTints", "Tint", iconGroups, "Color opcional; null conserva la política del componente. Usar android:tint o android:drawableTint.")
            appendLine("}")
        }
        output.source("cruxui/android/lib/theme/LibraryXmlReferences.kt", refs)

    }
    /** Emite referencias paralelas de asset, tamaño y tinte sin duplicar la enumeración de categorías. */
    private fun StringBuilder.iconReferences(
        family: String,
        suffix: String,
        groups: Map<String, Map<String, cruxui.android.lib.tokens.base.IconToken>>,
        usage: String,
    ) {
        appendLine(typeDocumentation(family, "Atributos de iconos agrupados por categoría Library.",
            "API pública del host: $family.alerts.info. $usage Requiere el overlay de marca del Context.").prependIndent("    "))
        appendLine("    object $family {")
        for ((group, icons) in groups) {
            appendLine(typeDocumentation("$family.$group", "Referencias del grupo $group.",
                "Cada hoja es un @AttrRes. $usage").prependIndent("        "))
            appendLine("        object $group {")
            for (name in icons.keys) {
                appendLine("            /** Atributo de $group.$name. $usage */")
                appendLine("            @androidx.annotation.AttrRes val $name = R.attr.${iconAttr(group, name)}$suffix")
            }
            appendLine("        }")
        }
        appendLine("    }")
    }

}
