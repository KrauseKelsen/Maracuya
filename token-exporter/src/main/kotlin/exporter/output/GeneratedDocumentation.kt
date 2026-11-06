package exporter.output

/** Construye la documentación española de los tipos generados sin alterar sus referencias. */
internal fun typeDocumentation(name: String, description: String, usage: String): String = """
/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `$name`: $description
 *
 * Justificación:
 * Evita mantener manualmente otra lista de recursos distinta del catálogo del DS.
 *
 * Se comunica con:
 * El catálogo de exportación y los recursos Android compilados en R.
 *
 * Uso:
 * $usage
 *
 * Objetivos:
 * - Exponer recursos generados con tipos y nombres del catálogo.
 * - Mantener consistente el acceso desde el host y los adaptadores del DS.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.theme.ThemeExports
 */
""".trimIndent()

/** Añade el manual de procedencia y consumo a los XML generados, sin cambiar valores. */
internal fun resourceDocumentation(relative: String): String = """
<!--
    s4121779 - Krause Kelsen

    Descripción:
    $relative: recursos derivados del catálogo LibraryThemes y FontAssetCatalog.

    Justificación:
    Comparte los valores de Compose con XML sin mantener otra paleta o tabla tipográfica.

    Se comunica con:
    LibraryXmlReferences, LibraryAssetResources, AndroidTokenAdapter y los estilos del host.

    Uso:
    Archivo generado: no editar. Cambiar los catálogos fuente y compilar.
    Aplicar ThemeOverlay.Maracuya, .Light o .Dark en el ámbito Android correspondiente.
    Usar android:textAppearance="?attr/libraryTypographyHeadline24" para tipografía,
    android:textColor="?attr/libraryColorBrandPrimary" para color y
    android:fontFamily="?attr/libraryFontPrimary" para la familia con sus pesos/cursivas.
    Las recetas de TokensOverrideRegistry se resuelven en ejecución por componente.
    Cambiar el proveedor semántico no reinfla estilos ni sustituye el Context existente.

    Objetivos:
    - Exportar referencias, colores, estilos, dimensiones y familias desde la fuente única.
    - Mantener una salida reproducible para el consumidor Android.

    @author Krause Kelsen
    @since 07-16-2026
    @version 0.0.13
    @see cruxui.android.lib.theme.ThemeExports
-->
""".trimIndent() + "\n"
