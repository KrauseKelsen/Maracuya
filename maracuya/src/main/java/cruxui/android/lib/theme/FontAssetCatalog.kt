package cruxui.android.lib.theme

import cruxui.android.lib.tokens.base.FontFamilyToken
import kotlin.math.abs

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `FontAssetFace` describe un archivo físico de fuente mediante un nombre de recurso, peso y cursiva.
 *
 * Justificación:
 * Permite compartir el catálogo entre el proceso JVM exportador y Android sin depender de R en los datos fuente.
 *
 * Se comunica con:
 * `FontAssetCatalog` agrupa estas entradas; `FontResourceCatalog` las enlaza a R y token-exporter produce
 * familias XML.
 *
 * Uso:
 * Tipo público de infraestructura del DS. El host normalmente selecciona `FontFamilyToken` en sus overrides.
 * Los mantenedores agregan el archivo en res/font y una entrada como `FontAssetFace("mulish_bold", 700)` al
 * catálogo; no se usa el nombre del archivo con extensión.
 *
 * Objetivos:
 * - Identificar una fuente sin tipos Android.
 * - Compartir peso y cursiva entre adaptadores.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param resourceName Nombre base válido de un recurso font, sin extensión ni prefijo @font/.
 * @param weight Peso numérico de la cara, por ejemplo 400 o 700.
 * @param italic true para el archivo en cursiva.
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
data class FontAssetFace(
    val resourceName: String,
    val weight: Int,
    val italic: Boolean = false,
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `FontAssetCatalog` mantiene la lista única de archivos tipográficos incluidos por familia.
 *
 * Justificación:
 * Evita duplicar pesos y cursivas en Compose, Views y XML. El exportador genera familias XML a partir de esta
 * lista.
 *
 * Se comunica con:
 * Entrega `FontAssetFace` a `FontResourceCatalog` y a token-exporter; usa `FontFamilyToken` para seleccionar la
 * familia.
 *
 * Uso:
 * API pública de infraestructura, usada principalmente dentro del DS. El host puede inspeccionar `faces(family)`
 * o solicitar `closest(family, weight, italic)` sin cargar Android.
 * Para usar una fuente en un componente preferir `fontFamily` del override o `setFont`.
 * Para agregar archivos, editar este catálogo y res/font; los recursos Android derivados se regeneran al
 * compilar. `closest` prioriza la cursiva solicitada y después la distancia de peso; si no hay coincidencia
 * exacta retorna la cara más cercana.
 *
 * Objetivos:
 * - Mantener una sola lista de archivos.
 * - Compartir selección de peso/cursiva.
 * - Alimentar recursos XML reproducibles.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
object FontAssetCatalog {
    private val mulish = listOf(
        FontAssetFace("mulish_extra_light", 200),
        FontAssetFace("mulish_extra_light_italic", 200, true),
        FontAssetFace("mulish_light", 300),
        FontAssetFace("mulish_light_italic", 300, true),
        FontAssetFace("mulish_regular", 400),
        FontAssetFace("mulish_italic", 400, true),
        FontAssetFace("mulish_medium", 500),
        FontAssetFace("mulish_medium_italic", 500, true),
        FontAssetFace("mulish_semi_bold", 600),
        FontAssetFace("mulish_semi_bold_italic", 600, true),
        FontAssetFace("mulish_bold", 700),
        FontAssetFace("mulish_bold_italic", 700, true),
        FontAssetFace("mulish_extra_bold", 800),
        FontAssetFace("mulish_extra_bold_italic", 800, true),
        FontAssetFace("mulish_black", 900),
        FontAssetFace("mulish_black_italic", 900, true),
    )

    private val davivienda = listOf(
        FontAssetFace("davivienda_light", 300),
        FontAssetFace("davivienda_light_italic", 300, true),
        FontAssetFace("davivienda_regular", 400),
        FontAssetFace("davivienda_italic", 400, true),
        FontAssetFace("davivienda_semi_bold", 600),
        FontAssetFace("davivienda_semi_bold_italic", 600, true),
        FontAssetFace("davivienda_bold", 700),
        FontAssetFace("davivienda_bold_italic", 700, true),
        FontAssetFace("davivienda_extra_bold", 800),
        FontAssetFace("davivienda_extra_bold_italic", 800, true),
    )

    /**
     * Devuelve las caras físicas de una familia, sin cargar Typeface.
     *
     * @param family Familia corporativa solicitada.
     * @return Lista del catálogo con los pesos y cursivas disponibles.
     */
    fun faces(family: FontFamilyToken): List<FontAssetFace> = when (family) {
        FontFamilyToken.MULISH -> mulish
        FontFamilyToken.DAVIVIENDA -> davivienda
    }

    /**
     * Busca la cara física más cercana, priorizando la coincidencia de cursiva sobre la distancia de peso.
     *
     * @param family Familia corporativa.
     * @param weight Peso solicitado entre 1 y 1000; por defecto 400.
     * @param italic Solicitud de cursiva; false por defecto.
     * @return Cara del catálogo; en empates conserva la primera.
     * @throws IllegalArgumentException Si weight está fuera de 1..1000.
     */
    fun closest(family: FontFamilyToken, weight: Int = 400, italic: Boolean = false): FontAssetFace {
        require(weight in 1..1000) { "Font weight must be between 1 and 1000" }
        return faces(family).minBy { abs(it.weight - weight) + if (it.italic == italic) 0 else 1000 }
    }
}
