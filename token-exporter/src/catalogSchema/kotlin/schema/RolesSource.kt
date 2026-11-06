package schema

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * RolesSource genera identificadores tipados para cualquier familia del catálogo.
 *
 * Justificación:
 * Un rol guarda una ruta semántica, nunca un valor de marca. Colores, tipografías e iconos usan el mismo
 * mecanismo y se resuelven contra la colección activa en Compose o en un override nativo.
 *
 * Uso:
 * Escritor interno. Recibe pares nombre público/ruta y el manual específico de la familia.
 * Los alias históricos se añaden por el punto de composición, sin condicionales por componente aquí.
 *
 * Objetivos:
 * - Generar APIs extensibles sin repetir implementaciones de enums.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object RolesSource {
    /** Genera un enum puro y comprueba colisiones de identificadores antes de escribirlo. */
    fun render(type: String, entries: List<Pair<String, String>>, usage: String): String {
        require(entries.map { it.first }.distinct().size == entries.size) { "Roles duplicados: $type" }
        return buildString {
            appendLine("package cruxui.android.lib.theme.catalog")
            appendLine(schemaDocumentation(
                "$type identifica todas las entradas Library de su familia sin copiar valores.",
                "La generación descubre Library; las consultas resuelven cada rol contra el tema activo. No depende de componentes.",
                "$usage No editar este archivo: agregar el token en Library y compilar. catalogName contiene la ruta semántica exacta.",
                "Ofrecer el catálogo completo con autocompletado en ambas plataformas.",
            ))
            appendLine("enum class $type(val catalogName: String) {")
            entries.forEach { (role, path) ->
                appendLine("    /** Selecciona $path de la colección activa. */")
                appendLine("    $role(\"$path\"),")
            }
            appendLine("}")
        }
    }

    /** Mantiene aliases existentes y añade todas las propiedades nuevas sin otra lista obligatoria. */
    fun entries(names: List<String>, aliases: List<Pair<String, String>> = emptyList()): List<Pair<String, String>> {
        val available = aliases.filter { it.second in names }
        return available + names.map { it.replaceFirstChar(Char::uppercaseChar) to it }
            .filter { entry -> available.none { it.first == entry.first } }
    }
}
