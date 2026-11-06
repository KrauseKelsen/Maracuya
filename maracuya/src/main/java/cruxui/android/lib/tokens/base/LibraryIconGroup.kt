package cruxui.android.lib.tokens.base

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryIconGroup declara el nombre público de una categoría en LibraryIconTokens.
 *
 * Justificación:
 * El exportador descubre categorías sin mantener otro registro de grupos o de iconos.
 * Se comunica con el lector de esquema de token-exporter; no interviene en la resolución de una View.
 *
 * Uso:
 * Contrato de construcción del DS. Anotar el getter de cada categoría con
 * `@get:LibraryIconGroup("alerts", legacyType = "AlertsIcons")`.
 * Para una categoría nueva basta un nombre único en camelCase; omitir legacyType.
 * El host consume LibraryTheme.current.icons.alerts.info o ?attr/libraryIconAlertsInfo en XML.
 * No necesita procesar esta anotación. La reflexión solo ocurre durante el build.
 *
 * Objetivos:
 * - Declarar la identidad semántica junto a su única fuente.
 * - Conservar los nombres históricos sin repetir las propiedades de los grupos.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @property name Nombre semántico del grupo y prefijo de sus atributos XML.
 * @property legacyType Alias Kotlin histórico opcional, utilizado únicamente para compatibilidad de fuentes.
 */
@Target(AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class LibraryIconGroup(val name: String, val legacyType: String = "")
