package schema

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * Construye la documentación española de las APIs generadas del catálogo.
 *
 * Justificación:
 * Mantiene el machote común sin acoplar los escritores a una familia de tokens ni a componentes.
 *
 * Uso:
 * Función interna del build; cada escritor proporciona propósito, justificación y manual del host.
 * El resultado se incorpora a las fuentes que recibirán Android y el exportador.
 *
 * Objetivos:
 * - Publicar documentación consistente junto a las APIs generadas.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param description Propósito del contrato.
 * @param reason Justificación y colaboradores.
 * @param usage Manual de consumo o mantenimiento.
 * @param objective Resultado que garantiza la API.
 */
internal fun schemaDocumentation(description: String, reason: String, usage: String, objective: String): String = """
/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * $description
 *
 * Justificación:
 * $reason
 *
 * Uso:
 * $usage
 *
 * Objetivos:
 * - $objective
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
""".trimIndent()
