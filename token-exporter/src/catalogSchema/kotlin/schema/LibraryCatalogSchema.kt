package schema

import cruxui.android.lib.atoms.LibraryColorTokens
import cruxui.android.lib.atoms.LibraryIconTokens
import cruxui.android.lib.tokens.base.IconToken
import cruxui.android.lib.tokens.base.LibraryIconGroup
import java.lang.reflect.Method
import java.lang.reflect.Modifier

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * IconGroupSchema transporta la estructura descubierta de una categoría Library, sin copiar valores.
 *
 * Justificación:
 * Separa reflexión y escritura: los generadores reciben nombres y tipos ya validados.
 *
 * Uso:
 * Modelo interno del build. LibraryCatalogSchema lo construye; LibraryIconsSource y RolesSource lo consumen.
 * El host utiliza LibraryIcons generado y nunca necesita este modelo.
 *
 * Objetivos:
 * - Representar cualquier categoría sin conocer sus iconos de antemano.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @property property Propiedad de LibraryIconTokens que contiene la categoría.
 * @property name Nombre público de la categoría.
 * @property type Tipo Library completo de la categoría.
 * @property legacyType Alias histórico opcional.
 * @property icons Nombres de todos los IconToken declarados por el grupo.
 */
internal data class IconGroupSchema(
    val property: String,
    val name: String,
    val type: String,
    val legacyType: String,
    val icons: List<String>,
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryCatalogSchema descubre colores y categorías de iconos desde sus clases Library compiladas.
 *
 * Justificación:
 * Es el único lector de estructura. No depende de temas, XML, Compose ni clases Corporate.
 * Entrega nombres a los escritores; los valores se consultan después en el tema efectivo.
 *
 * Uso:
 * Interno del build. Agregar ColorToken en LibraryColorTokens o IconToken en una categoría Library;
 * generateCatalogApi incorpora automáticamente sus getters. Un grupo nuevo se declara con LibraryIconGroup.
 * La app nunca utiliza reflexión. Los getters JVM de ColorToken se reconocen por el retorno Long y
 * el nombre mangled que Kotlin asigna al value class, excluyendo propiedades Long ajenas al catálogo.
 *
 * Objetivos:
 * - Ampliar catálogos sin editar listas de tokens ni resolutores por componente.
 * - Rechazar nombres incompatibles antes de producir fuentes.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
internal object LibraryCatalogSchema {
    /** Descubre los getters públicos de ColorToken, incluidas propiedades calculadas. */
    fun colors(): List<String> = validateNames(getters(LibraryColorTokens::class.java)
        .filter { it.returnType == Long::class.javaPrimitiveType && '-' in it.name }.map(::propertyName))

    /** Descubre categorías completas y conserva el orden del constructor público histórico. */
    fun icons(): List<IconGroupSchema> {
        val groups = getters(LibraryIconTokens::class.java).map { method ->
            val group = requireNotNull(method.getAnnotation(LibraryIconGroup::class.java)) {
                "Falta @get:LibraryIconGroup en ${method.name}"
            }
            require(group.legacyType.isEmpty() || group.legacyType.matches(Regex("[A-Z][a-zA-Z0-9]*")))
            IconGroupSchema(propertyName(method), group.name, method.returnType.name, group.legacyType,
                validateNames(getters(method.returnType).filter { it.returnType == IconToken::class.java }.map(::propertyName)))
        }
        validateNames(groups.map { it.name })
        require(groups.map { it.legacyType }.filter { it.isNotEmpty() }.let { it.distinct().size == it.size })
        // Compatibilidad posicional, no registro de categorías admitidas. Los grupos nuevos se ordenan al final.
        val legacyOrder = listOf("general", "alerts", "people", "arrows", "money", "extension")
        return groups.sortedWith(compareBy<IconGroupSchema> {
            legacyOrder.indexOf(it.name).takeIf { index -> index >= 0 } ?: Int.MAX_VALUE
        }.thenBy { it.name })
    }

    /** Solo considera propiedades públicas de instancia, no funciones con parámetros ni campos internos. */
    private fun getters(type: Class<*>): List<Method> = type.declaredMethods.filter {
        Modifier.isPublic(it.modifiers) && !Modifier.isStatic(it.modifiers) && !it.isSynthetic &&
            it.parameterCount == 0 && it.name.startsWith("get")
    }

    /** Traduce el getter JVM al identificador Kotlin conservando mayúsculas interiores. */
    private fun propertyName(method: Method): String = method.name.substringBefore('-')
        .removePrefix("get").replaceFirstChar(Char::lowercaseChar)

    /** Valida identificadores que pueden proyectarse a Kotlin y a atributos Android sin ambigüedad. */
    fun validateNames(names: List<String>): List<String> {
        require(names.isNotEmpty()) { "El catálogo no puede estar vacío" }
        require(names.all { it.matches(Regex("[a-z][a-zA-Z0-9]*")) }) { "Nombre de token no exportable" }
        require(names.distinct().size == names.size) { "Nombres semánticos duplicados" }
        require(names.map { it.lowercase() }.distinct().size == names.size) { "Colisión de nombres de recursos" }
        return names.sorted()
    }
}
