package cruxui.android.lib.tokens.base

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * IconToken identifica un asset y sus valores visuales predeterminados sin depender de Android.
 *
 * Justificación:
 * Las categorías Library declaran estos valores y los adaptadores los resuelven en Compose o Views.
 * resourceName permite exportar recursos antes de generar R; filledResId permite assets del host en ejecución.
 *
 * Uso:
 * API pública. Para un icono del DS usar library.icons.alerts.info; para sustituirlo usar copy.
 * Los mantenedores declaran IconToken("nombre", resourceName = "ic_asset", iconSize = 18).
 * resourceName debe existir en res/drawable. Cambiarlo y compilar actualiza el enlace XML y Android.
 * Para assets exclusivos del host usar IconToken("propio", filledResId = R.drawable.propio).
 * Estos IDs no se pueden exportar como parte del catálogo estático del DS.
 *
 * Objetivos:
 * - Compartir identidad, tamaño y tinte sin introducir recursos Android en los catálogos puros.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param name Identidad lógica del asset.
 * @param filledResId Recurso del host para ejecución; cero usa resourceName.
 * @param iconColor Tinte opcional; null delega la política al componente.
 * @param iconSize Tamaño predeterminado en dp.
 * @param resourceName Nombre de drawable empaquetado por el DS, sin extensión.
 */
data class IconToken(
    val name: String,
    val filledResId: Int = 0,
    val iconColor: ColorToken? = null,
    val iconSize: Int = 18,
    val resourceName: String? = null,
)
