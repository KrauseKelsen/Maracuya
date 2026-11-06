package cruxui.android.lib.theme

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Shapes
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import cruxui.android.lib.atoms.LibraryColorTokens
import cruxui.android.lib.compositions.LocalFontFamily
import cruxui.android.lib.compositions.LocalLibraryColorTokens
import cruxui.android.lib.compositions.LocalLibraryIcons
import cruxui.android.lib.compositions.LocalLibraryTypography
import cruxui.android.lib.theme.collections.LibraryTypography
import cruxui.android.lib.utils.composeadapters.ColorComposeAdapter
import cruxui.android.lib.utils.materialadapters.ColorMaterialAdapter
import cruxui.android.lib.utils.composeadapters.FontFamiliesComposeAdapter
import cruxui.android.lib.utils.materialadapters.TypographyMaterialAdapter

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `MyLibraryTheme` es la función pública que establece el tema del DS alrededor de un árbol Compose.
 *
 * Justificación:
 * Resuelve marca y modo una vez por ámbito, proporciona las colecciones semánticas existentes y permite adaptar
 * esas colecciones a Material cuando el host lo necesita.
 *
 * Se comunica con:
 * Usa `LibraryThemeSet.resolve`, `ThemeStyle`, los cuatro CompositionLocals y los adaptadores de color,
 * tipografía y fuentes. Delega el contenido a LibraryThemeContent.
 *
 * Uso:
 * API pública del host Compose. Debe envolver los componentes que necesitan colores e iconos del DS:
 * ```kotlin
 * MyLibraryTheme(
 *     theme = LibraryThemes.Maracuya,
 *     style = ThemeStyle.AUTO,
 *     useMaterial = true,
 * ) {
 *     val library = LibraryTheme.current
 *     TextView(
 *         text = "Información",
 *         tokensOverride = TextViewTokensOverride(typography = library.typography.headline24),
 *     )
 * }
 * ```
 * `theme` elige la marca; `style` fuerza claro/oscuro o sigue el sistema. `useMaterial = true` ofrece un
 * MaterialTheme derivado; false solo proporciona el DS.
 * La función pinta bgBase detrás del contenido. Se pueden anidar temas para aislar una sección; el ámbito
 * interior prevalece.
 * Para XML usar `MyLibraryThemeWrp`; no llamar esta función desde una View nativa ni desde Application. No
 * sincroniza por sí misma las barras del sistema.
 *
 * Objetivos:
 * - Proveer un ámbito temático coherente.
 * - Reutilizar la resolución común de temas.
 * - Hacer opcional la integración Material.
 *
 * Flujo de construcción o ejecución:
 * 1) Leer la preferencia oscura del sistema.
 * 2) Resolver el snapshot de marca.
 * 3) Proveer los locals y pintar bgBase.
 * 4) Proveer Material opcionalmente y ejecutar content.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param theme Marca; por defecto LibraryThemes.Maracuya.
 * @param style Modo LIGHT, DARK o AUTO; por defecto AUTO.
 * @param useMaterial Si true, proporciona colores y tipografía Material derivados del DS.
 * @param content Árbol Compose que consume el tema.
 * @see cruxui.android.lib.theme.LibraryTheme
 * @see cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp
 */
@Composable
fun MyLibraryTheme(
    theme: LibraryThemeSet = LibraryThemes.Maracuya,
    style: ThemeStyle = ThemeStyle.AUTO,
    useMaterial: Boolean = false,
    content: @Composable () -> Unit
) {
    LibraryThemeContent(
        theme = theme,
        style = style,
        useMaterial = useMaterial,
        applyBackground = true,
        content = content,
    )
}

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `MyLibraryComponentTheme` proporciona el tema a un componente Compose alojado dentro de un wrapper XML.
 *
 * Justificación:
 * Comparte la resolución de MyLibraryTheme sin pintar el fondo de toda la pantalla desde cada componente.
 *
 * Se comunica con:
 * Los wrappers basados en Compose llaman esta función; delega en LibraryThemeContent con applyBackground =
 * false.
 *
 * Uso:
 * Función interna del DS. El host no puede llamarla desde otro módulo; en XML configura `MyLibraryThemeWrp`, y
 * en Compose usa `MyLibraryTheme`.
 * Un wrapper interno entrega su marca, modo, opción Material y contenido Compose.
 *
 * Objetivos:
 * - Proveer el mismo tema a wrappers Compose.
 * - Dejar el fondo de la sección en manos del contenedor XML.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param theme Marca resuelta por el wrapper.
 * @param style Política de modo.
 * @param useMaterial Habilita el puente Material si el wrapper lo solicita.
 * @param content Contenido Compose del componente alojado.
 * @see cruxui.android.lib.theme.LibraryThemeSnapshot
 */
