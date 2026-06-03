package cruxui.android.maracuya.wrappers.components.buttons.primary

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `PrimaryButtonAttributes` agrupa los valores de configuración del wrapper
 * `PrimaryButtonWrp` obtenidos desde XML o defaults internos.
 *
 * Su objetivo es transportar estado inicial del componente de forma explícita,
 * evitando lógica distribuida y facilitando mantenimiento/lectura.
 *
 * Función:
 * - Representar texto visible del botón.
 * - Representar comportamiento de progreso al presionar.
 * - Representar estado habilitado inicial.
 *
 * Relación con otras clases:
 * - `PrimaryButtonAttributeParser` construye esta data class desde atributos XML.
 * - `PrimaryButtonWrp` usa sus valores para inicializar estados Compose.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @param text Texto principal del botón.
 * @param showProgressOnPress Si `true`, muestra indicador de progreso mientras está presionado.
 * @param enabled Estado habilitado inicial del botón.
 *
 * @see cruxui.android.maracuya.wrappers.components.buttons.primary.PrimaryButtonWrp
 */
internal data class PrimaryButtonAttributes(
    val text: String,
    val showProgressOnPress: Boolean,
    val enabled: Boolean,
)
