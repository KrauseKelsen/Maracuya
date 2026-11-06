package schema

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryTypographySource produce la API semántica tipada a partir de nombres descubiertos en Library.
 *
 * Justificación:
 * Genera propiedades, copy y consulta por nombre sin duplicar métricas ni escribir un mapeo por cada estilo.
 * Conserva LibraryTypography como data class para las personalizaciones de marca existentes.
 *
 * Uso:
 * Interno de construcción; recibe el esquema validado por TypographySchema y entrega código Kotlin.
 * El host consume la clase generada, nunca este escritor.
 *
 * Objetivos:
 * - Generar el contrato semántico completo sin almacenar valores fuera de Library.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object LibraryTypographySource {
    /** Genera una propiedad y una rama de consulta por cada entrada, siempre referidas a Library. */
    fun render(names: List<String>): String = buildString {
        appendLine("package cruxui.android.lib.theme.collections")
        appendLine("import cruxui.android.lib.atoms.LibraryTypographyTokens")
        appendLine("import cruxui.android.lib.tokens.base.TypographyToken")
        appendLine("import cruxui.android.lib.theme.catalog.LibraryTypographyRole")
        appendLine("""
/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryTypography expone todos los estilos de LibraryTypographyTokens como una colección de tema.
 *
 * Justificación:
 * Sus defaults apuntan a Library; no mantiene otras métricas. La generación incorpora automáticamente
 * cualquier propiedad nueva del catálogo y conserva copy para personalizaciones por marca o ámbito.
 * Se comunica con LibraryThemeSnapshot, LibraryTheme.current, los adaptadores y token-exporter.
 *
 * Uso:
 * API pública del host. En Compose consultar LibraryTheme.current.typography.headline24;
 * para TextView del DS usar TextViewTokensOverride(typography = library.typography.headline24).
 * En una receta XML, library es el snapshot recibido por el register del componente.
 * El host puede personalizar una colección con typography.copy(headline24 = nuevoToken).
 * get(nombre) ofrece consulta dinámica de cualquier entrada; asMap permite recorrer todo el catálogo.
 * En XML usar android:textAppearance="?attr/libraryTypographyHeadline24" dentro del overlay de marca.
 * Archivo generado: agregar o editar estilos en LibraryTypographyTokens y volver a compilar.
 *
 * Objetivos:
 * - Compartir valores entre Compose y XML.
 * - Permitir sustituciones de tema sin modificar el catálogo base.
 * - Evitar listas manuales adicionales de tipografías.
 *
 * Flujo: LibraryTypographyTokens → API semántica generada → tema → adaptadores Compose/XML.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
""".trimIndent())
        names.forEach { appendLine(" * @param $it Estilo $it; por defecto utiliza LibraryTypographyTokens.$it.") }
        appendLine(" * @see LibraryTypographyTokens")
        appendLine(" */")
        appendLine("data class LibraryTypography(")
        names.forEach {
            appendLine("    /** Estilo $it del tema; conserva la definición Library salvo sustitución explícita. */")
            appendLine("    val $it: TypographyToken = LibraryTypographyTokens.$it,")
        }
        appendLine(") {")
        appendLine("    /** Resuelve un rol contra los valores efectivos de esta colección. */")
        appendLine("    operator fun get(role: LibraryTypographyRole): TypographyToken = get(role.catalogName)")
        appendLine("    /** Consulta cualquier estilo por nombre exacto; falla explícitamente si no pertenece al catálogo. */")
        appendLine("    operator fun get(name: String): TypographyToken = when (name) {")
        names.forEach { appendLine("        \"$it\" -> $it") }
        appendLine("        else -> throw IllegalArgumentException(\"Tipografía no registrada: \$name\")")
        appendLine("    }")
        appendLine("    /** Proyecta los valores efectivos del tema; incluye todas las propiedades nuevas de Library. */")
        appendLine("    fun asMap(): Map<String, TypographyToken> = linkedMapOf(")
        names.sorted().forEach { appendLine("        \"$it\" to $it,") }
        appendLine("    )")
        appendLine("}")
    }
}
