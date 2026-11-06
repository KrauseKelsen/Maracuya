package cruxui.android.lib.theme

import androidx.annotation.FontRes
import cruxui.android.lib.tokens.base.FontFamilyToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `FontResourceFace` representa una cara tipográfica enlazada a un ID Android de res/font.
 *
 * Justificación:
 * Adapta la referencia simbólica del catálogo puro a los recursos que cargan los renderizadores sin duplicar la
 * lista de fuentes.
 *
 * Se comunica con:
 * `FontResourceCatalog` la construye desde `FontAssetFace` y `LibraryAssetResources`; la consumen los
 * adaptadores Android y Compose.
 *
 * Uso:
 * Tipo público de infraestructura. El host puede obtenerlo con `FontResourceCatalog.closest(...)` en una
 * integración Android propia; para componentes del DS basta con elegir la familia.
 * `resourceId` es un @FontRes, no un Typeface ni un ID de atributo.
 *
 * Objetivos:
 * - Transportar un enlace de fuente verificable por Android.
 * - Conservar peso y cursiva del catálogo puro.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param resourceId ID del archivo res/font.
 * @param weight Peso físico declarado del archivo.
 * @param italic Indica si el archivo corresponde a cursiva.
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
data class FontResourceFace(@param:FontRes val resourceId: Int, val weight: Int, val italic: Boolean = false)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `FontResourceCatalog` enlaza el catálogo físico de fuentes con los IDs Android generados.
 *
 * Justificación:
 * Ofrece a los adaptadores recursos nativos manteniendo FontAssetCatalog como única fuente de nombres, pesos y
 * cursivas.
 *
 * Se comunica con:
 * Consulta `FontAssetCatalog` y resuelve cada nombre mediante `LibraryAssetResources.font`; lo usan
 * `AndroidTokenAdapter` y `FontFamiliesComposeAdapter`.
 *
 * Uso:
 * API pública de infraestructura Android. El host suele usar la familia semántica del tema.
 * Si necesita un recurso concreto: `val face = FontResourceCatalog.closest(library.fontFamily, weight = 700)` y
 * cargar `face.resourceId` con ResourcesCompat.
 * Esta clase no lee CompositionLocals ni selecciona la marca; la familia se recibe explícitamente.
 *
 * Objetivos:
 * - Enlazar fuentes sin búsquedas por nombre durante el render.
 * - Compartir el catálogo físico entre las dos UIs.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
object FontResourceCatalog {
    /**
     * Enlaza todas las caras físicas de una familia con recursos Android.
     *
     * @param family Familia que desea inspeccionarse.
     * @return Lista de caras con IDs @FontRes, peso y cursiva.
     */
    fun faces(family: FontFamilyToken): List<FontResourceFace> = FontAssetCatalog.faces(family).map(::bind)
    /**
     * Enlaza la cara más cercana según la política de FontAssetCatalog.
     *
     * @param family Familia solicitada.
     * @param weight Peso entre 1 y 1000.
     * @param italic Solicitud de cursiva.
     * @return Cara física enlazada; el adaptador decide si necesita síntesis adicional.
     * @throws IllegalArgumentException Si weight está fuera del rango permitido.
     */
    fun closest(family: FontFamilyToken, weight: Int = 400, italic: Boolean = false): FontResourceFace =
        bind(FontAssetCatalog.closest(family, weight, italic))
    /**
     * Convierte una entrada simbólica en una cara Android usando los bindings generados. Uso privado del
     * catálogo.
     *
     * @param face Archivo físico declarado en FontAssetCatalog.
     * @return Cara con el ID de recurso correspondiente.
     */
    private fun bind(face: FontAssetFace) = FontResourceFace(LibraryAssetResources.font(face.resourceName), face.weight, face.italic)
}