@Composable
internal fun MyLibraryComponentTheme(
    theme: LibraryThemeSet = LibraryThemes.Maracuya,
    style: ThemeStyle = ThemeStyle.AUTO,
    useMaterial: Boolean = false,
    content: @Composable () -> Unit,
) {
    LibraryThemeContent(
        theme = theme,
        style = style,
        useMaterial = useMaterial,
        applyBackground = false,
        content = content,
    )
}

/**
 * Implementa el ámbito temático común a las dos entradas Compose. Uso privado del DS.
 * Resuelve el snapshot, adapta la fuente, proporciona los cuatro locals y envuelve el contenido Material si
 * corresponde.
 *
 * @param theme Marca seleccionada.
 * @param style Política de modo.
 * @param useMaterial Habilita MaterialTheme derivado.
 * @param applyBackground Si true, agrega el fondo bgBase; los wrappers lo desactivan.
 * @param content Árbol receptor del ámbito.
 */
@Composable
private fun LibraryThemeContent(
    theme: LibraryThemeSet,
    style: ThemeStyle,
    useMaterial: Boolean,
    applyBackground: Boolean,
    content: @Composable () -> Unit,
) {
    val isDark = style.isDark(isSystemInDarkTheme())

    val snapshot = remember(theme, isDark) { theme.resolve(isDark) }
    val tokens = snapshot.colors

    val fontFamilyToken = snapshot.fontFamily
    val composeFontFamily =
        FontFamiliesComposeAdapter.toCompose(fontFamilyToken)

    val bgColor = ColorComposeAdapter.toComposeColor(tokens.bgBase)

    val semanticTypography = snapshot.typography

    val semanticIcons = snapshot.icons

    CompositionLocalProvider(
        LocalLibraryColorTokens provides tokens,
        LocalFontFamily provides fontFamilyToken,
        LocalLibraryTypography provides semanticTypography,
        LocalLibraryIcons provides semanticIcons
    ) {
        if (applyBackground) {
            Box(modifier = Modifier.background(bgColor)) {
                ThemedMaterialContent(
                    useMaterial = useMaterial,
                    tokens = tokens,
                    isDark = isDark,
                    semanticTypography = semanticTypography,
                    composeFontFamily = composeFontFamily,
                    content = content,
                )
            }
        } else {
            ThemedMaterialContent(
                useMaterial = useMaterial,
                tokens = tokens,
                isDark = isDark,
                semanticTypography = semanticTypography,
                composeFontFamily = composeFontFamily,
                content = content,
            )
        }
    }
}

/**
 * Proporciona MaterialTheme derivado solo cuando useMaterial está activo; en otro caso ejecuta content
 * directamente.
 * Uso privado del tema; el host selecciona la opción en MyLibraryTheme.
 *
 * @param useMaterial Activa la adaptación Material.
 * @param tokens Paleta del modo ya resuelto.
 * @param isDark Modo usado al adaptar el esquema Material.
 * @param semanticTypography Tipografías de la marca.
 * @param composeFontFamily Familia ya adaptada a Compose.
 * @param content Contenido que recibe el tema opcional.
 */
@Composable
private fun ThemedMaterialContent(
    useMaterial: Boolean,
    tokens: LibraryColorTokens,
    isDark: Boolean,
    semanticTypography: LibraryTypography,
    composeFontFamily: FontFamily,
    content: @Composable () -> Unit,
) {
    if (useMaterial) {
        val colorScheme = remember(tokens, isDark) {
            ColorMaterialAdapter.toMaterialColorScheme(tokens, isDark)
        }

        val adaptedTypography = TypographyMaterialAdapter.adaptedTypography(semanticTypography, composeFontFamily)

        MaterialTheme(
            colorScheme = colorScheme,
            typography = adaptedTypography,
            shapes = Shapes(),
            content = content
        )
    } else {

        content()
    }
}
